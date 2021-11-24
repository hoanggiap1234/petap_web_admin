/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.controller.user;

import io.jsonwebtoken.Claims;
import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.CacheService;
import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.dto.user.UserDTO;
import io.petapp.api.enums.VerificationMethod;
import io.petapp.api.enums.VerificationType;
import io.petapp.api.model.user.User;
import io.petapp.api.service.user.OtpService;
import io.petapp.api.service.user.UserService;
import io.petapp.api.vm.admin.RequestTokenVM;
import io.petapp.api.vm.admin.VerifyTokenVM;
import io.petapp.api.vm.user.CheckPhoneVM;
import io.petapp.api.vm.user.LoginVM;
import io.petapp.security.jwt.JWTFilter;
import io.petapp.security.jwt.JWTToken;
import io.petapp.security.jwt.TokenProvider;
import io.petapp.utils.Helper;
import io.petapp.utils.encyption.TravisRsa;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping(Constants.Api.Path.AUTH)
@RequiredArgsConstructor
@Slf4j
@Api(tags = Constants.Api.Tag.AUTHENTICATION)
@Validated
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final OtpService otpService;
    private final UserService userService;
    private final CacheService cacheService;
    private final HttpServletRequest request;

    @ApiOperation(value = "Request OTP for registering new account",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.AUTHENTICATION)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OTP sent for registering purposes",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(Constants.Api.Path.Auth.OTP_REQUEST)
    public ApiResponseEntity requestOtp(@Valid @RequestBody RequestTokenVM requestTokenVM) {
        Locale locale = RequestContextUtils.getLocale(request);
        String receiver = requestTokenVM.getReceiver();
        if (!Helper.validatePhoneNumber(receiver)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(otpService.requestOtp(
            locale,
            receiver,
            VerificationMethod.SMS_OTP.getCode(),
            VerificationType.REGISTER_SCREEN.getCode(),
            requestTokenVM.getDeviceId(),
            Helper.getClientIpAddress(request),
            requestTokenVM.getCountryCode()
        )).build();
    }

    @ApiOperation(value = "Verify OTP for registering new account",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.AUTHENTICATION)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OTP verified for registering purposes",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(Constants.Api.Path.Auth.OTP_VERIFY)
    public ApiResponseEntity verifyOtp(@Valid @RequestBody VerifyTokenVM verifyTokenVM) {
        if (otpService.verifyOtp(verifyTokenVM)) {
            return ApiResponseEntity.bodyOk().build();
        }
        return ApiResponseEntity.bodyError().build();
    }

    @ApiOperation(value = "Sign in",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.AUTHENTICATION)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Login successfully",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(Constants.Api.Path.Auth.LOGIN)
    public ApiResponseEntity login(@Valid @RequestBody LoginVM loginVM) {
        if (!Helper.validatePhoneNumber(loginVM.getPhoneNumber())) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }

        String userName = Helper.formatPhone(loginVM.getPhoneNumber(), loginVM.getCountryCode());

        if (!userService.checkActivatedUser(userName)) {
            throw new BusinessException(ExceptionType.NOT_ACTIVATED_USER);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userName,
            loginVM.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JWTToken jwtToken = new JWTToken();

        // Create access token
        Map<String, Object> claims = new HashMap<>();
        claims.put(TokenProvider.CLAIM_DEVICE_ID, loginVM.getDevice().getId());
        String accessToken = tokenProvider.createToken(authentication, loginVM.isRememberMe(), claims);
        jwtToken.setAccessToken(accessToken);

        // Set user's information
        if (!StringUtils.isBlank(accessToken)) {
            ModelMapper modelMapper = new ModelMapper();
            User.Device device = modelMapper.map(loginVM.getDevice(), User.Device.class);
            jwtToken.setUser(userService.login(userName, device, null));
        }

        // Create refresh token
        claims.put(TokenProvider.CLAIM_REFRESH_TOKEN, true);
        String refreshToken = tokenProvider.createToken(authentication, true, claims);
        jwtToken.setRefreshToken(refreshToken);

        // Create secure transaction
        TravisRsa travisRsa = cacheService.getTravisRsa();
        if (travisRsa == null) {
            travisRsa = new TravisRsa();
            cacheService.setTravisRsa(travisRsa);
        }
        jwtToken.setPublicKey(TravisRsa.getBase64PublicKey(travisRsa.getPublicKey()));

        return ApiResponseEntity.bodyOk().bodyData(jwtToken).build();
    }

    @ApiOperation(value = "Refresh token",
        notes = "Use in case of expired token",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.AUTHENTICATION,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Token successfully refreshed",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping(Constants.Api.Path.Auth.REFRESH_TOKEN)
    public ApiResponseEntity refreshToken() throws Exception {
        Claims claims = (Claims) request.getAttribute(JWTFilter.ATTR_CLAIMS);
        boolean isRefreshToken = claims != null
            && claims.containsKey(TokenProvider.CLAIM_REFRESH_TOKEN)
            && (boolean) claims.get(TokenProvider.CLAIM_REFRESH_TOKEN);
        if (!isRefreshToken)
            return ApiResponseEntity.bodyForbidden().build();
        Map<String, Object> claimMap = getMapFromClaims(claims);
        claimMap.remove(TokenProvider.CLAIM_REFRESH_TOKEN);
        JWTToken jwtToken = new JWTToken();

        // Create access token
        String accessToken = tokenProvider.createRefreshToken(claimMap, claimMap.get(TokenProvider.CLAIM_SUB).toString());
        jwtToken.setAccessToken(accessToken);

        // Set user's information
        if (!StringUtils.isBlank(accessToken)) {
            jwtToken.setUser(userService.login(
                claimMap.get(TokenProvider.CLAIM_SUB).toString(),
                null,
                claimMap.get(TokenProvider.CLAIM_DEVICE_ID).toString()));
        }

        // Create refresh token
        claimMap.put(TokenProvider.CLAIM_REFRESH_TOKEN, true);
        String refreshToken = tokenProvider.createRefreshToken(claimMap, claimMap.get(TokenProvider.CLAIM_SUB).toString());
        jwtToken.setRefreshToken(refreshToken);

        // Create secure transaction
        TravisRsa travisRsa = cacheService.getTravisRsa();
        if (travisRsa == null) {
            travisRsa = new TravisRsa();
            cacheService.setTravisRsa(travisRsa);
        }
        jwtToken.setPublicKey(TravisRsa.getBase64PublicKey(travisRsa.getPublicKey()));

        return ApiResponseEntity.bodyOk().bodyData(jwtToken).build();
    }

    private Map<String, Object> getMapFromClaims(Claims claims) {
        Map<String, Object> expectedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }

    @ApiOperation(value = "Check phone number is valid for registering",
        notes = "return true if phone number is available for registering, otherwise false.",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.UTILITIES)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Phone number is available for registering",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(Constants.Api.Path.Auth.CHECK_PHONE_NUMBER)
    public ApiResponseEntity checkPhoneNumber(@Valid @RequestBody CheckPhoneVM checkPhoneVM) {
        UserDTO userDTO = userService.findByPhone(checkPhoneVM.getPhoneNumber(), checkPhoneVM.getCountryCode());
        return ApiResponseEntity.bodyOk().bodyData(userDTO == null).build();
    }
}

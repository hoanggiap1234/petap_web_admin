package io.petapp.api.controller.admin;

import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.CacheService;
import io.petapp.api.core.Constants;
import io.petapp.api.vm.admin.AdminLoginVM;
import io.petapp.security.jwt.JWTToken;
import io.petapp.security.jwt.TokenProvider;
import io.petapp.utils.encyption.TravisRsa;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(Constants.Api.Path.ADMIN + "/auth")
@RequiredArgsConstructor
@Slf4j
@Api(tags = Constants.Api.Tag.AUTHENTICATION)
@Validated
public class AdminAuthenticationController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final CacheService cacheService;

    @ApiOperation(value = "Sign in",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.AUTHENTICATION)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Login successfully",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(Constants.Api.Path.Auth.LOGIN)
    public ApiResponseEntity login(@Valid @RequestBody AdminLoginVM adminLoginVM) {

//        if (!userService.checkActivatedUser(loginVM.getPhoneNumber())) {
//            throw new BusinessException(ExceptionType.NOT_ACTIVATED_USER);
//        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            adminLoginVM.getUserName(),
            adminLoginVM.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JWTToken jwtToken = new JWTToken();

        // Create access token
        Map<String, Object> claims = new HashMap<>();
//        claims.put(TokenProvider.CLAIM_DEVICE_ID, loginVM.getDevice().getId());
        String accessToken = tokenProvider.createToken(authentication, false, claims);
        jwtToken.setAccessToken(accessToken);

        // Set user's information
        if (!StringUtils.isBlank(accessToken)) {
            ModelMapper modelMapper = new ModelMapper();
//            User.Device device = modelMapper.map(loginVM.getDevice(), User.Device.class);
//            jwtToken.setUser(userService.login(loginVM.getPhoneNumber(), device, null));
        }

        // Create refresh token
        claims.put(TokenProvider.CLAIM_REFRESH_TOKEN, true);
        String refreshToken = tokenProvider.createToken(authentication, false, claims);
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
}

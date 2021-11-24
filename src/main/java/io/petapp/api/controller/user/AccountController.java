/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.controller.user;

import io.petapp.api.commons.ConstUrl;
import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.Constants;
import io.petapp.api.core.MailService;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.enums.VerificationMethod;
import io.petapp.api.enums.VerificationType;
import io.petapp.api.repository.user.UserRepository;
import io.petapp.api.service.user.NotificationService;
import io.petapp.api.service.user.OtpService;
import io.petapp.api.service.user.SettingService;
import io.petapp.api.service.user.UserService;
import io.petapp.api.vm.admin.ManagedUserVM;
import io.petapp.api.vm.admin.RequestTokenVM;
import io.petapp.api.vm.admin.VerifyTokenVM;
import io.petapp.api.vm.user.*;
import io.petapp.security.SecurityUtils;
import io.petapp.security.jwt.JWTFilter;
import io.petapp.security.jwt.TokenProvider;
import io.petapp.utils.Helper;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.Api.Path.PREFIX + "/account")
@Api(tags = Constants.Api.Tag.PROFILE_SETTINGS)
public class AccountController {

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    private final SettingService settingService;

    private final OtpService otpService;

    private final TokenProvider tokenProvider;

    private final HttpServletRequest request;

    private final NotificationService notificationService;

    @ApiOperation(value = "Register new account",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.AUTHENTICATION)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New account successfully registered.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(Constants.Api.Path.Account.REGISTER)
    public ApiResponseEntity registerAccount(@Valid @RequestBody RegisterAccountVM registerAccountVM) {
        if (!Helper.validatePhoneNumber(registerAccountVM.getPhoneNumber())) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }

        userService.registerUser(registerAccountVM);
        return ApiResponseEntity.bodyOk().build();
    }

//    /**
//     * {@code GET  /activate} : activate the registered user.
//     *
//     * @param key the activation key.
//     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
//     */
//    @GetMapping("/activate")
//    public void activateAccount(@RequestParam(value = "key") String key) {
//        Optional<User> user = userService.activateRegistration(key);
//        if (!user.isPresent()) {
//            throw new AccountResourceException("No user was found for this activation key");
//        }
//    }

//    /**
//     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
//     *
//     * @param request the HTTP request.
//     * @return the login if the user is authenticated.
//     */
//    @GetMapping("/authenticate")
//    public String isAuthenticated(HttpServletRequest request) {
//        log.debug("REST request to check if the current user is authenticated");
//        return request.getRemoteUser();
//    }

//    /**
//     * {@code GET  /account} : get the current user.
//     *
//     * @return the current user.
//     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
//     */
//    @GetMapping("/account")
//    public AdminUserDTO getAccount() {
//        return userService
//            .getUserWithAuthorities()
//            .map(AdminUserDTO::new)
//            .orElseThrow(() -> new AccountResourceException("User could not be found"));
//    }

    @ApiOperation(value = "Update logged in user's account information",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.PROFILE_SETTINGS,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User's information successfully updated.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping("/update")
    public ApiResponseEntity saveAccount(@Valid @RequestBody UpdateAccountVM updateAccountVM) {
        String phoneNumber = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(phoneNumber)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        String jwt = JWTFilter.resolveToken(request);
        String idDevice = tokenProvider.getDeviceId(jwt);
        userService.updateUser(updateAccountVM, phoneNumber, idDevice);
        return ApiResponseEntity.bodyOk().build();
//        String userLogin = SecurityUtils
//            .getCurrentUserLogin()
//            .orElseThrow(() -> new AccountResourceException("Current user login not found"));
//        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
//        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
//            throw new EmailAlreadyUsedException();
//        }
//        Optional<User> user = userRepository.findOneByLogin(userLogin);
//        if (!user.isPresent()) {
//            throw new AccountResourceException("User could not be found");
//        }
//        userService.updateUser(
//            userDTO.getFirstName(),
//            userDTO.getLastName(),
//            userDTO.getEmail(),
//            userDTO.getLangKey(),
//            userDTO.getImageUrl()
//        );
    }

    @ApiOperation(value = "Change password of logged in user",
        notes = "Returns 200 if successful. 400 in the remaining cases.",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.PROFILE_SETTINGS,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Password changed.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(path = Constants.Api.Path.Account.CHANGE_PASSWORD)
    public ApiResponseEntity changePassword(@Valid @RequestBody PasswordChangeVM passwordChangeVM) {
        String phoneNumber = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(phoneNumber)) {
            phoneNumber = null;
        }
        userService.changePassword(phoneNumber, passwordChangeVM);
        return ApiResponseEntity.bodyOk().build();
    }

    @ApiOperation(value = "Request OTP for resetting password",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.AUTHENTICATION)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OTP sent for resetting password",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(path = Constants.Api.Path.Account.RESET_PASSWORD_INIT)
    public ApiResponseEntity requestPasswordReset(@RequestBody RequestTokenVM requestTokenVM) {
        Locale locale = RequestContextUtils.getLocale(request);
        String receiver = requestTokenVM.getReceiver();
        if (!Helper.validatePhoneNumber(receiver)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }

        if (!userService.checkActivatedUser(receiver)) {
            throw new BusinessException(ExceptionType.NOT_ACTIVATED_USER);
        }

        return ApiResponseEntity.bodyOk().bodyData(otpService.requestOtp(
                locale,
                receiver,
                VerificationMethod.SMS_OTP.getCode(),
                VerificationType.RESET_PASSWORD_SCREEN.getCode(),
                requestTokenVM.getDeviceId(),
                Helper.getClientIpAddress(request),
                requestTokenVM.getCountryCode()
            )
        ).build();
//        Optional<User> user = userService.requestPasswordReset(mail);
//        if (user.isPresent()) {
//            mailService.sendPasswordResetMail(user.get());
//        } else {
//            // Pretend the request has been successful to prevent checking which emails really exist
//            // but log that an invalid attempt has been made
//            log.warn("Password reset requested for non existing mail");
//        }
    }

    @ApiOperation(value = "Verify OTP for resetting password",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.AUTHENTICATION)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OTP verified for resetting password",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(path = Constants.Api.Path.Account.RESET_PASSWORD_FINISH)
    public ApiResponseEntity finishPasswordReset(@RequestBody VerifyTokenVM vm) {
        return ApiResponseEntity.bodyOk().bodyData(otpService.verifyOtp(vm)).build();
//        if (isPasswordLengthInvalid(keyAndPassword.getNewPassword())) {
//            throw new InvalidPasswordException();
//        }
//        Optional<User> user = userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());
//
//        if (!user.isPresent()) {
//            throw new AccountResourceException("No user was found for this reset key");
//        }
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
                password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH ||
                password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH
        );
    }

    @ApiOperation(value = "Get notifications of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.NOTIFICATION,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "All notifications of logged in user",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping("/notifications")
    public ApiResponseEntity getNotifications(@RequestParam(required = false, defaultValue = "0") Integer page,
                                              @RequestParam(required = false, defaultValue = "10")
                                              @Max(value = 1000, message = Constants.ValidationMessage.INVALID_MAX_VALUE)
                                                  Integer size,
                                              @RequestParam(required = false, defaultValue = "") String search) {
        String phoneNumber = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (phoneNumber == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }
        return ApiResponseEntity.bodyOk().bodyData(notificationService.getAllNotification(phoneNumber, page, size, search)).build();
    }

    @ApiOperation(value = "Get notification's detail by ID",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.NOTIFICATION,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Details of a notification received by logged in user",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping("/notification/{id}")
    public ApiResponseEntity getNotificationDetail(@PathVariable String id) {
        String phoneNumber = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (phoneNumber == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }
        return ApiResponseEntity.bodyOk().bodyData(notificationService.getNotificationById(phoneNumber, id)).build();
    }

    @ApiOperation(value = "Get settings of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.PROFILE_SETTINGS,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Logged in user's Profile Settings (timezone, language, theme..)",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping(ConstUrl.URL_SETTINGS)
    public ApiResponseEntity getSettings() {
        String phoneNumber = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(phoneNumber)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(settingService.getSettings(phoneNumber)).build();
    }

    @ApiOperation(value = "Update setting of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.PROFILE_SETTINGS,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Logged in user's Profile Settings successfully updated",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(ConstUrl.URL_SETTINGS + ConstUrl.URL_UPDATE)
    public ApiResponseEntity updateSetting(@RequestBody UpdateSettingVM updateSettingVM) {
        String phoneNumber = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(phoneNumber)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        settingService.updateSetting(updateSettingVM, phoneNumber);
        return ApiResponseEntity.bodyOk().build();
    }

    @ApiOperation(value = "Get profile's information",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.PROFILE_SETTINGS,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Logged in user's information (name, avatar, nb of devices, country code)",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping(ConstUrl.URL_PROFILE)
    public ApiResponseEntity getInformation() {
        String phoneNumber = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(phoneNumber)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(userService.getInformation(phoneNumber)).build();
    }
}

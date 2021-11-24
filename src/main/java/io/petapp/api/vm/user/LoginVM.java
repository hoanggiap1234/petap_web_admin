/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class LoginVM {

    @Schema(description = "Phone Number of the User.", example = Constants.Api.FieldExample.PHONE_NUMBER, required = true)
    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String phoneNumber;

    @Schema(description = "Password of the User.", example = Constants.Api.FieldExample.PASSWORD, required = true)
    @NotNull
    @Size(min = 6, max = 32, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @Pattern(regexp = "^[\\w\\d$&+,:;=?@#|'<>.^*()%!-]*", message = Constants.ValidationMessage.INVALID_PASSWORD)
    private String password;

    @Schema(description = "Country code for registration purposes.", example = Constants.Api.FieldExample.COUNTRY_CODE, required = true)
    @NotNull
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @Pattern(regexp = "^[\\d]*", message = Constants.ValidationMessage.INVALID_COUNTRY_CODE)
    private String countryCode;

    @Schema(description = "Device that user is logging in.", required = true)
    @NotNull
    private DeviceVM device;

    @Schema(description = "Remember logged in status for a long time.", example = Constants.Api.FieldExample.BOOLEAN)
    private boolean rememberMe;

}

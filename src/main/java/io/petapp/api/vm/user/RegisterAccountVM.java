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
import java.io.Serializable;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/25/21 19:47
 */
@Getter
@Setter
public class RegisterAccountVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Phone number for registration purposes.", example = Constants.Api.FieldExample.PHONE_NUMBER, required = true)
    @NotNull
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String phoneNumber;

    @Schema(description = "Password for registration purposes.", example = Constants.Api.FieldExample.PASSWORD, required = true)
    @NotNull
    @Size(min = 6, max = 32, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @Pattern(regexp = "^[\\w\\d$&+,:;=?@#|'<>.^*()%!-]*", message = Constants.ValidationMessage.INVALID_PASSWORD)
    private String password;

    @Schema(description = "Country code for registration purposes.", example = Constants.Api.FieldExample.COUNTRY_CODE, required = true)
    @NotNull
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @Pattern(regexp = "^[\\d]*", message = Constants.ValidationMessage.INVALID_COUNTRY_CODE)
    private String countryCode;

}

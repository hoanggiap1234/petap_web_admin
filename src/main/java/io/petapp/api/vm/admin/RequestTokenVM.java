/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.admin;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/25/21 00:32
 */
@Getter
@Setter
public class RequestTokenVM {

    @Schema(description = "Phone number for receiving OTP.", example = Constants.Api.FieldExample.PHONE_NUMBER, required = true)
    @NotNull
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String receiver;

    @Schema(description = Constants.Api.FieldDescription.VERIFICATION_REQUEST_RECEIVER, example = Constants.Api.FieldExample.ID, required = true)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String deviceId;

    @Schema(description = "Country code for receiving OTP.", example = Constants.Api.FieldExample.COUNTRY_CODE, required = true)
    @NotNull
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @Pattern(regexp = "^[\\d]*", message = Constants.ValidationMessage.INVALID_COUNTRY_CODE)
    private String countryCode;

}



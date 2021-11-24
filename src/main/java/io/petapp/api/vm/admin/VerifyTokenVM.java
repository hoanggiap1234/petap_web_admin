/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.admin;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

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
public class VerifyTokenVM {

    @Schema(description = Constants.Api.FieldDescription.VERIFICATION_REQUEST_ID, example = Constants.Api.FieldExample.ID, required = true)
    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    @Size(max = 50)
    private String id;

    @Schema(description = Constants.Api.FieldDescription.VERIFICATION_REQUEST_TOKEN, example = Constants.Api.FieldExample.TOKEN, required = true)
    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    @Size(min = 4, max = 50)
    private String token;

    @Schema(description = Constants.Api.FieldDescription.VERIFICATION_REQUEST_TOKEN, example = Constants.Api.FieldExample.ID, required = true)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String deviceId;
}



/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class UpdateSettingVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Time zone of the device.", example = Constants.Api.FieldExample.TIME_ZONE)
    @Size(max = 250, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String timezone;

    @Schema(description = "Language of the device.", example = Constants.Api.FieldExample.LANGUAGE)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String languages;

    @Schema(description = "Length of the device.", example = "10")
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String length;

    @Schema(description = "Weight of the device.", example = Constants.Api.FieldExample.WEIGHT)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String weight;

    @Schema(description = "Volume of the device.", example = "2")
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String volume;

    @Schema(description = "Theme of the device.")
    private UpdateThemeVM theme;
}

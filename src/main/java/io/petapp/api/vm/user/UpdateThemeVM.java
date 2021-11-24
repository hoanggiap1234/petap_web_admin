/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class UpdateThemeVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Choose dark/light mode for the Device.", example = Constants.Api.FieldExample.BOOLEAN)
    private Boolean darkMode;

    @Schema(description = "Choose an application icon for the Device.", example = "1")
    private Integer appIcon;

    @Schema(description = "Choose a color for the Device.", example = "default")
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String color;
}

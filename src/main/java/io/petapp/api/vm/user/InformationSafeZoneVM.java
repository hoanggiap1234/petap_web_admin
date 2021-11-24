/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
public class InformationSafeZoneVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of the safe zone.", example = Constants.Api.FieldExample.ID)
    private String id;

    @Schema(description = "Longitude of the safe zone.", example = Constants.Api.FieldExample.GEOGRAPHIC_LONGITUDE)
    private Double longitude;

    @Schema(description = "Latitude of the safe zone.", example = Constants.Api.FieldExample.GEOGRAPHIC_LATITUDE)
    private Double latitude;

    @Schema(description = "Location of the safe zone.", example = Constants.Api.FieldExample.GEOGRAPHIC_LOCATION, required = true)
    @Size(max = 1000, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String location;

    @Schema(description = "Name of the safe zone.", example = Constants.Api.FieldExample.SAFE_ZONE_NAME, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String name;

    @Schema(description = "Description of the safe zone.", example = Constants.Api.FieldExample.SAFE_ZONE_DESCRIPTION)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String description;

    @Schema(description = "Radius of the safe zone.", example = Constants.Api.FieldExample.RADIUS, required = true)
    @Min(value = 50, message = Constants.ValidationMessage.INVALID_MIN_VALUE)
    @Max(value = 500, message = Constants.ValidationMessage.INVALID_MAX_VALUE)
    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private Double radius;
}

/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class InformationSafeZoneDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Longitude of the safe zone.", example = Constants.Api.FieldExample.GEOGRAPHIC_LONGITUDE)
    private Double longitude;

    @Schema(description = "Latitude of the safe zone.", example = Constants.Api.FieldExample.GEOGRAPHIC_LATITUDE)
    private Double latitude;

    @Schema(description = "Location of the safe zone.", example = Constants.Api.FieldExample.GEOGRAPHIC_LOCATION)
    private String location;

    @Schema(description = "Name of the safe zone.", example = Constants.Api.FieldExample.SAFE_ZONE_NAME)
    private String name;

    @Schema(description = "Description of the safe zone.", example = Constants.Api.FieldExample.SAFE_ZONE_DESCRIPTION)
    private String description;

    @Schema(description = "Radius of the safe zone.",  example = Constants.Api.FieldExample.RADIUS)
    private Double radius;
}

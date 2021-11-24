/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.user;


import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InformationBreedDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of the Breed.", example = Constants.Api.FieldExample.ID)
    private String id;

    @Schema(description = "Name of the Breed.", example = Constants.Api.FieldExample.PET_BREEDS)
    private String name;

    @Schema(description = "Path order of the Breed.")
    private String pathOrder;

    @Schema(description = "Avatar of the Breed.", example = Constants.Api.FieldExample.AVATAR_FILE)
    private String avatar;
}

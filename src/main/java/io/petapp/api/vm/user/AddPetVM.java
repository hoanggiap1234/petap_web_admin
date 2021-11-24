/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;


import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
public class AddPetVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Species of the added pet.", example = Constants.Api.FieldExample.PET_SPECIES, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String species;

    @Schema(description = "Breeds of the added pet.", example = Constants.Api.FieldExample.IDS, required = true)
    private String[] breeds;

    @Schema(description = "Name of the added pet.", example = Constants.Api.FieldExample.PET_NAME, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String name;

    @Schema(description = "Avatar of the added pet.", example = Constants.Api.FieldExample.AVATAR_BASE64)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String avatar;

    @Schema(description = "Microchip of the added pet.", example = Constants.Api.FieldExample.MICROCHIP, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String microchip;

    @Schema(description = "DOB of the added pet.", example = Constants.Api.FieldExample.TIME_STAMP_MILLISECONDS, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private Long dateOfBirth;

    @Schema(description = "Weight of the added pet.", example = Constants.Api.FieldExample.WEIGHT, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private Double weight;

    @Schema(description = "Gender of the added pet.", example = Constants.Api.FieldExample.GENDER, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private Integer gender;

    @Schema(description = "Sterilization status of the added pet.", example = Constants.Api.FieldExample.STERILIZATION_STATUS, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private Integer sterilizationStatus;

}

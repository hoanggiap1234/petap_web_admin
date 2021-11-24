/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class InformationPetVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of the saved pet.", example = Constants.Api.FieldExample.ID)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String id;

    @Schema(description = "Species of the saved pet.", example = Constants.Api.FieldExample.PET_SPECIES, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String species;

    @Schema(description = "Breeds of the saved pet.", example = Constants.Api.FieldExample.IDS, required = true)
    @Size(min = 1, message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String[] breeds;

    @Schema(description = "Name of the saved pet.", example = Constants.Api.FieldExample.PET_NAME, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String name;

    @Schema(description = "Avatar of the saved pet.", example = Constants.Api.FieldExample.AVATAR_BASE64)
    private String avatar;

    @Schema(description = "Microchip of the saved pet.", example = Constants.Api.FieldExample.MICROCHIP, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String microchip;

    @Schema(description = "DOB of the saved pet.", example = Constants.Api.FieldExample.TIME_STAMP_MILLISECONDS, required = true)
    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private Long dateOfBirth;

    @Schema(description = " Weight of the saved pet.", example = Constants.Api.FieldExample.WEIGHT, required = true)
    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private Double weight;

    @Schema(description = "Gender of the saved pet.", example = Constants.Api.FieldExample.GENDER, required = true)
    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private Integer gender;

    @Schema(description = "Sterilization status of the saved pet.", example = Constants.Api.FieldExample.STERILIZATION_STATUS, required = true)
    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private Integer sterilizationStatus;
}

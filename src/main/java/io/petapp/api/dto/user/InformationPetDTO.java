/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.user;


import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Setter
@Getter
public class InformationPetDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of the displayed pet.", example = Constants.Api.FieldExample.ID)
    private String id;

    @Schema(description = "Avatar of the displayed pet.", example = Constants.Api.FieldExample.AVATAR_FILE)
    private String avatar;

    @Schema(description = "Age of the displayed pet.", example = Constants.Api.FieldExample.AGE)
    private Long age;

    @Schema(description = "DOB of the displayed pet.", example = Constants.Api.FieldExample.TIME_STAMP_MILLISECONDS)
    private Long dateOfBirth;

    @Schema(description = "Breeds of the displayed pet.", example = Constants.Api.FieldExample.IDS)
    private String[] breeds;

    @Schema(description = "Gender of the displayed pet.", example = Constants.Api.FieldExample.GENDER)
    private Integer gender;

    @Schema(description = "Name of the displayed pet.", example = Constants.Api.FieldExample.PET_NAME)
    private String name;

    @Schema(description = " Weight of the displayed pet.", example = Constants.Api.FieldExample.WEIGHT)
    private Double weight;

    public Long getAge() {
        return dateOfBirth == null ? null : TimeUnit.MILLISECONDS.toDays(Instant.now().toEpochMilli() - dateOfBirth);
    }

    public void setAge(Long age) {
        this.age = age;
    }
}

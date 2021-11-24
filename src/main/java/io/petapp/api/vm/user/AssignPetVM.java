/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class AssignPetVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of the assigned pet.", example = Constants.Api.FieldExample.ID, required = true)
    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String petId;

    @Schema(description = "Imei of the tracker.", example = Constants.Api.FieldExample.IMEI, required = true)
    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String imei;
}

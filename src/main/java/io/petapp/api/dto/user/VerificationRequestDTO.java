/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/25/21 19:17
 */
@Setter
public class VerificationRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of the request.", example = Constants.Api.FieldExample.ID)
    private String id;
}

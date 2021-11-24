/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @author truongtran
 */
@Getter
@RequiredArgsConstructor
public class FieldError implements Serializable {

    @Schema(description = "Error occurring for this object.", example = "pet")
    private final String objectName;

    @Schema(description = "Error occurring in this field.", example = "name")
    private final String field;

    @Schema(description = "Error message.", example = "Invalid pet's name")
    private final String message;

}

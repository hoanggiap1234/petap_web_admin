/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.enums;

public enum VerificationType {
    REGISTER_SCREEN(1),
    RESET_PASSWORD_SCREEN(2);

    private final Integer code;

    VerificationType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}

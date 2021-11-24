/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.enums;

public enum UserStatus {
    ACTIVATED(1),
    VERIFIED(2),
    TRUSTED(3);

    private final Integer code;

    UserStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

}

/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.enums;

public enum TrackerStatus {
    IN_STOCK(1),
    ASSIGNED(2),
    IN_USED(3),
    UNDER_WARRANTY(4);

    private final Integer code;

    TrackerStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}

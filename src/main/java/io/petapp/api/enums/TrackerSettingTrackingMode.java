/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.enums;

public enum TrackerSettingTrackingMode {
    NORMAL(1),
    CONTINUOUS(2);

    private final Integer code;

    TrackerSettingTrackingMode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}

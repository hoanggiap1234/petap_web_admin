/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.enums;

public enum DeviceTokenType {
    MOBILE_DEVICE_TOKEN(1),
    BROWSER_SUBSCRIPTION(2);

    private final Integer code;

    DeviceTokenType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}

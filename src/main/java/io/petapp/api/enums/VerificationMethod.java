/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.enums;


public enum VerificationMethod {
    SMS_OTP(1),
    EMAIL_OTP(2);

    private final Integer code;
    VerificationMethod(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}

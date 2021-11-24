/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.enums;

public enum VerificationStatus {

    SEND_ERROR(0),
    SEND_NOT_VERIFY(1),
    VERIFY_SUCCESS(2),
    VERIFY_FAILED(3),
    CANCELED(4),
    ACTION_ACCOMPLISHED(5);

    private final Integer code;

    VerificationStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}

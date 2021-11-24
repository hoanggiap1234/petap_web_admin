package io.petapp.api.enums;

public enum UserType {
    SYSTEM_USER(1),
    NORMAL_USER(2),
    MERCHANT_USER(3);

    private final Integer code;

    UserType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}

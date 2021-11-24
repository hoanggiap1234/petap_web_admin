package io.petapp.api.enums;

public enum SocialGroupsPrivacyType {

    PUBLIC(1),
    PRIVATE(2);

    private final Integer code;

    SocialGroupsPrivacyType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}

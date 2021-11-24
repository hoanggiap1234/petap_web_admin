package io.petapp.api.enums;

public enum GroupMemberRole {

    ADMINISTRATOR(1),
    MODERATION(2);

    private final Integer code;

    GroupMemberRole(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}

package io.petapp.api.enums;

public enum CalendarType {

    SOCIAL_GROUP_CALENDAR(1);

    private final Integer code;

    CalendarType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}

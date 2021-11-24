package io.petapp.api.enums;

public enum CalendarRepeatType {
    ONCE(1),
    DAILY(2),
    WEEKLY(3),
    MONTHLY(4),
    ANNUALLY(5);

    private final Integer code;

    CalendarRepeatType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}

/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Application constants.
 */
public final class Constants {

    private Constants() {
    }

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "vi";
    public static final Locale DEFAULT_LOCALE = Locale.forLanguageTag(DEFAULT_LANGUAGE);
    public static final String API_KEY_JWT = "JWT";
    public static final String BASE_PACKAGE = "io.petapp.api";
    public static final String BASE_WEB_URL = "https://petapp.io";

    public static final class Cache {
        public static final List<String> ALL_NAMES = new ArrayList<>();
        public static final String RSA_BY_USER = "rsaByUser";
        public static final String PERMISSIONS_BY_USER = "permissionsByUser";

        static {
            ALL_NAMES.add(RSA_BY_USER);
            ALL_NAMES.add(PERMISSIONS_BY_USER);
        }
    }

    public static final class Api {

        public static class Tag {
            public static final String AUTHENTICATION = "Authentication";
            public static final String DASHBOARD = "Dashboard";
            public static final String PROFILE_SETTINGS = "Profile Settings";
            public static final String DEVICE_MANAGEMENT = "Device Management";
            public static final String PET_MANAGEMENT = "Pet Management";
            public static final String ROLE_MANAGEMENT = "Role Management";
            public static final String CALENDAR_MANAGEMENT = "Calendar Management";
            public static final String NOTIFICATION = "Notification";
            public static final String TRACKING = "Tracking";
            public static final String SAFE_ZONE = "Safe Zone";
            public static final String SOCIAL_GROUP = "Social Group";
            public static final String UTILITIES = "Utilities";
        }

        public static class FieldExample {
            public static final String ID = "d7fecfdeee394a79b849dac42ae20c9a";
            public static final String TOKEN = "123456";
            public static final String PHONE_NUMBER = "84123456789";
            public static final String PASSWORD = "Secret@123";
            public static final String BOOLEAN = "true";
            public static final String INTEGER = "1";
            public static final String ADDRESS = "Đà Nẵng";

            public static final String IP = "127.0.0.1";
            public static final String SESSION_ID = "06383951600dd0fc8713fafd63142fce";

            public static final String AVATAR_FILE = "/path/to/avatar.png";
            public static final String AVATAR_BASE64 = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";

            public static final String PET_SPECIES = "Alaska";
            public static final String PET_BREEDS = "Alaska Akita";
            public static final String PET_NAME = "Khuyển khuyển";
            public static final String AGE = "2";
            public static final String GENDER = "1";
            public static final String WEIGHT = "2.5";
            public static final String STERILIZATION_STATUS = "1";

            public static final String IDS = "['40ea2669874f407f972e271c76b8a60a','f08db909d20341feab8d03104938c32f']";
            public static final String MICROCHIP = "12321314646541321";
            public static final String DAY = "01-01-2021";

            public static final String IMEI = "351756051523999";
            public static final String GEOGRAPHIC_LONGITUDE = "106.696858°";
            public static final String GEOGRAPHIC_LATITUDE = "10.769662°";
            public static final String GEOGRAPHIC_LOCATION = "2 phố Trần Hưng Đạo, TP Hồ Chí Minh";

            public static final String SAFE_ZONE_NAME = "Gia đình";
            public static final String SAFE_ZONE_DESCRIPTION = "Nơi pet đi dạo";
            public static final String RADIUS = "150.0";

            public static final String TRACKER_NAME = "My Cute Tracker";
            public static final String TRACKER_MODEL = "PA-01";
            public static final String TRACKER_STATUS = "1";

            public static final String NOTIFICATION_TYPE = "1";

            public static final String USER_NAME = "whoami";
            public static final String FIRST_NAME = "John";
            public static final String MIDDLE_NAME = "";
            public static final String LAST_NAME = "Doe";
            public static final String LAST_MIDDLE_NAME = "Doe";
            public static final String FULL_NAME = "John Doe";
            public static final String EMAIL = "example@email.com";
            public static final String USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/69.0.3497.105 Mobile/15E148 Safari/605.1";

            public static final String COUNTRY_CODE = "84";
            public static final String TIME_ZONE = "GMT+7";
            public static final String LANGUAGE = "vi";
            public static final String TIME_STAMP_MILLISECONDS = "1629124518000";

        }

        public static class FieldDescription {
            public static final String VERIFICATION_REQUEST_ID = "Unique identifier of the verification request.";
            public static final String VERIFICATION_REQUEST_TOKEN = "Token or OTP of the verification request.";
            public static final String VERIFICATION_REQUEST_RECEIVER = "Phone number or email of the verification request.";
        }

        public static class Path {
            public static final String PREFIX = "/api";

            public static final String PUBLIC = PREFIX + "/public";
            public static final String ADMIN = PREFIX + "/admin";
            public static final String AUTH = PREFIX + "/auth";
            public static final String ACCOUNT = PREFIX + "/account";
            public static final String ON_BOARDING = PUBLIC + "/on-boarding";

            public static class Auth {
                public static final String LOGIN = "/login";
                public static final String REFRESH_TOKEN = "/refresh-token";
                public static final String CHECK_PHONE_NUMBER = "/check-phone-number";
                public static final String OTP = "/otp";
                public static final String OTP_REQUEST = OTP + "/request";
                public static final String OTP_VERIFY = OTP + "/verify";
            }

            public static class Account {
                public static final String REGISTER = "/register";
                public static final String CHANGE_PASSWORD = "/change-password";
                public static final String RESET_PASSWORD = "/reset-password";
                public static final String RESET_PASSWORD_INIT = RESET_PASSWORD + "/init";
                public static final String RESET_PASSWORD_FINISH = RESET_PASSWORD + "/finish";
            }

            public static class Admin {
                public static final String AUTH = ADMIN + "/auth";
            }
        }
    }

    public static class ValidationMessage {
        public static final String FIELD_IS_REQUIRED = "validation.mustNotBeNull";

        public static final String INVALID_PHONE_NUMBER = "validation.invalidPhoneNumber";
        public static final String INVALID_OTP = "validation.invalidOTP";
        public static final String INVALID_PASSWORD = "validation.invalidPassword";
        public static final String INVALID_USER_NAME = "validation.invalidUserName";
        public static final String INVALID_FIRST_NAME = "validation.invalidFirstName";
        public static final String INVALID_LAST_NAME = "validation.invalidLastName";
        public static final String INVALID_COUNTRY_CODE = "validation.invalidCountryCode";
        public static final String EXPIRED_OTP = "validation.expiredOTP";
        public static final String INVALID_DATE = "validation.invalidDate";
        public static final String UNVERIFIED_PHONE_NUMBER = "validation.unverifiedPhoneNumber";

        public static final String NOT_FOUND_RECEIVER = "validation.notFoundReceiver";
        public static final String NOT_FOUND_USER = "validation.notFoundUser";
        public static final String NOT_ACTIVATED_USER = "validation.notActivatedUser";
        public static final String NOT_FOUND_PET = "validation.notFoundPet";
        public static final String NOT_FOUND_NOTIFICATION = "validation.notFoundNotification";
        public static final String NOT_FOUND_TRACKER = "validation.notFoundTracker";
        public static final String CURRENT_PASSWORD_INVALID = "validation.invalidCurrentPassword";
        public static final String NOT_FOUND_BREED = "validation.notFoundBreed";
        public static final String NOT_FOUND_SAFE_ZONE = "validation.notFoundSafeZone";

        public static final String EXISTS_USER = "validation.existsUser";
        public static final String NOT_EXISTS_IMEI = "validation.notExistingImei";
        public static final String TOO_MANY_TIMES_GET_OTP = "validation.tooManyTimesGetOTP";
        public static final String TOO_SHORT_DURATION_BETWEEN_OTP_REQUESTS = "validation.tooShortDurationBetweenOtpRequests";

        public static final String INVALID_MIN_VALUE = "validation.invalidMinValue";
        public static final String INVALID_MAX_VALUE = "validation.invalidMaxValue";
        public static final String INVALID_SIZE_VALUE = "validation.invalidSizeValue";
        public static final String NOT_CONTAIN_WHITE_SPACE = "validation.notContainWhiteSpace";

    }

}

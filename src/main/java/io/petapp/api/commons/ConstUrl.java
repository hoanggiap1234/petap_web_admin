/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.commons;

public class ConstUrl {

    //    ROOT URL
    public static final String URL_USERS = "/users";
    public static final String URL_HOME = "/home";
    public static final String URL_PETS = "/pets";
    public static final String URL_ACCOUNT = "/account";
    public static final String URL_BREEDS = "/breeds";
    public static final String URL_TRACKERS = "/trackers";
    public static final String URL_SAFE_ZONES = "/safe-zones";
    public static final String URL_SOCIAL_GROUPS = "/social-groups";

    //    CRUD URL
    public static final String URL_ADD = "/add";
    public static final String URL_SAVE = "/save";
    public static final String URL_DELETE = "/delete/{id}";
    public static final String URL_UPDATE = "/update";
    public static final String URL_VERIFY_IMEI = "/verify-imei";
    public static final String URL_ASSIGN_PET = "/assign-pet";
    public static final String URL_CHECK_DEVICE_ASSIGNED_PET = "/check-device-assigned-pet";

    //    CHILD URL
    public static final String URL_LOGIN = "/login";
    public static final String URL_OTP_BY_PHONE = "/otp-by-phone";
    public static final String URL_OTP_VERIFY = "/otp-verify";
    public static final String URL_SETTINGS = "/settings";
    public static final String URL_INFORMATION = "/{id}";
    public static final String URL_PROFILE = "/profile";
    public static final String URL_TRACKING_HISTORY = "/tracking-history";

}

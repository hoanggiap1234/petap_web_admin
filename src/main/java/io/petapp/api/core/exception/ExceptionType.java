/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core.exception;

import io.petapp.api.core.Constants;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;

/**
 * @author truongtran
 */
@Getter
public enum ExceptionType {
    FORBIDDEN("exception.forbidden", null),
    UNAUTHORIZED("exception.unauthorized", null),
    INSECURE_API("exception.insecureApi", null),
    INVALID_PHONE_NUMBER("validation.invalidPhoneNumber", null),
    INVALID_PASSWORD("validation.invalidPassword", "/invalid-password"),
    NOT_FOUND_USER("validation.notFoundUser", null),
    NOT_ACTIVATED_USER ("validation.notActivatedUser", null),
    ALREADY_IN_USE_PHONE_NUMBER ("validation.alreadyInUsePhoneNumber", null),
    NOT_FOUND_NOTIFICATION ("validation.notFoundNotification", null),
    TOO_SHORT_DURATION_BETWEEN_OTP_REQUESTS ("validation.tooShortDurationBetweenOtpRequests", null),
    TOO_MANY_TIMES_GET_OTP ("validation.tooManyTimesGetOTP", null),
    TOO_MANY_TIMES_GET_OTP_DEVICE ("validation.tooManyTimesGetOTPDevice", null),
    NOT_FOUND_RECEIVER ("validation.notFoundReceiver", null),
    NOT_FOUND_PET ("validation.notFoundPet", null),
    NOT_FOUND_BREED("validation.notFoundBreed", null),
    NOT_FOUND_SAFE_ZONE("validation.notFoundSafeZone", null),
    NOT_FOUND_TRACKER("validation.notFoundTracker", null),
    NOT_FOUND_ROLE ("validation.notFoundRole", null),
    NOT_EXISTS_IMEI("validation.notExistingImei", null),
    UNVERIFIED_PHONE_NUMBER ("validation.unverifiedPhoneNumber", null),
    CURRENT_PASSWORD_INVALID("validation.invalidCurrentPassword", null),
    EXISTS_USER("validation.existsUser", null),
    INVALID_OTP("validation.invalidOTP", null),
    TOO_LONG_INTERVAL_TRACKER_HISTORY("validation.tooLongIntervalTrackerHistory", null),
    INVALID_MIN_VALUE("validation.invalidMinValue", null),
    INVALID_MAX_VALUE("validation.invalidMaxValue", null),
    INVALID_DATE("validation.invalidDate", null),
    NOT_FOUND_SOCIAL_GROUP ("validation.notFoundSocialGroup", null),
    INCORRECT_TRACKER_INFORMATION ("validation.incorrectTrackerInformation", null),
    ALREADY_IN_USE_TRACKER ("validation.alreadyInUseTracker", null),
    ALREADY_POSSESSED_TRACKER ("validation.alreadyPossessedTracker", null),
    TOO_MANY_TIMES_GET_OTP_IP("validation.tooManyTimesGetOTPIp", null),
    TOO_MANY_PUBLIC_GROUPS_PER_DAY("validation.tooManyPublicGroupsPerDay", null),
    EXPIRED_OTP ("validation.expiredOTP", null);

    public static final String TITLE_BUSINESS_EXCEPTION = "problem.title.exception.business";
    public static final String TITLE_SECURE_API_EXCEPTION = "problem.title.exception.secureApi";
    public static final String TITLE_METHOD_ARGUMENT_NOT_VALID = "problem.title.methodArgumentNotValid";

    public static final String EXCEPTION_HTTP_PREFIX = "problem.exception.http.";
    public static final String EXCEPTION_VALIDATION = "problem.exception.validation";
    public static final String EXCEPTION_CONCURRENCY_FAILURE = "problem.exception.concurrencyFailure";

    public static final String PROBLEM_BASE_URL = Constants.BASE_WEB_URL + "/technical-problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/business-exception");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");

    private final String messageKey;
    private final String problemUrl;
    private URI problemUri;

    ExceptionType(String messageKey, String problemUrl) {
        this.messageKey = messageKey;
        this.problemUrl = StringUtils.defaultString(problemUrl);
        if (!StringUtils.isBlank(problemUrl))
            this.problemUri = URI.create(PROBLEM_BASE_URL + problemUrl);
    }

}

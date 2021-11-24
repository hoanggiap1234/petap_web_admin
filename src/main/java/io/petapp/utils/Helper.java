/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Helper {

    public static final int OTP_LENGTH = 4;
    public static final String[] CLIENT_IP_HEADERS = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"
    };

    public static boolean isEmail(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        try {
            Pattern pattern = Pattern.compile("(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)");
            Matcher matcher = pattern.matcher(s.trim());
            if (matcher.find()) {
                return true;
            }
        } catch (Exception e) {
            log.error(null, e);
        }
        return false;
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            boolean isValid = false;
            String expression = "([+]?[-]?[0-9\\-]?){9,11}[0-9]$";
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(phoneNumber);
            if (phoneNumber.length() >= 9 && phoneNumber.length() <= 11) {
                if (matcher.matches()) {
                    isValid = true;
                }
            }
            return isValid;
        }
        return false;
    }

    /**
     * Generate a CRON expression is a string comprising 6 or 7 fields separated by white space.
     *
     * @param seconds    mandatory = yes. allowed values = {@code  0-59    * / , -}
     * @param minutes    mandatory = yes. allowed values = {@code  0-59    * / , -}
     * @param hours      mandatory = yes. allowed values = {@code 0-23   * / , -}
     * @param dayOfMonth mandatory = yes. allowed values = {@code 1-31  * / , - ? L W}
     * @param month      mandatory = yes. allowed values = {@code 1-12 or JAN-DEC    * / , -}
     * @param dayOfWeek  mandatory = yes. allowed values = {@code 0-6 or SUN-SAT * / , - ? L #}
     * @param year       mandatory = no. allowed values = {@code 1970–2099    * / , -}
     * @return a CRON Formatted String.
     */
    public static String generateCronExpression(
        String seconds,
        String minutes,
        String hours,
        String dayOfMonth,
        String month,
        String dayOfWeek,
        String year) {
        return String.format("%1$s %2$s %3$s %4$s %5$s %6$s %7$s",
            seconds, minutes, hours, dayOfMonth, month, dayOfWeek, year);
    }

    public static String formatPhone(String phone, String countryCode) {
        if (phone.charAt(0) == '0') {
            phone = phone.substring(1);
        }
        return countryCode + phone;
    }

    public static String generateRandomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static String generateOtp() {
        return generateRandomNumber(OTP_LENGTH);
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : CLIENT_IP_HEADERS) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

}

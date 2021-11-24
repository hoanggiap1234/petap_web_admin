/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core;

import io.petapp.config.ApplicationProperties;
import io.pitagon.fpt.sms.brandname.SmsBrandNameClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
@Slf4j
public class SmsBrandNameService {

    private static final String SMS_CONTENT_KEY = "sms.brandname.otp";
    private final ApplicationProperties applicationProperties;
    private final MessageSource messageSource;

    @Async
    public void sendOtp(String phone, String otp, Locale locale) {
        try {
            SmsBrandNameClient.createClient()
                .mode(applicationProperties.getSmsBrandName().getMode())
                .clientSecret(applicationProperties.getSmsBrandName().getClientSecret())
                .clientId(applicationProperties.getSmsBrandName().getClientId())
                .brandName(applicationProperties.getSmsBrandName().getBrandName())
                .createInstance()
                .sendSmsOtp(
                    phone,
                    messageSource.getMessage(
                        SMS_CONTENT_KEY,
                        new Object[]{
                            applicationProperties.getSmsBrandName().getBrandName(),
                            otp,
                            applicationProperties.getSmsBrandName().getAppName(),
                            applicationProperties.getSmsBrandName().getValidityInMinute()
                        },
                        locale
                    ),
                    applicationProperties.getSmsBrandName().getRequestId());
        } catch (Exception ex) {
            log.error("sendOtp | " + ex.getMessage(), ex);
        }
    }

}

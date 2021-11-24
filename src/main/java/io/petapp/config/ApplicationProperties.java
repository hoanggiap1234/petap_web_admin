/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to PetApp Service.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    @Getter
    private final SmsBrandName smsBrandName = new SmsBrandName();

    @Getter
    @Setter
    public static class SmsBrandName {
        private String brandName;
        private String appName;
        private String clientId;
        private String clientSecret;
        private Integer validityInMinute;
        private String mode;
        private String requestId;

        public String getBrandName() {
            return brandName == null ? null : brandName.toUpperCase();
        }

        public String getAppName() {
            return appName == null ? null : appName.toUpperCase();
        }

        public String getRequestId() {
            return requestId == null ? null : requestId.toUpperCase();
        }
    }

}

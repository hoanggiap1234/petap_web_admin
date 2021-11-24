/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.config;

import io.petapp.api.core.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

    private static final String HEADER_CONTENT_LANGUAGE = "Content-Language";
    private static final Locale DEFAULT_LOCALE = Constants.DEFAULT_LOCALE;
    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(
        DEFAULT_LOCALE,
        Locale.ENGLISH
    );

    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver() {
            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                if (StringUtils.isBlank(request.getHeader(HEADER_CONTENT_LANGUAGE))) {
                    return DEFAULT_LOCALE;
                }
                List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader(HEADER_CONTENT_LANGUAGE));
                return Locale.lookup(list, SUPPORTED_LOCALES);
            }
        };

        localeResolver.setDefaultLocale(DEFAULT_LOCALE);
        return localeResolver;
    }
}

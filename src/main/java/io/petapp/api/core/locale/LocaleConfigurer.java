/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core.locale;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author truongtran
 */
@RequiredArgsConstructor
@Configuration
public class LocaleConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final LocaleResolver localeResolver;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new LocaleFilter(localeResolver), UsernamePasswordAuthenticationFilter.class);
    }
}

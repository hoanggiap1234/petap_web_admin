/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core.locale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

/**
 * @author truongtran
 */
@Slf4j
@RequiredArgsConstructor
public class LocaleFilter extends GenericFilterBean {

    private final LocaleResolver localeResolver;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        try {
            if (localeResolver != null) {
                Locale locale = localeResolver.resolveLocale(httpServletRequest);
                LocaleContextHolder.setLocale(locale);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        filterChain.doFilter(servletRequest, servletResponse);
        if (localeResolver != null) {
            LocaleContextHolder.resetLocaleContext();
        }
    }

}

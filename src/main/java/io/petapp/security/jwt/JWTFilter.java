/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.petapp.api.core.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_BEARER = "Bearer ";
    public static final String EXCEPTION = "exception";
    public static final String ATTR_CLAIMS = "claims";

    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        try {
            // JWT Token is in the form "Bearer token". Remove Bearer word and
            // get only the Token
            String jwt = resolveToken(httpServletRequest);
            if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
                String requestURL = httpServletRequest.getRequestURL().toString();
                if (requestURL.contains(Constants.Api.Path.Auth.REFRESH_TOKEN)) {
                    Jws<Claims> jws = this.tokenProvider.getJwsClaims(jwt);
                    throw new ExpiredJwtException(jws.getHeader(), jws.getBody(), null);
                } else {
                    Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredJwtException ex) {
            Claims claims = ex.getClaims();
            boolean isRefreshToken = claims != null
                && claims.containsKey(TokenProvider.CLAIM_REFRESH_TOKEN)
                && (boolean) claims.get(TokenProvider.CLAIM_REFRESH_TOKEN);
            String requestURL = httpServletRequest.getRequestURL().toString();
            // allow for Refresh Token creation if following conditions are true.
            if (isRefreshToken && requestURL.contains(Constants.Api.Path.Auth.REFRESH_TOKEN)) {
                allowForRefreshToken(ex, httpServletRequest);
            } else {
                httpServletRequest.setAttribute(EXCEPTION, ex);
            }
        } catch (BadCredentialsException ex) {
            httpServletRequest.setAttribute(EXCEPTION, ex);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create new JWT
        request.setAttribute(ATTR_CLAIMS, ex.getClaims());
    }

    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTHORIZATION_BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

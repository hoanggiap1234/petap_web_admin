/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.security.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.petapp.api.core.CacheService;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.core.security.OperationType;
import io.petapp.api.core.security.Permission;
import io.petapp.api.core.security.ResourceType;
import io.petapp.utils.encyption.TravisAes;
import io.petapp.utils.encyption.TravisRsa;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author truongtran
 */
@Component
@Slf4j
public class ApiInterceptor implements HandlerInterceptor {

    public static final String ATTR_PLAIN_DATA = "plainData";
    public static final String ATTR_SECRET = "secret";
    public static final String PARAM_SECRET = "secret";
    public static final String PARAM_DATA = "data";

    @Autowired
    private CacheService cacheService;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            SecureApi secureApi = handlerMethod.getMethod().getAnnotation(SecureApi.class);
            if (secureApi != null) {
                if (secureApi.authorized()) {
                    if (!hasPermission(secureApi.permissions())) {
                        throw new SecureApiException(ExceptionType.FORBIDDEN);
                    }
                }
                if (secureApi.encrypted() && isSecureMethod(request)) {
                    String requestData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                    String plainData = getPlainData(request, requestData);
                    request.setAttribute(ATTR_PLAIN_DATA, plainData);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request,
                           @NotNull HttpServletResponse response,
                           @NotNull Object handler,
                           ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request,
                                @NotNull HttpServletResponse response,
                                @NotNull Object handler,
                                Exception ex) {
    }

    private static boolean isSecureMethod(HttpServletRequest request) {
        return isMethod(request.getMethod(), HttpMethod.POST, HttpMethod.PUT);
    }

    private static boolean isMethod(String method, HttpMethod... httpMethods) {
        if (httpMethods.length == 0) {
            return false;
        }
        for (HttpMethod httpMethod : httpMethods) {
            if (httpMethod != null && httpMethod.matches(method)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPermission(ApiPermissions apiPermissions) {
        List<Permission> permissions = cacheService.getPermissions();
        if (apiPermissions == null || ObjectUtils.isEmpty(apiPermissions.value()) || ObjectUtils.isEmpty(permissions)) {
            return false;
        }
        ApiPermission[] apiPermissionsArr = apiPermissions.value();
        boolean hasPermission = false;
        for (ApiPermission apiPermission : apiPermissionsArr) {
            hasPermission = false;
            if (apiPermission == null) {
                return false;
            }
            ResourceType resource = apiPermission.resource();
            OperationType[] operations = apiPermission.operations();
            if (StringUtils.isBlank(resource.name()) || ObjectUtils.isEmpty(operations)) {
                return false;
            }
            String resourceName = resource.name();
            for (OperationType operation : operations) {
                hasPermission = false;
                String operationName = operation == null ? "" : operation.name();
                if (StringUtils.isBlank(operationName)) {
                    return false;
                }
                for (Permission permission : permissions) {
                    if (resourceName.equalsIgnoreCase(permission.getResource())
                        && operationName.equalsIgnoreCase(permission.getOperation())) {
                        hasPermission = true;
                        break;
                    }
                }
                if (!hasPermission) {
                    return false;
                }
            }
            if (!hasPermission) {
                return false;
            }
        }
        return hasPermission;
    }

    private String getPlainData(HttpServletRequest request, String requestData) throws BusinessException {
        try {
            HashMap<String, Object> requestMap = new ObjectMapper().readValue(requestData, HashMap.class);
            if (!requestMap.containsKey(PARAM_SECRET) || !requestMap.containsKey(PARAM_DATA)) {
                throw new SecureApiException(ExceptionType.INSECURE_API);
            }
            String encryptedData = (String) requestMap.get(PARAM_DATA);
            String encryptedSecret = (String) requestMap.get(PARAM_SECRET);
            TravisRsa travisRsa = cacheService.getTravisRsa();
            String secret = travisRsa.decrypt(encryptedSecret);
            request.setAttribute(ATTR_SECRET, secret);
            TravisAes aesUtil = new TravisAes();
            return aesUtil.decrypt(secret, encryptedData);
        } catch (Exception e) {
            throw new SecureApiException(ExceptionType.INSECURE_API);
        }
    }

}

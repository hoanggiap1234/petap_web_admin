/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.security.api;

import io.petapp.api.core.exception.ExceptionProblem;
import io.petapp.api.core.exception.ExceptionType;
import lombok.Getter;
import org.zalando.problem.Status;

/**
 * @author truongtran
 */
@Getter
public class SecureApiException extends ExceptionProblem {

    public SecureApiException(ExceptionType exceptionType) {
        super(exceptionType, ExceptionType.TITLE_SECURE_API_EXCEPTION, Status.FORBIDDEN);
    }

    public SecureApiException(String messageKey) {
        super(null, ExceptionType.TITLE_SECURE_API_EXCEPTION, Status.FORBIDDEN, messageKey);
    }
}

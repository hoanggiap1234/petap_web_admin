/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core.exception;

import lombok.Getter;
import org.zalando.problem.Status;

/**
 * @author truongtran
 */
@Getter
public class BusinessException extends ExceptionProblem {

    public BusinessException(ExceptionType exceptionType) {
        super(exceptionType, ExceptionType.TITLE_BUSINESS_EXCEPTION, Status.BAD_REQUEST);
    }

    public BusinessException(String messageKey) {
        super(null, ExceptionType.TITLE_BUSINESS_EXCEPTION, Status.BAD_REQUEST, messageKey);
    }
}

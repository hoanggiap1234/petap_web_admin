/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core.exception;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * @author truongtran
 */
@Getter
public class ExceptionProblem extends AbstractThrowableProblem {

    protected final String message;

    public ExceptionProblem(ExceptionType exceptionType, String title, Status status) {
        super(exceptionType == null ? null : exceptionType.getProblemUri(), title, status);
        this.message = exceptionType == null ? "" : exceptionType.getMessageKey();
    }

    public ExceptionProblem(ExceptionType exceptionType, String title, Status status, String messageKey) {
        super(exceptionType == null ? null : exceptionType.getProblemUri(), title, status);
        this.message = messageKey;
    }

}

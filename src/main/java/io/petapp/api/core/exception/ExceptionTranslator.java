/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core.exception;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.zalando.problem.*;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;
import tech.jhipster.config.JHipsterConstants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 *
 * @author truongtran
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    private static final String KEY_FIELD_ERRORS = "fieldErrors";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_PATH = "path";
    private static final String KEY_VIOLATIONS = "violations";

    private final Environment env;
    private final MessageSource messageSource;

    private Locale getLocale(NativeWebRequest request) {
        return RequestContextUtils.getLocale(Objects.requireNonNull(request.getNativeRequest(HttpServletRequest.class)));
    }

    private String translate(NativeWebRequest request, String key) {
        return messageSource.getMessage(key, null, getLocale(request));
    }

    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, @NotNull NativeWebRequest request) {
        if (entity == null) {
            return null;
        }
        HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
        String requestUri = nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;
        Problem problem = entity.getBody();

        if (problem instanceof ExceptionProblem) {
            ProblemBuilder builder = Problem
                .builder()
                .withType(problem.getType())
                .withStatus(problem.getStatus())
                .withTitle(translate(request, problem.getTitle()))
                .with(KEY_PATH, requestUri)
                .with(KEY_MESSAGE, translate(request, ((ExceptionProblem) problem).getMessage()));
            return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
        }

        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }

        ProblemBuilder builder = Problem
            .builder()
            .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ExceptionType.DEFAULT_TYPE : problem.getType())
            .withStatus(problem.getStatus())
            .withTitle(translate(request, problem.getTitle()))
            .with(KEY_PATH, requestUri);

        if (problem instanceof ConstraintViolationProblem) {
            builder
                .with(KEY_VIOLATIONS, ((ConstraintViolationProblem) problem).getViolations())
                .with(KEY_MESSAGE, translate(request, ExceptionType.EXCEPTION_VALIDATION));
        } else {
            builder.withCause(((DefaultProblem) problem).getCause()).withDetail(problem.getDetail()).withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);
            if (!problem.getParameters().containsKey(KEY_MESSAGE) && problem.getStatus() != null) {
                builder.with(KEY_MESSAGE, translate(request, ExceptionType.EXCEPTION_HTTP_PREFIX + problem.getStatus().getStatusCode()));
            }
        }
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result
            .getFieldErrors()
            .stream()
            .map(
                f ->
                    new FieldError(
                        f.getObjectName()
                            .replaceFirst("DTO$", "")
                            .replaceFirst("VM$", ""),
                        f.getField(),
                        translate(request, StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode())
                    )
            )
            .collect(Collectors.toList());

        Problem problem = Problem
            .builder()
            .withType(ExceptionType.CONSTRAINT_VIOLATION_TYPE)
            .withTitle(translate(request, ExceptionType.TITLE_METHOD_ARGUMENT_NOT_VALID))
            .withStatus(defaultConstraintViolationStatus())
            .with(KEY_MESSAGE, translate(request, ExceptionType.EXCEPTION_VALIDATION))
            .with(KEY_FIELD_ERRORS, fieldErrors)
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .withStatus(Status.CONFLICT)
            .with(KEY_MESSAGE, translate(request, ExceptionType.EXCEPTION_CONCURRENCY_FAILURE))
            .build();
        return create(ex, problem, request);
    }

    @Override
    public ProblemBuilder prepare(final Throwable throwable, final StatusType status, final URI type) {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            if (throwable instanceof HttpMessageConversionException) {
                return Problem
                    .builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Unable to convert http message")
                    .withCause(Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null));
            }
            if (throwable instanceof DataAccessException) {
                return Problem
                    .builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Failure during data access")
                    .withCause(Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null));
            }
            return Problem
                .builder()
                .withType(type)
                .withTitle(status.getReasonPhrase())
                .withStatus(status)
                .withDetail("Unexpected runtime exception")
                .withCause(Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null));
        }

        return Problem
            .builder()
            .withType(type)
            .withTitle(status.getReasonPhrase())
            .withStatus(status)
            .withDetail(throwable.getMessage())
            .withCause(Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null));
    }

}

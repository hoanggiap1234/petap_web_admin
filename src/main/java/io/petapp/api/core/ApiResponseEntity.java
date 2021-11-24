/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;

@Getter
public class ApiResponseEntity<T, U> extends ResponseEntity<T> implements Serializable {

    private U bodyData;
    private String bodyMessage;

    public ApiResponseEntity(HttpStatus status) {
        super(status);
    }

    public ApiResponseEntity(T body, HttpStatus status) {
        super(body, status);
        this.bodyData = (U) body;
    }

    public ApiResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ApiResponseEntity(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
        this.bodyData = (U) body;
    }

    public ApiResponseEntity(T body, MultiValueMap<String, String> headers, int rawStatus) {
        super(body, headers, rawStatus);
        this.bodyData = (U) body;
    }

    public static ApiResponseEntity bodyNotFound() {
        return new ApiResponseEntity(HttpStatus.NOT_FOUND);
    }

    public static ApiResponseEntity bodyForbidden() {
        return new ApiResponseEntity(HttpStatus.FORBIDDEN);
    }

    public static ApiResponseEntity bodyError() {
        return new ApiResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ApiResponseEntity bodyBad() {
        return new ApiResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public static ApiResponseEntity bodyOk() {
        return new ApiResponseEntity(HttpStatus.OK);
    }

    public ApiResponseEntity bodyMessage(String message) {
        this.bodyMessage = message;
        return this;
    }

    public ApiResponseEntity bodyData(U data) {
        this.bodyData = data;
        return this;
    }

    public ApiResponseEntity build() {
        return new ApiResponseEntity<>(
            new BodyResponse<>(getStatusCode().value(), bodyMessage, bodyData),
            getHeaders(),
            getStatusCode());
    }

    @Getter
    @RequiredArgsConstructor
        public static class BodyResponse<U> implements Serializable {

        @Schema(description = "HTTP status code of the response.", example = "200")
        private final int status;
        @Schema(description = "Message of the response.", example = "message.successful")
        private final String message;
        @Schema(description = "Data object of the response.", example = "{}")
        private final U data;
    }

}

/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.admin;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/25/21 19:17
 */
@Setter
@Getter
public class VerificationRequestDTO implements Serializable {

    private String id;
    private String otp;
}

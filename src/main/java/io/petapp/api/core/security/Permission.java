/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author truongtran
 */
@Getter
@RequiredArgsConstructor
public class Permission {
    private final String resource;
    private final String operation;
}

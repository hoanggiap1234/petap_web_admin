/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.security.api;

import io.petapp.api.core.security.OperationType;
import io.petapp.api.core.security.ResourceType;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author truongtran
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, TYPE, ANNOTATION_TYPE})
@Repeatable(ApiPermissions.class)
public @interface ApiPermission {

    ResourceType resource();

    OperationType[] operations();

}

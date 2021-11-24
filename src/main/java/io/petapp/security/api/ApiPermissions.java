/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.security.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author truongtran
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, TYPE, ANNOTATION_TYPE})
public @interface ApiPermissions {

    ApiPermission[] value();

}

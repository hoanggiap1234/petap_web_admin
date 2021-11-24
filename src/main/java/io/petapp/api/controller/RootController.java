/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.controller;

import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.Constants;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.DefaultProblem;

/**
 * @author truongtran
 */
@Tag(name = Constants.Api.Tag.AUTHENTICATION, description = "The APIs for signing in / signing up / reset password.")
@Tag(name = Constants.Api.Tag.DASHBOARD, description = "The APIs for signing in / signing up / reset password.")
@Tag(name = Constants.Api.Tag.PROFILE_SETTINGS, description = "The APIs for viewing and setting profile.")
@Tag(name = Constants.Api.Tag.DEVICE_MANAGEMENT, description = "The APIs for managing devices.")
@Tag(name = Constants.Api.Tag.PET_MANAGEMENT, description = "The APIs for managing pets.")
@Tag(name = Constants.Api.Tag.CALENDAR_MANAGEMENT, description = "The APIs for managing calendars.")
@Tag(name = Constants.Api.Tag.NOTIFICATION, description = "The APIs for managing notification.")
@Tag(name = Constants.Api.Tag.TRACKING, description = "The APIs for tracking module.")
@Tag(name = Constants.Api.Tag.SAFE_ZONE, description = "The APIs for managing safe zones.")
@Tag(name = Constants.Api.Tag.SOCIAL_GROUP, description = "The APIs for managing social group.")
@Tag(name = Constants.Api.Tag.UTILITIES, description = "The utility APIs.")
@RestController
@RequestMapping(Constants.Api.Path.PREFIX)
@RequiredArgsConstructor
public class RootController {

    @ApiOperation(value = "", hidden = true, response = DefaultProblem.class)
    @GetMapping("/")
    public ApiResponseEntity get() {
        return ApiResponseEntity.bodyOk().build();
    }
}

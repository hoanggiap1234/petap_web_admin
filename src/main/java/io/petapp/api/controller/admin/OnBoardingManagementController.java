/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.controller.admin;

import io.petapp.api.core.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/26/21 13:36
 */
@RestController
@RequestMapping(Constants.Api.Path.ADMIN + "/on-boarding")
@RequiredArgsConstructor
@Slf4j
public class OnBoardingManagementController {
}

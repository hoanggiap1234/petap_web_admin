/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.controller.user;

import io.petapp.api.commons.ConstUrl;
import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.service.user.HomeService;
import io.petapp.security.SecurityUtils;
import io.petapp.api.core.ApiResponseEntity;
import io.petapp.utils.Helper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.Api.Path.PREFIX + ConstUrl.URL_HOME)
@RequiredArgsConstructor
@Slf4j
@Api(tags = Constants.Api.Tag.DASHBOARD)
public class HomeController {

    private final HomeService homeService;

    @ApiOperation(value = "Get dashboard of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.DASHBOARD,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return home page (dashboard) with user's all pets information",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping()
    public ApiResponseEntity getInformationHome() {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(homeService.getInformationHome(userName)).build();
    }

}

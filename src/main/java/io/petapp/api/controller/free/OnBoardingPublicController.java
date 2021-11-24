/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.controller.free;

import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.Constants;
import io.petapp.api.dto.admin.OnBoardingDTO;
import io.petapp.api.service.user.OnBoardingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/26/21 13:39
 */
@RestController
@RequestMapping(Constants.Api.Path.ON_BOARDING)
@RequiredArgsConstructor
@Slf4j
@Api(tags = Constants.Api.Tag.UTILITIES)
public class OnBoardingPublicController {

    private final OnBoardingService onBoardingService;

    @ApiOperation(value = "Get all currently active on boarding screens",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.UTILITIES)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping("/")
    public ApiResponseEntity getAllActive() {
        List<OnBoardingDTO> onBoardingDTOList = onBoardingService.getAllActive();
        return ApiResponseEntity.bodyOk().bodyData(onBoardingDTOList).build();
    }

}

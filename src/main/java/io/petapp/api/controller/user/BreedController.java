/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.controller.user;

import io.petapp.api.commons.ConstUrl;
import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.service.user.BreedService;
import io.petapp.security.SecurityUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;

@RestController
@RequestMapping(Constants.Api.Path.PREFIX + ConstUrl.URL_BREEDS)
@RequiredArgsConstructor
@Slf4j
@Api(tags = Constants.Api.Tag.PET_MANAGEMENT)
public class BreedController {

    private final BreedService breedService;

    @ApiOperation(value = "Get list of breeds",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.PET_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return a list of breeds",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping()
    public ApiResponseEntity getListInformation(@RequestParam(required = false) String parentId,
                                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                                @RequestParam(required = false, defaultValue = "10")
                                                @Max(value = 1000, message = Constants.ValidationMessage.INVALID_MAX_VALUE)
                                                    Integer size,
                                                @RequestParam(required = false, defaultValue = "") String search) {

        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }
        return ApiResponseEntity.bodyOk().bodyData(breedService.getListBreed(parentId, page, size, search)).build();
    }
}

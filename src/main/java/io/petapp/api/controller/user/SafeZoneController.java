/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.controller.user;

import io.petapp.api.commons.ConstUrl;
import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.service.user.SafeZoneService;
import io.petapp.api.vm.user.InformationSafeZoneVM;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;

@RestController
@RequestMapping(Constants.Api.Path.PREFIX + ConstUrl.URL_SAFE_ZONES)
@RequiredArgsConstructor
@Slf4j
@Api(tags = Constants.Api.Tag.SAFE_ZONE)
public class SafeZoneController {

    private final SafeZoneService safeZoneService;

    @ApiOperation(value = "Get safe zones of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.SAFE_ZONE,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return all safe zones of logged in user.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping()
    public ApiResponseEntity getListSafeZones(@RequestParam(required = false, defaultValue = "0") Integer page,
                                              @RequestParam(required = false, defaultValue = "10")
                                              @Max(value = 1000, message = Constants.ValidationMessage.INVALID_MAX_VALUE)
                                                  Integer size,
                                              @RequestParam(required = false, defaultValue = "") String search) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(safeZoneService.getListSafeZones(userName, page, size, search)).build();
    }

    @ApiOperation(value = "Create/Update safe zone of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.SAFE_ZONE,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return a new added safe zone/ or updated information of an existing SZ.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(ConstUrl.URL_SAVE)
    public ApiResponseEntity saveSafeZone(@Valid @RequestBody InformationSafeZoneVM informationSafeZoneVM) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        safeZoneService.saveSafeZone(informationSafeZoneVM, userName);
        return ApiResponseEntity.bodyOk().build();
    }

    @ApiOperation(value = "Get a safe zone's information of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.SAFE_ZONE,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return detailed information of one specific safe zone.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping(ConstUrl.URL_INFORMATION)
    public ApiResponseEntity getInformation(@PathVariable String id) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(safeZoneService.getInformation(userName, id)).build();
    }

    @ApiOperation(value = "Delete a safe zone of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.SAFE_ZONE,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return isDeleted = true for a removed safe zone.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(ConstUrl.URL_DELETE)
    public ApiResponseEntity deleteSafeZone(@PathVariable String id) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);

        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }

        safeZoneService.deleteSafeZone(userName, id);
        return ApiResponseEntity.bodyOk().build();
    }
}

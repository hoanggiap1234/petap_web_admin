/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.controller.user;

import io.petapp.api.commons.ConstUrl;
import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.service.user.PetService;
import io.petapp.api.vm.user.InformationPetVM;
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
@RequestMapping(Constants.Api.Path.PREFIX + ConstUrl.URL_PETS)
@RequiredArgsConstructor
@Slf4j
@Api(tags = Constants.Api.Tag.PET_MANAGEMENT)
public class PetController {

    private final PetService petService;

    @ApiOperation(value = "Get pets of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.PET_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return logged in user's all pets",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping()
    public ApiResponseEntity getListInformation(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                @RequestParam(required = false, defaultValue = "10")
                                                @Max(value = 1000, message = Constants.ValidationMessage.INVALID_MAX_VALUE)
                                                    Integer size,
                                                @RequestParam(required = false, defaultValue = "") String search) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(petService.getListPet(userName, page, size, search)).build();
    }

    @ApiOperation(value = "Get logged in user's pet information",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.PET_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return logged in user's one specific pet information",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping(ConstUrl.URL_INFORMATION)
    public ApiResponseEntity getInformation(@PathVariable String id) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(petService.getInformation(userName, id)).build();
    }

    @ApiOperation(value = "Save logged in user's pet information",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.PET_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return a new added pet/ or updated information of an existing pet",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(ConstUrl.URL_SAVE)
    public ApiResponseEntity savePet(@Valid @RequestBody InformationPetVM informationPetVM) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);

        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        petService.savePet(informationPetVM, userName);
        return ApiResponseEntity.bodyOk().build();
    }

    @ApiOperation(value = "Delete a pet of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.PET_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return isDeleted = false for a removed pet ",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(ConstUrl.URL_DELETE)
    public ApiResponseEntity deletePet(@PathVariable String id) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);

        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        petService.deletePet(id, userName);
        return ApiResponseEntity.bodyOk().build();
    }
}

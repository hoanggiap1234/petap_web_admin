/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.controller.user;

import com.sun.org.apache.bcel.internal.Const;
import io.petapp.api.commons.ConstUrl;
import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.dto.user.InformationTrackerDTO;
import io.petapp.api.service.user.TrackerGPSHistoryService;
import io.petapp.api.service.user.TrackerService;
import io.petapp.api.vm.user.AssignPetVM;
import io.petapp.api.vm.user.UpdateTrackerVM;
import io.petapp.api.vm.user.VerifyImeiVM;
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
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.Api.Path.PREFIX + ConstUrl.URL_TRACKERS)
@Api(tags = Constants.Api.Tag.DEVICE_MANAGEMENT)
@Validated
public class TrackerController {

    private final TrackerService trackerService;

    private final TrackerGPSHistoryService trackerGPSHistoryService;

    private final Integer INTERVAL_TRACKING_HISTORY = 30;


    @ApiOperation(value = "Get trackers of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.DEVICE_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return all trackers of logged in user.",
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

        return ApiResponseEntity.bodyOk().bodyData(trackerService.getListTracker(userName, page, size, search)).build();
    }

    @ApiOperation(value = "Get a tracker's information of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.DEVICE_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return detailed information of one specific tracker.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping(ConstUrl.URL_INFORMATION)
    public ApiResponseEntity getInformation(@PathVariable String id) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(trackerService.getInformation(userName, id)).build();
    }

    @ApiOperation(value = "Create/Update a tracker of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.DEVICE_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return a new tracker/ or updated information of a tracker.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(ConstUrl.URL_UPDATE)
    public ApiResponseEntity updateTracker(@Valid @RequestBody UpdateTrackerVM informationTrackerVM) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }

        trackerService.updateTracker(userName, informationTrackerVM);
        return ApiResponseEntity.bodyOk().build();
    }

    @ApiOperation(value = "Verify IMEI of a tracker when adding new device",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.DEVICE_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return information of the added device, associated with the given IMEI number.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(ConstUrl.URL_VERIFY_IMEI)
    public ApiResponseEntity verifyImei(@Valid @RequestBody VerifyImeiVM verifyImeiVM) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(trackerService.verifyImei(userName, verifyImeiVM.getImei())).build();
    }

    @ApiOperation(value = "Assign a pet for a device of logged in user",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.DEVICE_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "An ID tracker is assigned to a pet.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(ConstUrl.URL_ASSIGN_PET)
    public ApiResponseEntity assignPet(@RequestBody AssignPetVM assignPetVM) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        trackerService.assignPet(userName, assignPetVM);
        return ApiResponseEntity.bodyOk().build();
    }

    @ApiOperation(value = "Check that the device has already assigned for a pet",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.DEVICE_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return true if the device is already assigned, false if otherwise.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(ConstUrl.URL_CHECK_DEVICE_ASSIGNED_PET)
    public ApiResponseEntity checkDeviceAssignedPet(@RequestParam(value = "petId") String id) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(trackerService.checkDeviceAssignedPet(userName, id)).build();
    }

    @ApiOperation(value = "Get GPS history",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.DEVICE_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return true if the device is already assigned, false if otherwise.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping(ConstUrl.URL_TRACKING_HISTORY)
    public ApiResponseEntity getListTrackerGPSHistory(@Valid @RequestParam("idTracker")
                                                      @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
                                                      @Size(max = 50, message = Constants.ValidationMessage.INVALID_MAX_VALUE)
                                                          String idTracker,
                                                      @RequestParam Long fromDate,
                                                      @RequestParam Long toDate) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);

        if (fromDate > toDate) {
            throw new BusinessException(ExceptionType.INVALID_DATE);
        }

        if (TimeUnit.DAYS.convert(toDate - fromDate, TimeUnit.MILLISECONDS) > INTERVAL_TRACKING_HISTORY) {
            throw new BusinessException(ExceptionType.TOO_LONG_INTERVAL_TRACKER_HISTORY);
        }

        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
        }
        return ApiResponseEntity.bodyOk().bodyData(trackerGPSHistoryService.getListTrackerHistory(userName, idTracker, fromDate, toDate)).build();
    }
}

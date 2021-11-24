package io.petapp.api.controller.admin;

import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.service.admin.AdminUserService;
import io.petapp.api.vm.admin.AdminRegisterAccountVM;
import io.petapp.api.vm.user.RegisterAccountVM;
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
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.Api.Path.ADMIN + "/account")
@Api(tags = Constants.Api.Tag.PROFILE_SETTINGS)
@Validated
public class AdminAccountController {

    private final AdminUserService adminUserService;

    @ApiOperation(value = "Get list account",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.AUTHENTICATION,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List account",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping()
    @Valid
    public ApiResponseEntity getListAccount(
        @RequestParam(required = false, defaultValue = "0") Integer page,
        @RequestParam(required = false, defaultValue = "10")
        @Max(value = 100, message = Constants.ValidationMessage.INVALID_MAX_VALUE)
            Integer size,
        @RequestParam(required = false, defaultValue = "") String search,
        @RequestParam(required = false, defaultValue = "id,asc") String[] sort
    ) {

//        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);

        return ApiResponseEntity.bodyOk().bodyData(adminUserService.getListInformation("", page, size, search, sort)).build();
    }

    @ApiOperation(value = "Register new account",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.AUTHENTICATION)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New account successfully registered.",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(Constants.Api.Path.Account.REGISTER)
    public ApiResponseEntity registerAccount(@Valid @RequestBody AdminRegisterAccountVM adminRegisterAccountVM) {

        adminUserService.registerUser(adminRegisterAccountVM);
        return ApiResponseEntity.bodyOk().build();
    }
}

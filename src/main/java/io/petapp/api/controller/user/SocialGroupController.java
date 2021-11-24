package io.petapp.api.controller.user;

import io.petapp.api.commons.ConstUrl;
import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.service.user.SocialGroupService;
import io.petapp.api.vm.user.SocialGroupVM;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Constants.Api.Path.PREFIX + ConstUrl.URL_SOCIAL_GROUPS)
@RequiredArgsConstructor
@Slf4j
@Api(tags = Constants.Api.Tag.SOCIAL_GROUP)
public class SocialGroupController {

    private final SocialGroupService socialGroupService;

    @ApiOperation(value = "Add a new social group",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.SOCIAL_GROUP,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return a new added social group",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(ConstUrl.URL_ADD)
    public ApiResponseEntity addSocialGroup(@Valid @RequestBody SocialGroupVM socialGroupVM) {
        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);

        if (!Helper.validatePhoneNumber(userName)) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        socialGroupService.addSocialGroup(socialGroupVM, userName);
        return ApiResponseEntity.bodyOk().build();
    }
}

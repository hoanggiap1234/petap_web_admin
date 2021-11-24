package io.petapp.api.controller.admin;

import io.petapp.api.commons.ConstUrl;
import io.petapp.api.core.ApiResponseEntity;
import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.dto.admin.RoleDTO;
import io.petapp.api.model.admin.Role;
import io.petapp.api.service.admin.RoleService;
import io.petapp.api.vm.admin.ManagedRoleVM;
import io.petapp.security.SecurityUtils;
import io.petapp.utils.Helper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jodd.net.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;

@RestController
@RequestMapping("/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * api: add new role
     * if dupplicate role code throw
     * @param roleVM
     * @return
     * @author GiapHN
     */
    @ApiOperation(value = "Add logged in Role information",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.ROLE_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return a new Role ",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(value = "role/add")
    public ApiResponseEntity createRole(@Valid @RequestBody ManagedRoleVM roleVM) {
        try {

            String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
            if (!Helper.validatePhoneNumber(userName)) {
                throw new BusinessException(ExceptionType.NOT_FOUND_USER);
            }

            if (roleService.findBycode(roleVM.getCode().trim()) == false) {

                RoleDTO roleDTO = roleService.createRoles(roleVM);

                return ApiResponseEntity.bodyOk().bodyData(roleDTO).build();

            } else {

                throw new BusinessException(ExceptionType.EXCEPTION_VALIDATION);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * api: update role
     * if dupplicate role code => update role
     * else if not founded role code => add new role
     * @param roleVM
     * @return
     * @author GiapHN
     */
    @ApiOperation(value = "update logged in Role information",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.ROLE_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return a Role with old role id  ",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(value = "role/update")
    public ApiResponseEntity updateRole(@Valid @RequestBody ManagedRoleVM roleVM) {
        try {

//            String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
//            if (!Helper.validatePhoneNumber(userName)) {
//                throw new BusinessException(ExceptionType.NOT_FOUND_USER);
//            }

            RoleDTO roleDTO = new RoleDTO();

            if (roleService.findBycode(roleVM.getCode().trim()) == true) {
                roleDTO = roleService.updateRole(roleVM);
            } else {
                roleDTO = roleService.createRoles(roleVM);
            }

            return ApiResponseEntity.bodyOk().bodyData(roleDTO).build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get Role by id
     * @param id
     * @return
     * @author GiapHN
     */

    @ApiOperation(value = "Get logged in role's information",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.ROLE_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return logged in role's information",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping(value = "/api/role/{id}")
    public ApiResponseEntity getRoleById(@PathVariable String id) {
//        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
//        if (!Helper.validatePhoneNumber(userName)) {
//            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
//        }
//        return ApiResponseEntity.bodyOk().bodyData(roleService.getRoleById(userName, id)).build();
        return ApiResponseEntity.bodyOk().bodyData(roleService.getRoleById(id)).build();
    }

    /**
     * Get list Role with pagination, not find some infor role
     * @param page
     * @param size
     * @return
     * @author GiapHN
     */
//    @ApiOperation(value = "Get list role of logged in user",
//        response = ApiResponseEntity.BodyResponse.class,
//        tags = Constants.Api.Tag.ROLE_MANAGEMENT,
//        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return logged in user's all roles",
//        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping(value = "/api/role")
    public ApiResponseEntity getListRoles(@RequestParam(required = false, defaultValue = "0") Integer page,
                                          @RequestParam(required = false, defaultValue = "10")
                                          @Max(value = 1000, message = Constants.ValidationMessage.INVALID_MAX_VALUE)
                                              Integer size){
//        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
//        if (!Helper.validatePhoneNumber(userName)) {
//            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
//        }
//        return ApiResponseEntity.bodyOk().bodyData(roleService.getLisRoles(userName, page, size, search)).build();
        return ApiResponseEntity.bodyOk().bodyData(roleService.getLisRoles(page, size)).build();
    }

    /**
     *
     * @param page
     * @param size
     * @param roleVM
     * @return
     * @author GiapHN
     */
//    @ApiOperation(value = "Get list role of logged in user with params search",
//        response = ApiResponseEntity.BodyResponse.class,
//        tags = Constants.Api.Tag.ROLE_MANAGEMENT,
//        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return logged in user's all roles",
//        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @GetMapping(value = "/api/role/search")
    public ApiResponseEntity getListRolesWithParamSearch(@RequestParam(required = false, defaultValue = "0") Integer page,
                                          @RequestParam(required = false, defaultValue = "10")
                                          @Max(value = 1000, message = Constants.ValidationMessage.INVALID_MAX_VALUE)
                                              Integer size, @RequestBody ManagedRoleVM roleVM){
//        String userName = SecurityUtils.getCurrentUserLogin().orElse(null);
//        if (!Helper.validatePhoneNumber(userName)) {
//            throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
//        }
//        return ApiResponseEntity.bodyOk().bodyData(roleService.getLisRoles(userName, page, size, search)).build();
        return ApiResponseEntity.bodyOk().bodyData(roleService.getLisRolesWithParamSearch(page, size, roleVM)).build();
    }

    /**
     *
     * @param id
     * @return
     * @author GiapHN
     */
    @ApiOperation(value = "Delete a role of logged in Role",
        response = ApiResponseEntity.BodyResponse.class,
        tags = Constants.Api.Tag.ROLE_MANAGEMENT,
        authorizations = {@Authorization(value = Constants.API_KEY_JWT)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return isDeleted = false for a removed role ",
        content = @Content(schema = @Schema(implementation = ApiResponseEntity.BodyResponse.class)))})
    @PostMapping(value = "/api/role/deleted/{id}")
    public Boolean deleteRole(@PathVariable String id) {
        Boolean status = false;
        try {
            String phoneNumber = SecurityUtils.getCurrentUserLogin().orElse(null);

            if (!Helper.validatePhoneNumber(phoneNumber)) {
                throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
            }

            roleService.deleteRole(id, phoneNumber);

            status = true;

        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }
        return status;

    }

}

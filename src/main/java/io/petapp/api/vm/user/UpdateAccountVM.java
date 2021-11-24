/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.petapp.api.core.Constants;
import io.petapp.api.dto.user.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/25/21 19:50
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateAccountVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Display name of the user for updating purposes.", example = Constants.Api.FieldExample.USER_NAME, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String displayName;

    @Schema(description = "Avatar of the user for updating purposes.", example = Constants.Api.FieldExample.AVATAR_BASE64)
    private String avatar;

    @Schema(description = "Type of the Device.", example = "1", required = true, hidden = true)
    private Boolean bioAuth;
}

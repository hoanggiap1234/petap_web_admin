/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InformationUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Display name of the user.", example = Constants.Api.FieldExample.USER_NAME)
    private String displayName;

    @Schema(description = "Country code of the user.", example = Constants.Api.FieldExample.COUNTRY_CODE)
    private String countryCode;

    @Schema(description = "Avatar of the user.", example = Constants.Api.FieldExample.AVATAR_FILE)
    private String avatar;

    @Schema(description = "Number of devices of the user.", example = "1")
    private Integer countDevice;
}

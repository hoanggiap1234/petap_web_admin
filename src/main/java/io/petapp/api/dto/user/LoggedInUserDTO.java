/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/25/21 00:32
 */
@Data
@NoArgsConstructor
public class LoggedInUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of the logged in user.", example = Constants.Api.FieldExample.ID)
    private Long userId;

    @Schema(description = "IP of the logged in user.", example = Constants.Api.FieldExample.IP)
    private String ip;

    @Schema(description = "User agent of the logged in user.", example = Constants.Api.FieldExample.USER_AGENT)
    private String userAgent;

    @Schema(description = "Id session of the logged in user.", example = Constants.Api.FieldExample.SESSION_ID)
    private String sessionId;

}

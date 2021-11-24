/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/26/21 14:05
 */
@Setter
@Getter
public class NotificationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of the notification.", example = Constants.Api.FieldExample.ID)
    private String id;

    @Schema(description = "ID of the current user.", example = Constants.Api.FieldExample.ID)
    private String userId;

    @Schema(description = "Title of the notification.", example = "New device has been authorized")
    private String title;

    @Schema(description = "Content of the notification.", example = "You have successfully authorized a new device")
    private String content;

    @Schema(description = "Data of the notification.", example = "New device has been authorized")
    private String data;

    @Schema(description = "Type of the notification.", example = Constants.Api.FieldExample.NOTIFICATION_TYPE)
    private Integer type;

    @Schema(description = "The notification has been read or not.", example = Constants.Api.FieldExample.BOOLEAN)
    private Boolean read;

}

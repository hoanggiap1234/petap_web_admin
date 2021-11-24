/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.user;

import io.petapp.api.core.Constants;
import io.petapp.api.model.user.TrackerInformation;
import io.petapp.api.model.user.TrackerMacPositions;
import io.petapp.api.model.user.TrackerSetting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
public class InformationTrackerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of the tracker.", example = Constants.Api.FieldExample.ID)
    private String id;

    @Schema(description = "Name of the tracker.", example = Constants.Api.FieldExample.TRACKER_NAME)
    private String name;

    @Schema(description = "Avatar of the tracker.", example = Constants.Api.FieldExample.AVATAR_FILE)
    private String avatar;

    @Schema(description = "IMEI of the tracker.", example = Constants.Api.FieldExample.IMEI)
    private String imei;

    @Schema(description = "Model of the tracker.", example = Constants.Api.FieldExample.TRACKER_MODEL)
    private String model;

    @Schema(description = "Settings of the tracker.")
    private TrackerSetting settings;

    @Schema(description = "Latest information of the tracker.")
    private TrackerInformation latestInformation;

    @Schema(description = "Mac Positions of the tracker.")
    private TrackerMacPositions macPositions;

    @Schema(description = "Status of the tracker.", example = Constants.Api.FieldExample.TRACKER_STATUS)
    private Integer status;

    @Schema(description = "The tracker is activated or not.", example = Constants.Api.FieldExample.BOOLEAN)
    private Boolean activated;
}

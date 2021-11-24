/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.petapp.api.core.Constants;
import io.petapp.api.model.user.TrackerInformation;
import io.petapp.api.model.user.TrackerMacPositions;
import io.petapp.api.model.user.TrackerSetting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddTrackerVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of the tracker.", example = Constants.Api.FieldExample.ID)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String id;

    @Schema(description = "Name of the tracker.", example = Constants.Api.FieldExample.TRACKER_NAME)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String name;

    @Schema(description = "Model of the tracker.", example = Constants.Api.FieldExample.TRACKER_MODEL)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String model;

    @Schema(description = "Avatar of the tracker.", example = Constants.Api.FieldExample.AVATAR_FILE)
    private String avatar;

    @Schema(description = "Settings of the tracker.")
    private TrackerSetting settings;

    @Schema(description = "Latest information of the tracker.")
    private TrackerInformation latestInformation;

    @Schema(description = "Mac Positions of the tracker.")
    private TrackerMacPositions macPositions;
}

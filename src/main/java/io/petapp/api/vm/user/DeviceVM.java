/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
public class DeviceVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of the Device.", example = "d7fecfdeee394a79b849dac42ae20c9a", required = true)
    @NotNull
    private String id;

    @Schema(description = "Name of the Device.", example = "iPhone 12")
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String name;

    @Schema(description = "Type of the Device.", example = "1", required = true)
    @NotNull
    private Integer type;

    @Schema(description = "OS of the Device.", example = "iOS 14.3")
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String os;

    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String agent;

    @Schema(description = "Vendor of the Device.", example = "Apple")
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String vendor;

    @Schema(description = "Model of the Device.", example = "A1687")
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String model;

    @Schema(description = "Type of the Device.", example = "1", required = true, hidden = true)
    @Field("bio_auth")
    private Boolean bioAuth;
}

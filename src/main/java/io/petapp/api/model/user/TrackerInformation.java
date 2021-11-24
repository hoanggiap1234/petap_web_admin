/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerInformation {
    private Integer battery;
    private Double signalStrength;
    private Boolean onlineStatus;
}

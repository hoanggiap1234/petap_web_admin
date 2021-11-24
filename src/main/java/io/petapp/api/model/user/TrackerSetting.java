/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerSetting {
    private Boolean led;
    private Boolean notification;
    private Integer batteryThreshold;
    private Integer trackingMode;
}

/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerMacPositions {
    private String mac;
    private Double latitude;
    private Double longitude;
}

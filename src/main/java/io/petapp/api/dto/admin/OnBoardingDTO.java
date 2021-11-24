/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.admin;

import io.petapp.api.core.entity.IdentifiedEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/26/21 13:49
 */
@Getter
@Setter
public class OnBoardingDTO extends IdentifiedEntity implements Serializable {

    private String id;
    private String name;
    private Long startTime;
    private Long endTime;
    private String description;
    private String imageData;
    private String imageUrl;
    private Boolean activated;
    private Integer sortOrder;

}

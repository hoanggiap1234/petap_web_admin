/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.model.admin;

import io.petapp.api.core.entity.IdentifiedEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/26/21 13:49
 */
@Getter
@Setter
@Document(collection = "on_boardings")
public class OnBoarding extends IdentifiedEntity implements Serializable {

    private String name;
    @Field("start_time")
    private Long startTime;
    @Field("end_time")
    private Long endTime;
    private String description;
    @Field("image_data")
    private String imageData;
    @Field("image_url")
    private String imageUrl;
    private Boolean activated;
    @Field("sort_order")
    private Integer sortOrder;

}

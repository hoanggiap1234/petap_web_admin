/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.model.user;

import io.petapp.api.core.entity.IdentifiedEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;

@Getter
@Setter
@Document(collection = "trackers")
public class Tracker extends IdentifiedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ReadOnlyProperty
    private User user;

    @Field(targetType = FieldType.OBJECT_ID, name = "user_id")
    private String userId;

    @Field(name = "name")
    private String name;

    @Field(name = "avatar")
    private String avatar;

    @Field(name = "imei")
    private String imei;

    @Field(name = "model")
    private String model;

    @Field(name = "activated")
    private Boolean activated;

    @Field(name = "status")
    private Integer status;

    @Field(name = "settings")
    private TrackerSetting settings;

    @Field(name = "mac_positions")
    private TrackerMacPositions macPositions;

    @Field(name = "latest_information")
    private TrackerInformation latestInformation;
}

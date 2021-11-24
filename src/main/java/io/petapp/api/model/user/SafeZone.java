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
@Document(collection = "safe_zones")
public class SafeZone extends IdentifiedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ReadOnlyProperty
    private User user;

    @Field(targetType = FieldType.OBJECT_ID, name = "user_id")
    private String userId;

    @Field(name = "longitude")
    private Double longitude;

    @Field(name = "latitude")
    private Double latitude;

    @Field(name = "location")
    private String location;

    @Field(name = "name")
    private String name;

    @Field(name = "description")
    private String description;

    @Field(name = "radius")
    private Double radius;
}

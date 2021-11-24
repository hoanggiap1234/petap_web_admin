/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.model.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/26/21 14:05
 */
@Getter
@Setter
@Document(collection = "notifications")
public class Notification implements Serializable {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Field(targetType = FieldType.OBJECT_ID, name = "user_id")
    private String userId;
    private String title;
    private String content;
    private String data;
    private Integer type;
    private Boolean read;

}

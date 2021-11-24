/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.model.admin;

import io.petapp.api.core.entity.AuditingEntity;
import io.petapp.api.model.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.List;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/25/21 19:11
 */
@Getter
@Setter
@Document(collection = "roles")
public class Role extends AuditingEntity implements Serializable {

    @ReadOnlyProperty
    private User user;

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name = "code")
    private String code;

    @Field(name = "name")
    private String name;

    @Field(name = "description")
    private String description;

    @Field(name = "activated")
    private Boolean activated;

    @Field(name = "permissions")
    private List<Permissions> permissions;

    @Field(name = "menus")
    private List<Menu> menus;

}

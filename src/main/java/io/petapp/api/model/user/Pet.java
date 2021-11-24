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

/**
 * @author truongtran
 * @version 1.0
 * @date 7/26/21 14:31
 */
@Getter
@Setter
@Document(collection = "pets")
public class Pet extends IdentifiedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ReadOnlyProperty
    private User user;

    @Field(targetType = FieldType.OBJECT_ID, name = "user_id")
    private String userId;

    @Field(name = "avatar")
    private String avatar;

    @Field(name = "name")
    private String name;

    @Field(name = "biography")
    private String biography;

    @Field(name = "address")
    private String address;

    @Field(name = "pet_id_card")
    private String petIdCard;

    @Field(name = "qr_code")
    private String qrCode;

    @Field(targetType = FieldType.OBJECT_ID, name = "species")
    private String species;

    @Field(targetType = FieldType.OBJECT_ID, name = "breeds")
    private String[] breeds;

    @Field(name = "microchip")
    private String microchip;

    @Field(name = "weight")
    private Double weight;

    @Field(name = "date_of_birth")
    private Long dateOfBirth;

    @Field(name = "gender")
    private Integer gender;

    @Field(name = "status")
    private Integer status;

    @Field(name = "sterilization_status")
    private Integer sterilizationStatus;

    @Field(targetType = FieldType.OBJECT_ID, name = "tracker_id")
    private String trackerId;
}

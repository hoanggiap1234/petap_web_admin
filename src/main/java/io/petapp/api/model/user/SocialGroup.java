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
@Document(collection = "social_groups")
public class SocialGroup extends IdentifiedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field(name = "avatar")
    private String avatar;

    @Field(name = "address")
    private String address;

    @Field(name = "longitude")
    private Double longitude;

    @Field(name = "latitude")
    private Double latitude;

    @Field(name = "name")
    private String name;

    @Field(name = "description")
    private String description;

    @Field(targetType = FieldType.OBJECT_ID, name = "breeds")
    private String[] breeds;

    @Field(name = "activity_type")
    private String[] activityType;

    @Field(name = "privacy_type")
    private Integer privacyType;

    @Field(targetType = FieldType.OBJECT_ID, name = "calendars")
    private String[] calendars;

}

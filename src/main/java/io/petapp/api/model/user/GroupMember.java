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
@Document(collection = "group_members")
public class GroupMember extends IdentifiedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ReadOnlyProperty
    private User user;

    @Field(targetType = FieldType.OBJECT_ID, name = "user_id")
    private String userId;

    @ReadOnlyProperty
    private SocialGroup socialGroup;

    @Field(targetType = FieldType.OBJECT_ID, name = "social_group_id")
    private String socialGroupId;

    @Field(name = "is_owner")
    private Boolean isOwner;

    @Field(name = "role")
    private Integer role;

}

package io.petapp.api.model.user;

import io.petapp.api.core.entity.IdentifiedEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;

@Getter
@Setter
@Document(collection = "calendars")
public class Calendar extends IdentifiedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field(targetType = FieldType.OBJECT_ID, name = "item_id")
    private String itemId;

    @Field(name = "type")
    private Integer type;

    @Field(name = "name")
    private String name;

    @Field(name = "description")
    private String description;

    @Field(name = "repeat_type")
    private Integer repeatType;

    @Field(name = "repeat_expression")
    private String repeatExpression;

    @Field(name = "reminder")
    private Long[] reminder;
}

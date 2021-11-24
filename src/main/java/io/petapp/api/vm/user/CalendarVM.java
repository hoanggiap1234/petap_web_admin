package io.petapp.api.vm.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;

@Getter
@Setter
public class CalendarVM implements Serializable {

    private static final long serialVersionUID = 1L;


    private String itemId;


    private Integer type;


    private String name;


    private String description;


    private Integer repeatType;


    private String repeatExpression;


    private Long[] reminder;

}

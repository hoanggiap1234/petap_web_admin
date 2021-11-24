/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public abstract class HierarchyEntity extends BasicEntity implements Serializable {

    @Indexed
    @Field(name = "parent_id")
    private String parentId;

    @Indexed
    @Field(name = "sort_order")
    private String sortOrder;

    @Indexed
    private Integer level;

    @Field(name = "children_count")
    private Integer childrenCount;

    @Indexed
    @Field(name = "path_id")
    private String pathId;

    @Field(name = "path_code")
    private String pathCode;

    @Field(name = "path_name")
    private String pathName;

    @Indexed
    @Field(name = "path_order")
    private String pathOrder;

}

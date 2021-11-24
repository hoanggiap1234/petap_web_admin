/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.model.user;

import io.petapp.api.core.entity.HierarchyEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "breeds")
@Getter
@Setter
public class Breed extends HierarchyEntity implements Serializable {

    private static final long serialVersionUID = 1L;
}

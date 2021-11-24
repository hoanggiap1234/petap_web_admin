/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

/**
 * Base abstract class for entities which will hold definitions for created, last modified, created by,
 * last modified by attributes.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AuditingEntity implements Serializable {

    @JsonIgnore
    @CreatedBy
    @Field("created_by")
    private String createdBy;

    @CreatedDate
    @JsonIgnore
    @Field("created_date")
    private Long createdDate = Instant.now().toEpochMilli();

    @JsonIgnore
    @LastModifiedBy
    @Field("last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @JsonIgnore
    @Field("last_modified_date")
    private Long lastModifiedDate = Instant.now().toEpochMilli();

    @JsonIgnore
    @Field(name = "deleted_date")
    private Long deletedDate;

    @JsonIgnore
    @Field(name = "deleted_by")
    private String deletedBy;

    @Indexed
    private Boolean deleted;

}

/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.model.admin;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/25/21 19:17
 */
@Getter
@Setter
@Document(collection = "verification_requests")
public class VerificationRequest implements Serializable {

    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID, name = "user_id")
    private String userId;

    @NotNull
    @Indexed
    private String receiver;

    @Indexed
    @Field(name = "device_id")
    private String deviceId;

    @Indexed
    @Field(name = "ip")
    private String ip;

    private String token;

    @Indexed
    private Integer method;

    @Field("requested_date")
    private Long requestedDate;

    @Field("validity_time")
    private Integer validityTime;

    @Indexed
    private Integer type;

    @Indexed
    private Integer status;

}

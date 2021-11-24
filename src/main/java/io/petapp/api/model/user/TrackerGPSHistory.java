package io.petapp.api.model.user;

import io.petapp.api.core.entity.IdentifiedEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;

@Getter
@Setter
@Document(collection = "tracker_gps_history")
public class TrackerGPSHistory extends IdentifiedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field(targetType = FieldType.OBJECT_ID, name = "tracker_id")
    @Indexed
    private String trackerId;

    @Field(targetType = FieldType.OBJECT_ID, name = "user_id")
    @Indexed
    private String userId;

    @Field(targetType = FieldType.OBJECT_ID, name = "pet_id")
    @Indexed
    private String petId;

    @Field(name = "longitude")
    private Double longitude;

    @Field(name = "latitude")
    private Double latitude;

    @Field(name = "is_gsm_location")
    private Boolean gsmLocation;

    @Field(name = "alt")
    private Double alt;

    @Field(name = "speed")
    private Double speed;

    @Field(name = "dir")
    private Double dir;

    @Field(name = "hdop")
    private Double hdop;

    @Field(name = "mil")
    private Double mil;

    @Field(name = "satellites")
    private Double satellites;

    @Field(name = "utc_time")
    @Indexed
    private Long utcTime;

}

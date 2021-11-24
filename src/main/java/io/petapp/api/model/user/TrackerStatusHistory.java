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
@Document(collection = "tracker_status_history")
public class TrackerStatusHistory extends IdentifiedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field(targetType = FieldType.OBJECT_ID, name = "tracker_id")
    private String trackerId;

    @Field(targetType = FieldType.OBJECT_ID, name = "user_id")
    private String userId;

    @Field(name = "is_gps_location")
    private Boolean gpsLocation;

    @Field(name = "is_wifi_location")
    private Boolean wifiLocation;

    @Field(name = "is_gsm_location")
    private Boolean gsmLocation;

    @Field(name = "is_ble_location")
    private Boolean bleLocation;

    @Field(name = "is_smart_location")
    private Boolean smartLocation;

    @Field(name = "is_beacon_location")
    private Boolean beaconLocation;

    @Field(name = "utc_time")
    private Long utcTime;

    @Field(name = "signal_strength")
    private Double signalStrength;

    @Field(name = "battery_level")
    private Double batteryLevel;

}

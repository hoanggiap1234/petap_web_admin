package io.petapp.api.dto.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Setter
@Getter
public class TrackerGPSHistoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field(name = "pet_id")
    @Schema(description = "Pet ID.", example = Constants.Api.FieldExample.ID)
    private String petId;

    @Schema(description = "Longitude of the tracker.", example = Constants.Api.FieldExample.GEOGRAPHIC_LONGITUDE)
    private Double longitude;

    @Schema(description = "Latitude of the tracker.", example = Constants.Api.FieldExample.GEOGRAPHIC_LATITUDE)
    private Double latitude;

    @Field(name = "utc_time")
    @Schema(description = "UTC time of the tracker.", example = Constants.Api.FieldExample.TIME_STAMP_MILLISECONDS)
    private Long utcTime;

}

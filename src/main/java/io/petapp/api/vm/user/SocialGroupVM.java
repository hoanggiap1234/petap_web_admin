package io.petapp.api.vm.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class SocialGroupVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String avatar;

    @Size(max = 1000, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String address;

    @Schema(description = "Longitude of the group geographic position.", example = Constants.Api.FieldExample.GEOGRAPHIC_LONGITUDE)
    private Double longitude;

    @Schema(description = "Latitude of the group geographic position.", example = Constants.Api.FieldExample.GEOGRAPHIC_LATITUDE)
    private Double latitude;

    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String name;

    @Size(max = 2000, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String description;

    @Size(min = 1, message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String[] breeds;

    @Size(min = 1, message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String[] activityType;

    @Size(min = 1, message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String[] calendars;

    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private Integer privacyType;
}

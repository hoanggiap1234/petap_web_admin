package io.petapp.api.vm.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class VerifyImeiVM {
    @Schema(description = "Imei of the tracker.", example = Constants.Api.FieldExample.IMEI)
    @Size(max = 16, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @NotBlank(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    private String imei;
}

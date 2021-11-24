package io.petapp.api.vm.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class CheckPhoneVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Phone number - check its availability for registration purposes.", example = Constants.Api.FieldExample.PHONE_NUMBER, required = true)
    @NotBlank
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String phoneNumber;

    @Schema(description = "Country code for registration purposes.", example = Constants.Api.FieldExample.COUNTRY_CODE, required = true)
    @NotBlank
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @Pattern(regexp = "^[\\d]*", message = Constants.ValidationMessage.INVALID_COUNTRY_CODE)
    private String countryCode;
}

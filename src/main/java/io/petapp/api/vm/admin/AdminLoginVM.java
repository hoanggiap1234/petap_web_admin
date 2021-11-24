package io.petapp.api.vm.admin;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AdminLoginVM {
    @Schema(description = "User name of the User.", example = Constants.Api.FieldExample.USER_NAME, required = true)
    @NotNull(message = Constants.ValidationMessage.FIELD_IS_REQUIRED)
    @Pattern(regexp = "^[\\w\\d]{5,20}$", message = Constants.ValidationMessage.INVALID_USER_NAME)
    private String userName;

    @Schema(description = "Password of the User.", example = Constants.Api.FieldExample.PASSWORD, required = true)
    @NotNull
    @Size(min = 6, max = 32, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @Pattern(regexp = "^[\\w\\d$&+,:;=?@#|'<>.^*()%!-]*", message = Constants.ValidationMessage.INVALID_PASSWORD)
    private String password;
}

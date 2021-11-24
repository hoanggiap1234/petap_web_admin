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
public class AdminRegisterAccountVM {

    @Schema(description = "Phone number for registration purposes.", example = Constants.Api.FieldExample.PHONE_NUMBER, required = true)
    @NotNull
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String phoneNumber;

    @Schema(description = "Password for registration purposes.", example = Constants.Api.FieldExample.PASSWORD, required = true)
    @NotNull
    @Size(min = 6, max = 32, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @Pattern(regexp = "^[\\w\\d$&+,:;=?@#|'<>.^*()%!-]*", message = Constants.ValidationMessage.INVALID_PASSWORD)
    private String password;

    @Schema(description = "User name for registration purposes.", example = Constants.Api.FieldExample.USER_NAME, required = true)
    @NotNull
    @Pattern(regexp = "^[\\w\\d]{5,20}$", message = Constants.ValidationMessage.INVALID_USER_NAME)
    private String userName;

    @Schema(description = "Display name of the user for updating purposes.", example = Constants.Api.FieldExample.USER_NAME, required = true)
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String displayName;

    @Schema(description = "Display name of the user for updating purposes.", example = Constants.Api.FieldExample.FIRST_NAME, required = true)
    @Pattern(regexp = "^[\\p{L}\\s]{1,30}$", message = Constants.ValidationMessage.INVALID_FIRST_NAME)
    @NotNull
    private String firstName;

    @Schema(description = "Display name of the user for updating purposes.", example = Constants.Api.FieldExample.LAST_NAME, required = true)
    @Pattern(regexp = "^[\\p{L}\\s]{1,30}$", message = Constants.ValidationMessage.INVALID_LAST_NAME)
    @NotNull
    private String lastName;

    @Schema(description = "Avatar of the user for updating purposes.", example = Constants.Api.FieldExample.ADDRESS)
    @NotNull
    private String address;

    @Schema(description = "Avatar of the user for updating purposes.", example = Constants.Api.FieldExample.AVATAR_BASE64)
    private String avatar;

    @Schema(description = "Gender of the user for updating purposes.", example = Constants.Api.FieldExample.GENDER)
    @NotNull
    private Integer gender;
}

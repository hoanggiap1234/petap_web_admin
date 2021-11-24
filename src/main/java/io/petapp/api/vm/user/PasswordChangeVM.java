/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class PasswordChangeVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Current password of the account.", example = Constants.Api.FieldExample.PASSWORD)
    @Pattern(regexp = "^[\\w\\d$&+,:;=?@#|'<>.^*()%!-]*", message = Constants.ValidationMessage.INVALID_PASSWORD)
    private String currentPassword;

    @Schema(description = "New password of the account.", example = Constants.Api.FieldExample.PASSWORD, required = true)
    @NotNull
    @Size(min = 6, max = 32, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    @Pattern(regexp = "^[\\w\\d$&+,:;=?@#|'<>.^*()%!-]*", message = Constants.ValidationMessage.INVALID_PASSWORD)
    private String newPassword;

    @Schema(description = "Phone number associated to the account.", example = Constants.Api.FieldExample.PHONE_NUMBER)
    @Size(min = 6, max = 32, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String phoneNumber;

}

/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.vm.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * View Model object for storing the user's key and password.
 */
public class KeyAndPasswordVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Password key.", example = "298bb0244ebc987810de3892384bb4663742a540db2b3a875f66b09d068d1f64")
    private String key;

    @Schema(description = "New password.", example = Constants.Api.FieldExample.PASSWORD, required = true)
    @Size(min = 6, max = 32, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    private String newPassword;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

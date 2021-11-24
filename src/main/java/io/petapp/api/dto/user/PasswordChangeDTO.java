/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.user;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class PasswordChangeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Current password of the account.", example = Constants.Api.FieldExample.PASSWORD)
    private String currentPassword;

    @Schema(description = "New password of the account.", example = Constants.Api.FieldExample.PASSWORD)
    private String newPassword;

    public PasswordChangeDTO() {
        // Empty constructor needed for Jackson.
    }

    public PasswordChangeDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

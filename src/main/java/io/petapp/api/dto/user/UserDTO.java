/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.user;

import io.petapp.api.core.Constants;
import io.petapp.api.model.admin.Role;
import io.petapp.api.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO representing a user, with only the public attributes.
 */
@Getter
@Setter
public class UserDTO implements Serializable {

    @Schema(description = "ID of the user.", example = Constants.Api.FieldExample.ID)
    private String id;

    @Schema(description = "User name of the user.", example = Constants.Api.FieldExample.USER_NAME)
    private String userName;

    @Schema(description = "Phone number of the user.", example = Constants.Api.FieldExample.PHONE_NUMBER)
    private String phoneNumber;

    @Schema(description = "First name of the user.", example = Constants.Api.FieldExample.FIRST_NAME)
    private String firstName;

    @Schema(description = "Last name of the user.", example = Constants.Api.FieldExample.LAST_MIDDLE_NAME)
    private String lastName;

    @Schema(description = "Full name of the user.", example = Constants.Api.FieldExample.FULL_NAME)
    private String fullName;

    @Schema(description = "Display name of the user.", example = Constants.Api.FieldExample.USER_NAME)
    private String displayName;

    @Schema(description = "Email of the user.", example = Constants.Api.FieldExample.EMAIL)
    private String email;

    @Schema(description = "Status of the user.", example = Constants.Api.FieldExample.INTEGER)
    private Integer status;

    @Schema(description = "Type of the user.", example = Constants.Api.FieldExample.INTEGER)
    private Integer type;

    @Schema(description = "The user is activated or not.", example = Constants.Api.FieldExample.BOOLEAN)
    private Boolean activated;

    @Schema(description = "Language of the application.", example = Constants.Api.FieldExample.LANGUAGE)
    private String lang;

    @Schema(description = "Avatar of the user.", example = Constants.Api.FieldExample.AVATAR_FILE)
    private String avatar;

    @Schema(description = "Tokens of devices authorized by the user.")
    private List<User.DeviceToken> deviceTokens;

    @Schema(description = "Devices authorized by the user.")
    private List<User.Device> devices;

    @Schema(description = "Country code of the user.", example = Constants.Api.FieldExample.COUNTRY_CODE)
    private String countryCode;

    @Schema(description = "Gender of the user.", example = Constants.Api.FieldExample.GENDER)
    private Integer gender;

    @Schema(description = "Address of the user.", example = Constants.Api.FieldExample.GEOGRAPHIC_LOCATION)
    private User.Address address;

    @Schema(description = "Settings of the current device.")
    private User.Setting settings;

    @Schema(description = "The user is author of creating or modifying information.")
    private List<Role> roles;

    @Schema(description = "The last time user logged in.", example = Constants.Api.FieldExample.TIME_STAMP_MILLISECONDS)
    private Long lastLoginDate;

}

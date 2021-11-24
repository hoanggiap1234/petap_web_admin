/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.petapp.api.core.entity.IdentifiedEntity;
import io.petapp.api.model.admin.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Document(collection = "users")
public class User extends IdentifiedEntity implements Serializable {

    @Indexed
    @Field("user_name")
    private String userName;
    @JsonIgnore
    private String password;
    @Indexed
    @Field("phone_number")
    private String phoneNumber;
    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    @Field("full_name")
    private String fullName;
    @Field("display_name")
    private String displayName;
    @Indexed
    @Email
    private String email;
    @Indexed
    private Integer status;
    @Indexed
    private Integer type;
    @Indexed
    private Boolean activated;
    private String lang;
    private String avatar;
    @Field("device_tokens")
    private List<DeviceToken> deviceTokens;
    private List<Device> devices;
    @Field("country_code")
    private String countryCode;
    private Integer gender;
    private Address address;
    private Setting setting;
    private List<Role> roles;
    @Field("last_login_date")
    private Long lastLoginDate;

    @Getter
    @Setter
    public static class DeviceToken implements Serializable {

        private String token;
        private Integer type;
    }

    @Getter
    @Setter
    public static class Device implements Serializable {

        public static final int STATUS_ACTIVATED = 1;
        public static final int STATUS_INVOKED = 2;

        @Indexed
        private String id;
        private String name;
        private Integer type;
        private String os;
        private String agent;
        private String vendor;
        private String model;
        private Integer status;
        @Field("bio_auth")
        private Boolean bioAuth;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Device device = (Device) o;
            return Objects.equals(id, device.id);
        }
    }

    @Getter
    @Setter
    public static class Setting {

        private String timezone;
        private String language;
        private String length;
        private String weight;
        private String volume;
        private Theme theme;

        @Getter
        @Setter
        public static class Theme {

            @Field("dark_mode")
            private Boolean darkMode;
            @Field("app_icon")
            private String appIcon;
            private String color;
        }
    }

    @Getter
    @Setter
    public static class Address {

        private String country;
        private String state;
        private String province;
        private String district;
        private String ward;
        private String street;
    }
}

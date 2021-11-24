/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.security.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.petapp.api.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author truongtran
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("public_key")
    private String publicKey;

    private UserDTO user;
}

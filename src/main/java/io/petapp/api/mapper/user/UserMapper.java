/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.mapper.user;

import io.petapp.api.dto.user.UserDTO;
import io.petapp.api.model.user.User;
import io.petapp.config.MapStructConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfiguration.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User entity);

    @InheritInverseConfiguration
    User toModel(UserDTO dto);

}

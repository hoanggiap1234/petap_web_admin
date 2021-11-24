/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.mapper.admin;

import io.petapp.api.dto.admin.RoleDTO;
import io.petapp.api.dto.user.UserDTO;
import io.petapp.api.model.admin.Role;
import io.petapp.api.model.user.User;
import io.petapp.config.MapStructConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfiguration.class)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDTO(Role entity);

    @InheritInverseConfiguration
    Role toModel(RoleDTO dto);
}

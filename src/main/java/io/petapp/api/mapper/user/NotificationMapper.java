/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.mapper.user;

import io.petapp.api.dto.user.NotificationDTO;
import io.petapp.api.dto.user.UserDTO;
import io.petapp.api.model.user.Notification;
import io.petapp.api.model.user.User;
import io.petapp.config.MapStructConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 * <p>
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Mapper(config = MapStructConfiguration.class)
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationDTO toDTO(Notification entity);

    List<NotificationDTO> toDtoList(List<Notification> entity);

    @InheritInverseConfiguration
    Notification toModel(NotificationDTO dto);

    @InheritInverseConfiguration
    List<Notification> toModelList(List<NotificationDTO> dto);

}

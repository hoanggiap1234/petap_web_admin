/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.mapper.admin;

import io.petapp.api.dto.admin.VerificationRequestDTO;
import io.petapp.api.model.admin.VerificationRequest;
import io.petapp.config.MapStructConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/25/21 19:25
 */
@Mapper(config = MapStructConfiguration.class)
public interface VerificationRequestMapper {

    VerificationRequestMapper INSTANCE = Mappers.getMapper(VerificationRequestMapper.class);

    VerificationRequestDTO toDTO(VerificationRequest entity);

    @InheritInverseConfiguration
    VerificationRequest toModel(VerificationRequestDTO dto);

}

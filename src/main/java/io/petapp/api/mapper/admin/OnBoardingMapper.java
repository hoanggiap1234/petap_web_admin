/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.mapper.admin;

import io.petapp.api.dto.admin.OnBoardingDTO;
import io.petapp.api.model.admin.OnBoarding;
import io.petapp.config.MapStructConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author truongtran
 * @version 1.0
 * @date 7/25/21 19:25
 */
@Mapper(config = MapStructConfiguration.class)
public interface OnBoardingMapper {

    OnBoardingMapper INSTANCE = Mappers.getMapper(OnBoardingMapper.class);

    OnBoardingDTO toDTO(OnBoarding entity);

    List<OnBoardingDTO> toDtoList(List<OnBoarding> onBoardingList);

    @InheritInverseConfiguration
    OnBoarding toModel(OnBoardingDTO dto);

    @InheritInverseConfiguration
    List<OnBoarding> toModelList(List<OnBoardingDTO> onBoardingDTOList);

}

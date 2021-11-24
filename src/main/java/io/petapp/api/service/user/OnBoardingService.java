/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.service.user;

import io.petapp.api.dto.admin.OnBoardingDTO;
import io.petapp.api.mapper.admin.OnBoardingMapper;
import io.petapp.api.model.admin.OnBoarding;
import io.petapp.api.repository.user.OnBoardingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnBoardingService {

    private final OnBoardingRepository onBoardingRepository;

    public List<OnBoardingDTO> getAllActive() {
        List<OnBoarding> onBoardingList = onBoardingRepository.getAllActive();
        return OnBoardingMapper.INSTANCE.toDtoList(onBoardingList);
    }
}

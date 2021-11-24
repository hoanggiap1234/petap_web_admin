/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.service.user;

import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.dto.user.InformationSafeZoneDTO;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.model.user.SafeZone;
import io.petapp.api.model.user.User;
import io.petapp.api.repository.user.SafeZoneRepository;
import io.petapp.api.repository.user.UserRepository;
import io.petapp.api.vm.user.InformationSafeZoneVM;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SafeZoneService {

    private final UserRepository userRepository;

    private final SafeZoneRepository safeZoneRepository;

    private ModelMapper modelMapper;

    public Page<InformationSafeZoneDTO> getListSafeZones(String userName, Integer page, Integer size, String search) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        modelMapper = new ModelMapper();
        return safeZoneRepository.findAllByUserIdAndDeletedOrderByCreatedDateDesc(user.getId(),
                false, PageRequest.of(page, size))
            .map(safeZone -> {
                InformationSafeZoneDTO informationSafeZoneDTO = new InformationSafeZoneDTO();

                informationSafeZoneDTO.setName(safeZone.getName());
                return informationSafeZoneDTO;
            });
    }

    public void saveSafeZone(InformationSafeZoneVM informationSafeZoneVM, String userName) {

        User user = userRepository.findByUserNameAndDeleted(userName, false);

        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        SafeZone safeZone;

        if (informationSafeZoneVM.getId() != null) {
            safeZone = safeZoneRepository.findByIdAndUserIdAndDeleted(informationSafeZoneVM.getId(), user.getId(), false);
            if (safeZone == null) {
                throw new BusinessException(ExceptionType.NOT_FOUND_SAFE_ZONE);
            }
            safeZone.setLastModifiedBy(userName);
        } else {
            safeZone = new SafeZone();
            safeZone.setUserId(user.getId());
            safeZone.setCreatedBy(userName);
            safeZone.setDeleted(false);
        }

        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.map(informationSafeZoneVM, safeZone);
        safeZone.setName(informationSafeZoneVM.getName().trim());
        safeZone.setLocation(informationSafeZoneVM.getLocation().trim());
        safeZoneRepository.save(safeZone);
    }


    public InformationSafeZoneDTO getInformation(String userName, String id) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }
        SafeZone safeZone = safeZoneRepository.findByIdAndUserIdAndDeleted(id, user.getId(), false);

        if (safeZone == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_SAFE_ZONE);
        }
        modelMapper = new ModelMapper();
        return modelMapper.map(safeZone, InformationSafeZoneDTO.class);
    }

    public void deleteSafeZone(String userName, String id) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        SafeZone safeZone = safeZoneRepository.findByIdAndUserIdAndDeleted(id, user.getId(), false);

        if (safeZone == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_SAFE_ZONE);
        }

        safeZone.setDeleted(true);
        safeZone.setDeletedBy(userName);
        safeZone.setDeletedDate(Instant.now().toEpochMilli());
        safeZoneRepository.save(safeZone);
    }
}

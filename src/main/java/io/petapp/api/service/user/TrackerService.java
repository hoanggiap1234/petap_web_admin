/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.service.user;

import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.dto.user.InformationTrackerDTO;
import io.petapp.api.enums.TrackerStatus;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.model.user.*;
import io.petapp.api.repository.user.PetRepository;
import io.petapp.api.repository.user.TrackerRepository;
import io.petapp.api.repository.user.UserRepository;
import io.petapp.api.vm.user.AssignPetVM;
import io.petapp.api.vm.user.UpdateTrackerVM;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackerService {

    private final TrackerRepository trackerRepository;

    private final UserRepository userRepository;

    private final PetRepository petRepository;

    private ModelMapper modelMapper;

    public Page<InformationTrackerDTO> getListTracker(String userName, Integer page, Integer size, String search) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        return trackerRepository.findAllByUserIdAndDeleted(user.getId(),
                false, PageRequest.of(page, size))
            .map(tracker -> {
                InformationTrackerDTO informationTrackerDTO = new InformationTrackerDTO();

                informationTrackerDTO.setLatestInformation(tracker.getLatestInformation());
                informationTrackerDTO.setSettings(tracker.getSettings());
                informationTrackerDTO.setId(tracker.getId());
                informationTrackerDTO.setName(tracker.getName());
                informationTrackerDTO.setModel(tracker.getModel());

                return informationTrackerDTO;
            });
    }

    public InformationTrackerDTO getInformation(String userName, String id) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        Tracker tracker = trackerRepository.findByIdAndDeletedAndUserId(id, false, user.getId());

        if (tracker == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_TRACKER);
        }

        InformationTrackerDTO informationTrackerDTO = new InformationTrackerDTO();

        informationTrackerDTO.setLatestInformation(tracker.getLatestInformation());
        informationTrackerDTO.setSettings(tracker.getSettings());
        informationTrackerDTO.setActivated(tracker.getActivated());
        informationTrackerDTO.setImei(tracker.getImei());
        informationTrackerDTO.setModel(tracker.getModel());
        informationTrackerDTO.setId(tracker.getId());
        informationTrackerDTO.setName(tracker.getName());

        return informationTrackerDTO;
    }

    public void updateTracker(String userName, UpdateTrackerVM updateTrackerVM) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        modelMapper = new ModelMapper();
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        Tracker tracker = trackerRepository.findByIdAndDeletedAndUserId(updateTrackerVM.getId(), false, user.getId());
        if (tracker == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_TRACKER);
        }

        if (updateTrackerVM.getMacPositions() != null && !StringUtils.isBlank(updateTrackerVM.getMacPositions().getMac())) {
            updateTrackerVM.getMacPositions().setMac(updateTrackerVM.getMacPositions().getMac().trim());
        }

        if (!StringUtils.isBlank(updateTrackerVM.getAvatar())) {
            updateTrackerVM.setAvatar(updateTrackerVM.getAvatar().trim());
        }

        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().
            setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        modelMapper.map(updateTrackerVM, tracker);
        tracker.setName(updateTrackerVM.getName().trim());

        trackerRepository.save(tracker);
    }

    public InformationTrackerDTO verifyImei(String userName, String imei) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        modelMapper = new ModelMapper();
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        Tracker tracker = trackerRepository.findByImeiAndDeleted(imei, false);

        if (tracker == null) {
            throw new BusinessException(ExceptionType.INCORRECT_TRACKER_INFORMATION);
        }

        if (
            ((tracker.getUserId() == null && tracker.getStatus().equals(TrackerStatus.IN_STOCK.getCode())) ||
                (tracker.getUserId().equals(user.getId()) && tracker.getStatus().equals(TrackerStatus.ASSIGNED.getCode())))
                && tracker.getActivated() && tracker.getImei().equals(imei)
        ) {
            tracker.setUserId(user.getId());
            tracker.setStatus(TrackerStatus.ASSIGNED.getCode());
            tracker = trackerRepository.save(tracker);

            InformationTrackerDTO informationTrackerDTO = new InformationTrackerDTO();

            informationTrackerDTO.setId(tracker.getId());
            informationTrackerDTO.setAvatar(tracker.getAvatar());
            informationTrackerDTO.setImei(tracker.getImei());
            informationTrackerDTO.setName(tracker.getName());

            return informationTrackerDTO;
        }

        if (
            (!tracker.getUserId().equals(user.getId()) && tracker.getStatus().equals(TrackerStatus.ASSIGNED.getCode())) ||
                tracker.getStatus().equals(TrackerStatus.UNDER_WARRANTY.getCode())
        ) {
            throw new BusinessException(ExceptionType.INCORRECT_TRACKER_INFORMATION);
        }

        if (tracker.getUserId().equals(user.getId()) && tracker.getStatus().equals(TrackerStatus.IN_USED.getCode())) {
            throw new BusinessException(ExceptionType.ALREADY_IN_USE_TRACKER);
        }

        if (!tracker.getUserId().equals(user.getId()) && tracker.getStatus().equals(TrackerStatus.IN_USED.getCode())) {
            throw new BusinessException(ExceptionType.ALREADY_POSSESSED_TRACKER);
        }

        throw new BusinessException(ExceptionType.NOT_FOUND_TRACKER);
    }

    public Boolean checkDeviceAssignedPet(String userName, String id) {

        User user = userRepository.findByUserNameAndDeleted(userName, false);

        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        Pet pet = petRepository.findByIdAndUserIdAndDeleted(id, user.getId(), false);

        if (pet == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_PET);
        }

        return pet.getTrackerId() != null;
    }

    @Transactional
    public void assignPet(String userName, AssignPetVM assignPetVM) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);

        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        Pet pet = petRepository.findByIdAndUserIdAndDeleted(assignPetVM.getPetId(), user.getId(), false);

        if (pet == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_PET);
        }

        Tracker tracker = trackerRepository.findByImeiAndUserIdAndDeleted(assignPetVM.getImei(), user.getId(), false);

        if (tracker == null) {
            throw new BusinessException(ExceptionType.INCORRECT_TRACKER_INFORMATION);
        }

        Pet usedPet = petRepository.findByTrackerId(tracker.getId());

        if (usedPet != null && !usedPet.getId().equals(pet.getId())) {
            usedPet.setTrackerId(null);
            petRepository.save(usedPet);
        }

        pet.setTrackerId(tracker.getId());
        petRepository.save(pet);

        tracker.setStatus(TrackerStatus.IN_USED.getCode());
        trackerRepository.save(tracker);
    }
}

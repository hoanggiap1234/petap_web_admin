/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.service.user;

import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.dto.user.InformationPetDTO;
import io.petapp.api.model.user.Breed;
import io.petapp.api.model.user.Pet;
import io.petapp.api.model.user.User;
import io.petapp.api.repository.user.BreedRepository;
import io.petapp.api.repository.user.PetRepository;
import io.petapp.api.repository.user.UserRepository;
import io.petapp.api.vm.user.InformationPetVM;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final UserRepository userRepository;

    private final PetRepository petRepository;

    private final BreedRepository breedRepository;

    private ModelMapper modelMapper;

    public Page<InformationPetDTO> getListPet(String userName, Integer page, Integer size, String search) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }
        modelMapper = new ModelMapper();
        return petRepository.findAllByUserIdAndDeletedOrderByCreatedDateDesc(user.getId(),
                false, PageRequest.of(page, size))
            .map(pet -> modelMapper.map(pet, InformationPetDTO.class));
    }


    public InformationPetDTO getInformation(String userName, String id) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }
        Pet pet = petRepository.findByIdAndUserIdAndDeleted(id, user.getId(), false);

        if (pet == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_PET);
        }
        modelMapper = new ModelMapper();

        return modelMapper.map(pet, InformationPetDTO.class);
    }

    public void savePet(InformationPetVM informationPetVM, String userName) {

        User user = userRepository.findByUserNameAndDeleted(userName, false);

        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        Breed breed = breedRepository.findByIdAndDeleted(informationPetVM.getSpecies(), false);

        if (breed == null || breedRepository.findAllByIdInAndDeleted(Arrays.stream(informationPetVM.getBreeds()).collect(Collectors.toList()), false).size() != informationPetVM.getBreeds().length) {
            throw new BusinessException(ExceptionType.NOT_FOUND_BREED);
        }

        Pet pet;

        if (informationPetVM.getId() != null) {
            pet = petRepository.findByIdAndUserIdAndDeleted(informationPetVM.getId(), user.getId(), false);
            if (pet == null) {
                throw new BusinessException(ExceptionType.NOT_FOUND_PET);
            }
            pet.setLastModifiedBy(userName);
        } else {
            pet = new Pet();
            pet.setUserId(user.getId());
            pet.setCreatedBy(userName);
            pet.setDeleted(false);
        }

        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        if (!StringUtils.isBlank(informationPetVM.getName())) {
            informationPetVM.setName(informationPetVM.getName().trim());
        }

        if (!StringUtils.isBlank(informationPetVM.getAvatar())) {
            informationPetVM.setAvatar(informationPetVM.getAvatar().trim());
        }

        modelMapper.map(informationPetVM, pet);

        petRepository.save(pet);
    }

    public void deletePet(String id, String userName) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);

        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        Pet pet = petRepository.findByIdAndUserIdAndDeleted(id, user.getId(), false);

        if (pet == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_PET);
        }
        pet.setDeleted(true);
        pet.setDeletedBy(userName);
        pet.setDeletedDate(Instant.now().toEpochMilli());
        petRepository.save(pet);
    }
}


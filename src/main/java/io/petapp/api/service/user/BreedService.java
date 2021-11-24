/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.service.user;

import io.petapp.api.dto.user.InformationBreedDTO;
import io.petapp.api.model.user.Breed;
import io.petapp.api.repository.user.BreedRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BreedService {

    private final BreedRepository breedRepository;

    private ModelMapper modelMapper;

    public Page<InformationBreedDTO> getListBreed(String parentId, Integer page, Integer size, String search) {
        modelMapper = new ModelMapper();

        return breedRepository.findAllByParentIdAndDeletedOrderByPathOrderAsc(parentId,
            false,
            PageRequest.of(page, size)).map(breed -> modelMapper.map(breed, InformationBreedDTO.class)
        );
    }
}

/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.service.user;

import io.petapp.api.dto.user.InformationHomeDTO;
import io.petapp.api.dto.user.InformationPetDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PetService petService;

    ModelMapper modelMapper;

    public InformationHomeDTO getInformationHome(String phoneNumber) {
        InformationHomeDTO informationHomeDTO = new InformationHomeDTO();
        Page<InformationPetDTO> listPet = petService.getListPet(phoneNumber, 0, 10, "");
        informationHomeDTO.setPets(listPet);
        return informationHomeDTO;
    }
}

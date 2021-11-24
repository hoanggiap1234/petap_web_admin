/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.dto.user;


import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.io.Serializable;

@Getter
@Setter
public class InformationHomeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Page (Home) containing pets information.")
    Page<InformationPetDTO> pets;
}

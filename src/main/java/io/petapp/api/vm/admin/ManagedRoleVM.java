package io.petapp.api.vm.admin;

import io.petapp.api.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
public class ManagedRoleVM implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "role code is not null")
    @Size(max = 50, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    public  String code;

    @NotBlank(message = "role name is not null")
    @Size(max = 250, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    public  String name;

    @NotBlank(message = "role is not null")
    @Size(max = 1000, message = Constants.ValidationMessage.INVALID_SIZE_VALUE)
    public  String description;

    @NotNull(message = "role activated is not null")
    @Schema(description = "role activated", required = true)
    public  Boolean activated;

}

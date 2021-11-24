package io.petapp.api.dto.admin;

import io.petapp.api.core.Constants;
import io.petapp.api.core.entity.IdentifiedEntity;
import io.petapp.api.model.admin.Menu;
import io.petapp.api.model.admin.Permissions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class RoleDTO extends IdentifiedEntity implements Serializable {

    private String id;

    private String code;

    private String name;

    private String description;

    private Boolean activated;

    private List<Permissions> permissions;

    private List<Menu> menus;
}

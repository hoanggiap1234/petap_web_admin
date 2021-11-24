package io.petapp.api.dto.user;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
public class GroupMembersDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String socialGroupId;

    private Boolean isOwner;

    private Integer role;
}

package com.ronnachate.inventory.user.dto;

import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends BaseUserDTO {

    private UUID id;

    private UserStatusDTO status;

    private int statusId;

    private Date createdAt;

    private Date updatedAt;
}

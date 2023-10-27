package com.ronnachate.inventory.user.dto;

import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private UUID id;
    private String title;
    private String name;
    private String lastname;
    private String username;
    private UserStatusDTO status;
    private Date createdAt;
    private Date updatedAt;
}

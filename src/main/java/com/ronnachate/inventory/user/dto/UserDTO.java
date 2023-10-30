package com.ronnachate.inventory.user.dto;

import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class UserDTO {
    private UUID id;

    @Size(max = 20, message = "Username have at most 50 characters")
    private String title;

    @NotEmpty(message = "Name is required")
    @Size(min = 2, message = "Name should have at least 2 characters")
    @Size(max = 20, message = "Username have at most 100 characters")
    private String name;

    @Size(max = 20, message = "Username have at most 100 characters")
    private String lastname;

    @NotEmpty(message = "Username is required")
    @Size(min = 2, message = "Username have at least 2 characters")
    @Size(max = 20, message = "Username have at most 50 characters")
    private String username;

    private UserStatusDTO status;

    private int statusId;

    private Date createdAt;

    private Date updatedAt;
}

package com.ronnachate.inventory.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseUserDTO {

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
}

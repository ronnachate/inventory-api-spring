package com.ronnachate.inventory.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO extends BaseUserDTO {

    public CreateUserDTO() {

    
    }
    public CreateUserDTO(String title, String name, String lastname, String username) {
        this.title = title;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
    }
}

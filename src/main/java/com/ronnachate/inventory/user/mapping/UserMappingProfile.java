package com.ronnachate.inventory.user.mapping;

import org.modelmapper.PropertyMap;

import com.ronnachate.inventory.user.dto.CreateUserDTO;
import com.ronnachate.inventory.user.dto.UserDTO;
import com.ronnachate.inventory.user.entity.User;

public class UserMappingProfile {
    
    public static PropertyMap<CreateUserDTO, User> DtoToEntities() {
        return new PropertyMap<CreateUserDTO, User>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getStatus());
                skip(destination.getCreatedAt());
                skip(destination.getUpdatedAt());
            }
        };
    }

    public static PropertyMap<User, UserDTO> EntitityToDTO() {
        return new PropertyMap<User, UserDTO>() {
            @Override
            protected void configure() {
            }
        };
    }
}

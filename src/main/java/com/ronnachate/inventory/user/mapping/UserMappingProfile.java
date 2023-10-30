package com.ronnachate.inventory.user.mapping;

import org.modelmapper.PropertyMap;

import com.ronnachate.inventory.user.dto.UserDTO;
import com.ronnachate.inventory.user.entity.User;

public class UserMappingProfile {
    
    public static PropertyMap<UserDTO, User> DtoToEntities() {
        return new PropertyMap<UserDTO, User>() {
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

package com.ronnachate.inventory.user.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronnachate.inventory.user.entity.User;
import com.ronnachate.inventory.user.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getById(UUID id) {
        var user = userRepository.findById(id);
        return user;
    }
}

package com.ronnachate.inventory.user.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<User> getUsers(int page, int rows) {
        PageRequest pageRequest = PageRequest.of(page - 1, rows);
        Page<User> pagingUser = userRepository.findAll(pageRequest);
        return pagingUser;
    }
}

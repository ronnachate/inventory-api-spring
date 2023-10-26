package com.ronnachate.inventory.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ronnachate.inventory.user.dto.UserDTO;
import com.ronnachate.inventory.user.entity.UserStatus;
import com.ronnachate.inventory.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<String>> getUsers() {
        try {
            List<String> userNames = Arrays.asList("foo", "bar");

            return new ResponseEntity<>(userNames, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable UUID id) {
        try {
            var response = userService.getById(id);

            if (response.isPresent()) {
                var user = response.get();

                var userDTO = modelMapper.map(user, UserDTO.class);

                return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

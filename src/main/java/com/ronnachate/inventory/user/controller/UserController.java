package com.ronnachate.inventory.user.controller;

import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ronnachate.inventory.shared.pagination.Resultset;
import com.ronnachate.inventory.user.entity.User;
import com.ronnachate.inventory.user.dto.UserDTO;
import com.ronnachate.inventory.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<Resultset<User, UserDTO>> getUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows) {
        try {
            var pageUser = userService.getUsers(page, rows);
            Resultset<User, UserDTO> resultset = new Resultset<User, UserDTO>(modelMapper, page, rows,
                    (int) pageUser.getTotalElements(), pageUser.getTotalPages(), pageUser.getContent(), UserDTO.class);
            return new ResponseEntity<Resultset<User, UserDTO>>(resultset, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("getUsers Error", e);
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
                logger.error(String.format("getById not found with: %s", id.toString()));
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("getById Error", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

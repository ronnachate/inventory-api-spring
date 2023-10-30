package com.ronnachate.inventory.user.controller;

import java.util.UUID;

import com.ronnachate.inventory.user.entity.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ronnachate.inventory.shared.constant.GenericConstant;
import com.ronnachate.inventory.shared.pagination.Resultset;
import com.ronnachate.inventory.user.entity.User;
import com.ronnachate.inventory.user.mapping.UserMappingProfile;
import com.ronnachate.inventory.user.dto.UserDTO;
import com.ronnachate.inventory.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get users", description = "Ger users with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful return users data"),
            @ApiResponse(responseCode = "500", description = "internal server error", content = @Content)
    })
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

    @Operation(summary = "Get user by id", description = "Ger users by specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful return user data"),
            @ApiResponse(responseCode = "404", description = "no user found with specified id", content = @Content),
            @ApiResponse(responseCode = "500", description = "internal server error", content = @Content)
    })
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

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto) {
        try {
            modelMapper.addMappings(UserMappingProfile.DtoToEntities());
            var userEntitiy = modelMapper.map(dto, User.class);
            userEntitiy.setStatus(new UserStatus(GenericConstant.ActiveUserStatus));

            var user = userService.addUser(userEntitiy);
            dto = modelMapper.map(user, UserDTO.class);
            
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("createUser Error", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

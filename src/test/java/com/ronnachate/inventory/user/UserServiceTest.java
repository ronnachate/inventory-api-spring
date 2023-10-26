package com.ronnachate.inventory.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ronnachate.inventory.user.entity.User;
import com.ronnachate.inventory.user.repository.UserRepository;
import com.ronnachate.inventory.user.service.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("User service tests")
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Get user by id shoud return correct data")
    void getById_should_return_correct_data() {
        var mockUser = new User("tile1", "name1", "lastname1", "user1");
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockUser));
        var user = userService.getById(UUID.randomUUID());
        assertEquals("name1", user.get().getName());
        assertEquals("user1", user.get().getUsername());
    }

    @Test
    @DisplayName("Get user by id shoud return empty when no data found")
    void getById_should_return_empty_when_no_data_found() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        var user = userService.getById(UUID.randomUUID());
        assumeTrue(user.isEmpty());
    }

    @Test
    @DisplayName("Get user by id shoud throw error when error found")
    void getById_should_throw_error_when_error_found() {
        when(userRepository.findById(any(UUID.class))).thenThrow(new ArrayIndexOutOfBoundsException());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            userService.getById(UUID.randomUUID());
        });
    }
}

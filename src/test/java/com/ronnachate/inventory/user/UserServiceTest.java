package com.ronnachate.inventory.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.checkerframework.checker.units.qual.g;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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

    @Test
    @DisplayName("Get users shoud return correct page data")
    void getUsers_should_return_correct_paget_data() {
        int page = 1;
        int rows = 10;
        var mockUser = new User("tile1", "name1", "lastname1", "user1");
        List<User> users = Arrays.asList(mockUser);
        Page<User> pagedResponse = new PageImpl<User>(users);
        PageRequest pageRequest = PageRequest.of(page - 1, rows);
        when(userRepository.findAll(pageRequest)).thenReturn(pagedResponse);
        var userPage = userService.getUsers(page, rows);

        assertEquals(1, userPage.getTotalElements());
        assertEquals(1, userPage.getTotalPages());
        var user = userPage.getContent().get(0);
        assertEquals("name1", user.getName());
        assertEquals("user1", user.getUsername());
    }

    @Test
    @DisplayName("Get users shoud return empty page when no data found")
    void getUsers_should_return_empty_page_when_no_data_found() {
        int page = 1;
        int rows = 10;
        List<User> users = new ArrayList<User>();
        Page<User> pagedResponse = new PageImpl<User>(users);
        PageRequest pageRequest = PageRequest.of(page - 1, rows);
        when(userRepository.findAll(pageRequest)).thenReturn(pagedResponse);
        var userPage = userService.getUsers(page, rows);

        assertEquals(0, userPage.getTotalElements());
        assertEquals(1, userPage.getTotalPages());
    }

    @Test
    @DisplayName("Get users shoud throw error when error found")
    void getUsers_should_throw_error_when_error_found() {
        int page = 1;
        int rows = 10;
        PageRequest pageRequest = PageRequest.of(page - 1, rows);
        when(userRepository.findAll(pageRequest)).thenThrow(new ArrayIndexOutOfBoundsException());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            userService.getUsers(page, rows);
        });
    }

    @Test
    @DisplayName("addUser shoud return saved user data")
    void addUser_should_return_saved_user_data() {
        var uuid = UUID.randomUUID();
        var user = new User("tile1", "name1", "lastname1", "user1");
        var mockUser = user;
        mockUser.setId(uuid);
        when(userRepository.save(user)).thenReturn(mockUser);

        var created = userService.addUser(user);
        assertEquals(uuid, created.getId());
        assertEquals("name1", created.getName());
        assertEquals("user1", created.getUsername());
    }

    @Test
    @DisplayName("addUser shoud throw error when error found")
    void addUser_should_throw_error_when_error_found() {
        when(userRepository.save(any(User.class))).thenThrow(new ArrayIndexOutOfBoundsException());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            userService.addUser(new User("tile1", "name1", "lastname1", "user1"));
        });
    }

}

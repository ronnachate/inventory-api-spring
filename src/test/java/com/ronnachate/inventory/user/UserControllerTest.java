package com.ronnachate.inventory.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import com.google.gson.reflect.TypeToken;
import com.ronnachate.inventory.user.controller.UserController;
import com.ronnachate.inventory.user.dto.CreateUserDTO;
import com.ronnachate.inventory.user.dto.UserDTO;
import com.ronnachate.inventory.user.entity.User;
import com.ronnachate.inventory.user.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = UserControllerTest.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("User controller tests")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper modalMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void Setup() {
        mockMvc = standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("Get user by id shoud return corect data")
    void getById_should_return_correct_data() throws Exception {
        UUID userId = UUID.randomUUID();
        User mockUser = new User("tile1", "name1", "lastname1", "user1");
        UserDTO expectedDto = new UserDTO();
        expectedDto.setName("name1");
        when(userService.getById(userId)).thenReturn(Optional.of(mockUser));
        when(modalMapper.map(mockUser, UserDTO.class)).thenReturn(expectedDto);

        mockMvc.perform(
                get("/api/users/" + userId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name1"));
    }

    @Test
    @DisplayName("Get user by id shoud return 404 when no data found")
    void getById_should_return_404_when_no_data_found() throws Exception {
        UUID userId = UUID.randomUUID();
        when(userService.getById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(
                get("/api/users/" + userId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get user by id shoud return 500 when exception thrown")
    void getById_should_return_500_when_exception_thrown() throws Exception {
        UUID userId = UUID.randomUUID();
        when(userService.getById(userId)).thenThrow(new ArrayIndexOutOfBoundsException());

        mockMvc.perform(
                get("/api/users/" + userId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Get users shoud return correct resultset")
    void getUsers_should_return_correct_resultset() throws Exception {
        UUID userId = UUID.randomUUID();
        User mockUser = new User("tile1", "name1", "lastname1", "user1");
        List<User> users = Arrays.asList(mockUser);
        Page<User> pagedResponse = new PageImpl<User>(users);
        when(userService.getUsers(1, 10)).thenReturn(pagedResponse);

        UserDTO expectedDto = new UserDTO();
        expectedDto.setName("name1");
        List<UserDTO> dtos = Arrays.asList(expectedDto);
        when(userService.getById(userId)).thenReturn(Optional.of(mockUser));
        Type type = TypeToken.getParameterized(List.class, UserDTO.class).getType();
        when(modalMapper.map(users, type)).thenReturn(dtos);

        mockMvc.perform(
                get("/api/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.items[0]").exists())
                .andExpect(jsonPath("$.items[0].name").value("name1"));
    }

    @Test
    @DisplayName("Get users id shoud return 500 when exception thrown")
    void getUsers_should_return_500_when_exception_thrown() throws Exception {
        when(userService.getUsers(1, 10)).thenThrow(new ArrayIndexOutOfBoundsException());

        mockMvc.perform(
                get("/api/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Create users shoud return correct result with valid user request")
    public void createUser_with_valid_request_return_correct_response() throws Exception {
        String user = "{\"name\": \"name1\", \"username\" : \"user1\"}";
        User mockEntity = new User(null, "name1", null, "user1");
        UserDTO expectedDto = new UserDTO();
        expectedDto.setName("name1");
        expectedDto.setUsername("user1");
        when(modalMapper.map(any(CreateUserDTO.class), any())).thenReturn(mockEntity);
        when(modalMapper.map(any(User.class), any())).thenReturn(expectedDto);
        when(userService.addUser(mockEntity)).thenReturn(mockEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .content(user).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("name1"));
    }

    // MethodValidationPostProcessor will not work with
    // MockMvcBuilders.standaloneSetup()
    // then we can not test result of validation, return 400 with detail
    @Test
    @DisplayName("Create users shoud return bad request with detail for invalid user request")
    public void createUser_should_return_400_with_detail_for_invalid_request() throws Exception {
        String noRequiredParams = "{\"name\": \"name1\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .content(noRequiredParams).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        // .andExpect(jsonPath("$.name").value("Name is required"))
        // .andExpect(jsonPath("$.username").value("Username is required"));

        String invalidInputLength = "{\"name\": \"n\", \"username\" : \"u\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .content(invalidInputLength).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        // .andExpect(jsonPath("$.name").value("Name should have at least 2
        // characters"))
        // .andExpect(jsonPath("$.username").value("Username have at least 2
        // characters"));
    }

    @Test
    @DisplayName("Create users shoud return 500 when exception thrown")
    void createUser_should_return_500_when_exception_thrown() throws Exception {
        String user = "{\"name\": \"name1\", \"username\" : \"user1\"}";
        when(modalMapper.map(any(CreateUserDTO.class), any())).thenThrow(new ArrayIndexOutOfBoundsException());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .content(user).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}

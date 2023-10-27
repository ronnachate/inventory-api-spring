package com.ronnachate.inventory.shared;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ronnachate.inventory.shared.pagination.Resultset;
import com.ronnachate.inventory.user.dto.UserDTO;
import com.ronnachate.inventory.user.entity.User;

@DisplayName("Resultset tests")
public class ResultsetTest {

    @Test
    @DisplayName("resultset constructor should set property correctly")
    void resultset_constructor_should_set_property_correctly() {

        ModelMapper modalMapper = new ModelMapper();

        var mockUser = new User("tile1", "name1", "lastname1", "user1");
        List<User> users = Arrays.asList(mockUser);
        var resultset = new Resultset<User, UserDTO>(modalMapper, 1, 10, 1, 1, users, UserDTO.class);
        assertEquals(1, resultset.getPage());
        assertEquals(10, resultset.getRows());
        assertEquals(1, resultset.getTotal());
        assertEquals(1, resultset.getTotalPage());
        var userDto = resultset.getItems().get(0);
        assertEquals(userDto.getClass(), UserDTO.class);
        assertEquals("name1", userDto.getName());
        assertEquals("user1", userDto.getUsername());
    }
}

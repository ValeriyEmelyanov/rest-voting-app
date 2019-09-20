package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.UserDto;
import com.example.restvotingapp.entity.Role;
import com.example.restvotingapp.service.Impl.UserServiceImpl;
import com.example.restvotingapp.util.EndPoins;
import com.example.restvotingapp.web.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    private MockMvc mockMvc;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userDto = new UserDto();
        userDto.setId(101);
        userDto.setName("User");
        userDto.setEmail("user@test.com");
        userDto.setEnabled(true);
        userDto.setPassword("password");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(Role.ROLE_USER);
        userDto.setRoles(userRoles);
    }

    @Test
    void list() throws Exception {
        List<UserDto> users = new ArrayList<>();
        users.add(userDto);
        users.add(new UserDto());

        when(userService.list(anyInt(), anyInt())).thenReturn(users);

        List<UserRest> resultList = userController.list(1, 20);

        assertNotNull(resultList);
        assertEquals(users.size(), resultList.size());

        mockMvc.perform(get(EndPoins.USERS))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(get(EndPoins.USERS + "?page=1&limit=20"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getById() throws Exception {
        when(userService.getById(anyInt())).thenReturn(userDto);

        UserRest userRest = userController.getById(100);

        assertNotNull(userRest);
        assertEquals(userDto.getId(), userRest.getId());
        assertEquals(userDto.getName(), userRest.getName());
        assertEquals(userDto.getEmail(), userRest.getEmail());
        assertEquals(userDto.isEnabled(), userRest.isEnabled());

        mockMvc.perform(get(EndPoins.USERS + "/101"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void create() throws Exception {
        when(userService.create(ArgumentMatchers.any())).thenReturn(userDto);

        UserRest userRest = userController.create(new UserDto());
        assertNotNull(userRest);
        assertEquals(userDto.getName(), userRest.getName());
        assertEquals(userDto.getEmail(), userRest.getEmail());
        assertEquals(userDto.isEnabled(), userRest.isEnabled());

        String userJsonString = "{\"name\":\"User\",\"email\":\"user@test.com\",\"password\":\"password\"," +
                "\"roles\":[\"ROLE_USER\"]}";

        mockMvc.perform(post(EndPoins.USERS)
                .content(userJsonString)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept("application/json"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("User"))
                .andExpect(jsonPath("$.email").value("user@test.com"));
    }

    @Test
    void createWithNullEmail() throws Exception {
        String userJsonString = "{\"name\":\"User\",\"password\":\"password\"," +
                "\"roles\":[\"ROLE_USER\"]}";

        mockMvc.perform(post(EndPoins.USERS)
                .content(userJsonString)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        when(userService.update(anyInt(), ArgumentMatchers.any())).thenReturn(userDto);

        UserRest userRest = userController.update(1, new UserDto());
        assertNotNull(userRest);
        assertEquals(userDto.getName(), userRest.getName());
        assertEquals(userDto.getEmail(), userRest.getEmail());
        assertEquals(userDto.isEnabled(), userRest.isEnabled());

        String userJsonString = "{\"name\":\"User\",\"enabled\": true," +
                "\"roles\":[\"ROLE_USER\"]}";

        mockMvc.perform(put(EndPoins.USERS + "/1")
                .content(userJsonString)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept("application/json"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("User"));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(EndPoins.USERS + "/1"))
                .andExpect(status().isNoContent());
    }
}
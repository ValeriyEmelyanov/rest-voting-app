package com.example.restvotingapp.service.Impl;

import com.example.restvotingapp.dto.UserDto;
import com.example.restvotingapp.entity.Role;
import com.example.restvotingapp.entity.User;
import com.example.restvotingapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User userEntity;
    private Set<Role> roles;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userEntity = new User();
        userEntity.setId(100);
        userEntity.setName("User");
        userEntity.setEmail("user@test.com");
        userEntity.setEnabled(true);
        userEntity.setPassword("password");
        roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        userEntity.setRoles(roles);
    }

    @Test
    void getByEmail() {

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getByEmail("user@test.com");

        assertNotNull(userDto);
        assertEquals(userEntity.getId(), userDto.getId());
        assertEquals(userEntity.getName(), userDto.getName());
        assertEquals(userEntity.getEmail(), userDto.getEmail());
        assertEquals(userEntity.getPassword(), userDto.getPassword());
        assertEquals(userEntity.isEnabled(), userDto.isEnabled());
        assertEquals(roles.size(), userDto.getRoles().size());
    }

    @Test
    void getByEmail_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> {userService.getByEmail("test@test.com");});
    }

    @Test
    void create() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        UserDto userDto = userService.create(new UserDto());

        assertNotNull(userDto);
        assertNotNull(userDto.getId());
        assertEquals(userEntity.getName(), userDto.getName());
        assertEquals(userEntity.getEmail(), userDto.getEmail());
        assertEquals(userEntity.getPassword(), userDto.getPassword());
        assertEquals(userEntity.isEnabled(), userDto.isEnabled());
        assertEquals(roles.size(), userDto.getRoles().size());
    }

    @Test
    void create_AlreadyExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        assertThrows(RuntimeException.class,
                () -> {userService.create(new UserDto());});
    }
}
package com.example.restvotingapp.service;

import com.example.restvotingapp.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDto> list(int page, int limit);
    UserDto create(UserDto user);
    UserDto getByEmail(String email);
    UserDto getById(Integer id);
    UserDto update(Integer id, UserDto user);
    void delete(Integer id);
}

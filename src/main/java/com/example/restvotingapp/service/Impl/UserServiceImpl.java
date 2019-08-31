package com.example.restvotingapp.service.Impl;

import com.example.restvotingapp.dto.UserDto;
import com.example.restvotingapp.entity.User;
import com.example.restvotingapp.repository.UserRepository;
import com.example.restvotingapp.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDto> list(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<User> usersPage = userRepository.findAll(pageableRequest);
        List<User> users = usersPage.getContent();

        for (User userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

    @Override
    public UserDto create(UserDto user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("User already exists!");
        }

        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);

        User storedUser = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUser, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getByEmail(String email) {
        User userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new RuntimeException("User with E-Mail: " + email + " not found");
        }

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getById(Integer id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID: " + id + " not found"));

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto update(Integer id, UserDto user) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID: " + id + " not found"));

        userEntity.setName(user.getName());
        userEntity.setEnabled(user.isEnabled());
        userEntity.setRoles(user.getRoles());
        User updatedUser = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @Override
    public void delete(Integer id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID: " + id + " not found"));

        userRepository.delete(userEntity);
    }
}
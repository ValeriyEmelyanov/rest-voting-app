package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.UserDto;
import com.example.restvotingapp.service.UserService;
import com.example.restvotingapp.web.model.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<UserRest> list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.list(page - 1, limit);

        for (UserDto userDto : users) {
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(userDto, userRest);
            returnValue.add(userRest);
        }

        return returnValue;
    }

    @GetMapping(path = "/{id}")
    public UserRest getById(@PathVariable Integer id) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getById(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping
    public UserRest create(@RequestBody UserDto userDetails) {
        UserRest returnValue = new UserRest();

        UserDto createdUser = userService.create(userDetails);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    public UserRest update(@PathVariable Integer id, @RequestBody UserDto userDetails) {
        UserRest returnValue = new UserRest();

        UserDto updatedUser = userService.update(id, userDetails);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }
}

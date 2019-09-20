package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.UserDto;
import com.example.restvotingapp.service.UserService;
import com.example.restvotingapp.util.EndPoins;
import com.example.restvotingapp.web.response.UserRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(EndPoins.USERS)
@Transactional(readOnly = true)
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserRest> list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "25") int limit) {
        log.info("Get users list: page {}, limit {}", page, limit);

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
        log.info("Get user {}", id);

        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getById(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping
    @Transactional
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserRest create(@Valid @RequestBody UserDto userDetails) {
        log.info("Greate user");

        UserRest returnValue = new UserRest();

        UserDto createdUser = userService.create(userDetails);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    @Transactional
    public UserRest update(@PathVariable Integer id, @RequestBody UserDto userDetails) {
        log.info("Update user {}", id);

        UserRest returnValue = new UserRest();

        UserDto updatedUser = userService.update(id, userDetails);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("Delete user {}", id);

        userService.delete(id);
    }
}

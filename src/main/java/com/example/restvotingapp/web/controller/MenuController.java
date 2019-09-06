package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.MenuDto;
import com.example.restvotingapp.service.MenuService;
import com.example.restvotingapp.web.model.MenuRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("menus")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping(path = "/date/{date}")
    public List<MenuRest> listByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<MenuDto> menus = menuService.listByDate(date);
        ModelMapper modelMapper = new ModelMapper();

        return menus
                .stream()
                .map(menuDto -> modelMapper.map(menuDto, MenuRest.class))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/date/{date}/restaraunt/{restaraunt_id}")
    public MenuRest getByDateAndRestaraunt(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable Integer restaraunt_id) {
        return get(menuService.getByDateAndRestaraunt(date, restaraunt_id));
    }

    @GetMapping(path = "/{id}")
    public MenuRest getById(@PathVariable Integer id) {
        return get(menuService.getById(id));
    }

    private MenuRest get(MenuDto menuDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(menuDto, MenuRest.class);
    }

    @PostMapping
    public MenuRest create(@RequestBody MenuDto menuDetails) {
        MenuDto createdMenu = menuService.create(menuDetails);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(createdMenu, MenuRest.class);
    }

    @PutMapping(path = "/{id}")
    public MenuRest update(@PathVariable Integer id, @RequestBody MenuDto menuDetails) {
        MenuDto updatedMenu = menuService.update(id, menuDetails);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(updatedMenu, MenuRest.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        menuService.delete(id);
    }
}

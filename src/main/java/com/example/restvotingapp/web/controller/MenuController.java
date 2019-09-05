package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.MenuDto;
import com.example.restvotingapp.service.MenuService;
import com.example.restvotingapp.web.model.MenuRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("menus")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping(path = "/{date}")
    public List<MenuRest> listByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<MenuDto> menus = menuService.listByDate(date);
        ModelMapper modelMapper = new ModelMapper();

        return menus
                .stream()
                .map(menuDto -> modelMapper.map(menuDto, MenuRest.class))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{date}/restaraunt/{restaraunt_id}")
    public MenuRest getByDateAndRestaraunt(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable Integer restaraunt_id) {
        ModelMapper modelMapper = new ModelMapper();
        MenuDto menu = menuService.getByDateAndRestaraunt(date, restaraunt_id);

        return modelMapper.map(menu, MenuRest.class);
    }

}

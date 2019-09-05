package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.MenuDto;
import com.example.restvotingapp.service.MenuService;
import com.example.restvotingapp.web.model.MenuRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("menus")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping(path = "/{date}")
    public List<MenuRest> listByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<MenuRest> returnValue = new ArrayList<>();

        List<MenuDto> menus = menuService.listByDate(date);

        ModelMapper modelMapper = new ModelMapper();

        for (MenuDto menuDto : menus) {
            MenuRest menuRest = modelMapper.map(menuDto, MenuRest.class);
            returnValue.add(menuRest);
        }

        return returnValue;
    }
}

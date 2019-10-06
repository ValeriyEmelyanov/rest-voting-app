package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.MenuDto;
import com.example.restvotingapp.service.MenuService;
import com.example.restvotingapp.util.EndPoins;
import com.example.restvotingapp.web.response.MenuRest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@Transactional(readOnly = true)
public class MenuController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private MenuService menuService;

    private ModelMapper modelMapper;

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = EndPoins.MENUS_DATE)
    public List<MenuRest> listByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Get menu list for {}", date);

        List<MenuDto> menus = menuService.listByDate(date);

        return menus
                .stream()
                .map(menuDto -> modelMapper.map(menuDto, MenuRest.class))
                .collect(Collectors.toList());
    }

    @GetMapping(path = EndPoins.MENUS_DATE_RESTAURANT)
    public MenuRest getByDateAndRestaurant(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable Integer restaurant_id) {
        log.info("Get menu for {} and  restaurant {}", date, restaurant_id);

        return get(menuService.getByDateAndRestaurant(date, restaurant_id));
    }

    @GetMapping(path = EndPoins.MENUS_ID)
    public MenuRest getById(@PathVariable Integer id) {
        log.info("Get menu {}", id);

        return get(menuService.getById(id));
    }

    private MenuRest get(MenuDto menuDto) {
        return modelMapper.map(menuDto, MenuRest.class);
    }

    @PostMapping(path = EndPoins.MENUS)
    @Transactional
    @ResponseStatus(value = HttpStatus.CREATED)
    public MenuRest create(@Valid @RequestBody MenuDto menuDetails) {
        log.info("Greate menu");

        MenuDto createdMenu = menuService.create(menuDetails);

        return modelMapper.map(createdMenu, MenuRest.class);
    }

    @PutMapping(path = EndPoins.MENUS_ID)
    @Transactional
    public MenuRest update(@PathVariable Integer id, @RequestBody MenuDto menuDetails) {
        log.info("Update menu {}", id);

        MenuDto updatedMenu = menuService.update(id, menuDetails);

        return modelMapper.map(updatedMenu, MenuRest.class);
    }

    @DeleteMapping(path = EndPoins.MENUS_ID)
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("Delete menu {}", id);

        menuService.delete(id);
    }
}

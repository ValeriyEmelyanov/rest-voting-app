package com.example.restvotingapp.service;

import com.example.restvotingapp.dto.MenuDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface MenuService {
    List<MenuDto> listByDate(LocalDate date);
    MenuDto getByDateAndRestaurant(LocalDate date, Integer restaurant_id);
    MenuDto getById(Integer id);
    MenuDto create(MenuDto menuDetails);
    MenuDto update(Integer id, MenuDto menuDetails);
    void delete(Integer id);
}

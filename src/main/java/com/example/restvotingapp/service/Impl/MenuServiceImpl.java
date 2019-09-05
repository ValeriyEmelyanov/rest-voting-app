package com.example.restvotingapp.service.Impl;

import com.example.restvotingapp.dto.MenuDto;
import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.repository.MenuRepository;
import com.example.restvotingapp.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Override
    public List<MenuDto> listByDate(LocalDate date) {
        List<MenuDto> returnValue = new ArrayList<>();

        List<Menu> menus = menuRepository.findAllByDate(date);

        ModelMapper modelMapper = new ModelMapper();

        for (Menu menu : menus) {
            MenuDto menuDto = modelMapper.map(menu, MenuDto.class);
            returnValue.add(menuDto);
        }

        return returnValue;
    }
}

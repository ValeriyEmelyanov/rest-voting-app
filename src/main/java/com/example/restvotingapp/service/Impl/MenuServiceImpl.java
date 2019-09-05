package com.example.restvotingapp.service.Impl;

import com.example.restvotingapp.dto.MenuDto;
import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.entity.Restaraunt;
import com.example.restvotingapp.repository.MenuRepository;
import com.example.restvotingapp.repository.RestarauntRepository;
import com.example.restvotingapp.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    RestarauntRepository restarauntRepository;

    @Override
    public List<MenuDto> listByDate(LocalDate date) {
        List<Menu> menus = menuRepository.findAllByDate(date);
        ModelMapper modelMapper = new ModelMapper();
        return menus
                .stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MenuDto getByDateAndRestaraunt(LocalDate date, Integer restaraunt_id) {
        Restaraunt restarauntEntity = restarauntRepository.findById(restaraunt_id)
                .orElseThrow(() -> new RuntimeException("Restaraunt with ID: " + restaraunt_id + " not found"));

        Menu menuEntity = menuRepository.findByDateAndRestaraunt(date, restarauntEntity);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(menuEntity, MenuDto.class);
    }
}

package com.example.restvotingapp.service;

import com.example.restvotingapp.dto.MenuDto;
import com.example.restvotingapp.entity.Restaraunt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface MenuService {
    List<MenuDto> listByDate(LocalDate date);
    MenuDto getByDateAndRestaraunt(LocalDate date, Integer restaraunt_id);
}

package com.example.restvotingapp.repository;

import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.entity.Restaraunt;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository extends CrudRepository<Menu, Integer> {
    List<Menu> findAllByDate(LocalDate date);
    Menu findByDateAndRestaraunt(LocalDate date, Restaraunt restaraunt);
}

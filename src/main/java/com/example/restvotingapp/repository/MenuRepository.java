package com.example.restvotingapp.repository;

import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.entity.Restaraunt;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuRepository extends CrudRepository<Menu, Integer> {
    Optional<Menu> getById(Integer id);
    List<Menu> findAllByDate(LocalDate date);
    Menu findByDateAndRestaraunt(LocalDate date, Restaraunt restaraunt);
}

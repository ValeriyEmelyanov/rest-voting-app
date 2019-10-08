package com.example.restvotingapp.repository;

import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.entity.Restaurant;
import com.example.restvotingapp.dto.MenuWithoutItemsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Integer> {
    Optional<Menu> getById(Integer id);
    List<Menu> findAllByDate(LocalDate date);
    Menu findByDateAndRestaurant(LocalDate date, Restaurant restaurant);

    @Query("select " +
            "new com.example.restvotingapp.dto.MenuWithoutItemsDto(m.id, m.restaurant, m.date) " +
            "from Menu m where m.date=:date and m.restaurant=:restaurantId")
    Optional<MenuWithoutItemsDto> findByDateAndRestaurantQL(
            @Param("date") LocalDate date, @Param("restaurantId") Restaurant restaurant);
}

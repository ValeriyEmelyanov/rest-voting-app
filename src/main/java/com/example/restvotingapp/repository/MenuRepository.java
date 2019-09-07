package com.example.restvotingapp.repository;

import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.entity.Restaraunt;
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
    Menu findByDateAndRestaraunt(LocalDate date, Restaraunt restaraunt);

    @Query("select " +
            "new com.example.restvotingapp.dto.MenuWithoutItemsDto(m.id, m.restaraunt, m.date) " +
            "from Menu m where m.id=:id")
    MenuWithoutItemsDto getByIdWithoutItems(@Param("id") Integer id);
}

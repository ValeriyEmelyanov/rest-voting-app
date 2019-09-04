package com.example.restvotingapp.repository;

import com.example.restvotingapp.entity.Restaraunt;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestarauntRepository extends PagingAndSortingRepository<Restaraunt, Integer> {
    Optional<Restaraunt> getById(Integer id);
    Restaraunt findByName(String name);
    Optional<List<Restaraunt>> getByActive(boolean active);
    List<Restaraunt> findAllByActive(boolean active);
}

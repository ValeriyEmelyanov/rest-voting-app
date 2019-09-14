package com.example.restvotingapp.repository;

import com.example.restvotingapp.entity.Restaurant;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Integer> {
    Optional<Restaurant> getById(Integer id);
    Restaurant findByName(String name);
    Optional<List<Restaurant>> getByActive(boolean active);
    List<Restaurant> findAllByActive(boolean active);
}

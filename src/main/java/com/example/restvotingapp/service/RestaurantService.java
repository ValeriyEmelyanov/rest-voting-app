package com.example.restvotingapp.service;

import com.example.restvotingapp.dto.RestaurantDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {
    List<RestaurantDto> list(int page, int limit);
    List<RestaurantDto> listActive();
    RestaurantDto getById(Integer id);
    RestaurantDto create(RestaurantDto restaurantDetails);
    RestaurantDto update(Integer id, RestaurantDto restaurantDetails);
    void delete(Integer id);
}

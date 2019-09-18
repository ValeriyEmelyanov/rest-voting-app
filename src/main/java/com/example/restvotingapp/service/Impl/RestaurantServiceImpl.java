package com.example.restvotingapp.service.Impl;

import com.example.restvotingapp.dto.RestaurantDto;
import com.example.restvotingapp.entity.Restaurant;
import com.example.restvotingapp.exceptions.RecordAlreadyExistsException;
import com.example.restvotingapp.exceptions.RecordNotFoundException;
import com.example.restvotingapp.repository.RestaurantRepository;
import com.example.restvotingapp.service.RestaurantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;

    @Autowired
    public void setRestaurantRepository(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<RestaurantDto> list(int page, int limit) {
        List<RestaurantDto> returnValue = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<Restaurant> restaurantsPage = restaurantRepository.findAll(pageableRequest);
        List<Restaurant> restaurants = restaurantsPage.getContent();

        for (Restaurant restaurantEntity : restaurants) {
            RestaurantDto restaurantDto = new RestaurantDto();
            BeanUtils.copyProperties(restaurantEntity, restaurantDto);
            returnValue.add(restaurantDto);
        }

        return returnValue;
    }

    @Override
    public List<RestaurantDto> listActive() {
        List<RestaurantDto> returnValue = new ArrayList<>();

        Optional<List<Restaurant>> optionalRestaurant = restaurantRepository.getByActive(true);
        if (optionalRestaurant.isPresent()) {
            List<Restaurant> restaurants = optionalRestaurant.get();

            for (Restaurant restaurant : restaurants) {
                RestaurantDto restaurantDto = new RestaurantDto();
                BeanUtils.copyProperties(restaurant, restaurantDto);
                returnValue.add(restaurantDto);
            }
        }

        return returnValue;
    }

    @Override
    public RestaurantDto getById(Integer id) {
        Restaurant restaurantEntity = restaurantRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Restaurant with ID: " + id + " not found"));

        RestaurantDto returnValue = new RestaurantDto();
        BeanUtils.copyProperties(restaurantEntity, returnValue);

        return returnValue;
    }

    @Override
    @Transactional
    public RestaurantDto create(RestaurantDto restaurantDetails) {
        if (restaurantRepository.findByName(restaurantDetails.getName()) != null) {
            throw new RecordAlreadyExistsException("Restaurant already exists!");
        }

        Restaurant restaurantEntity = new Restaurant();
        BeanUtils.copyProperties(restaurantDetails, restaurantEntity);

        Restaurant storedRestaurant = restaurantRepository.save(restaurantEntity);

        RestaurantDto returnValue = new RestaurantDto();
        BeanUtils.copyProperties(storedRestaurant, returnValue);

        return returnValue;
    }

    @Override
    @Transactional
    public RestaurantDto update(Integer id, RestaurantDto restaurantDetails) {
        Restaurant restaurantEntity = restaurantRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Restaurant with ID: " + id + " not found"));

        restaurantEntity.setName(restaurantDetails.getName());
        restaurantEntity.setActive(restaurantDetails.isActive());
        Restaurant updatedRestaurant = restaurantRepository.save(restaurantEntity);

        RestaurantDto returnValue = new RestaurantDto();
        BeanUtils.copyProperties(updatedRestaurant, returnValue);

        return returnValue;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Restaurant with ID: " + id + " not found"));

        restaurantRepository.delete(restaurant);
    }
}

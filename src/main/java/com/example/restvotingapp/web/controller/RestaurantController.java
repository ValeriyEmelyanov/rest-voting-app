package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.RestaurantDto;
import com.example.restvotingapp.service.RestaurantService;
import com.example.restvotingapp.util.EndPoins;
import com.example.restvotingapp.web.response.RestaurantRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional(readOnly = true)
public class RestaurantController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private RestaurantService restaurantService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(path = EndPoins.RESTAURANTS)
    public List<RestaurantRest> list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "25") int limit) {
        log.info("Get restaurants list: page {}, limit {}", page, limit);

        List<RestaurantRest> returnValue = new ArrayList<>();
        List<RestaurantDto> restaurants = restaurantService.list(page - 1, limit);

        for (RestaurantDto restaurantDto : restaurants) {
            RestaurantRest restaurantRest = new RestaurantRest();
            BeanUtils.copyProperties(restaurantDto, restaurantRest);
            returnValue.add(restaurantRest);
        }

        return returnValue;
    }

    @GetMapping(path = EndPoins.RESTAURANTS_ACTIVE)
    public List<RestaurantRest> listActive() {
        log.info("Get active restaurants list");

        List<RestaurantRest> returnValue = new ArrayList<>();

        List<RestaurantDto> restaurants = restaurantService.listActive();
        for (RestaurantDto restaurantDto : restaurants) {
            RestaurantRest restaurantRest = new RestaurantRest();
            BeanUtils.copyProperties(restaurantDto, restaurantRest);
            returnValue.add(restaurantRest);
        }

        return returnValue;
    }

    @GetMapping(path = EndPoins.RESTAURANTS_ID)
    public RestaurantRest getById(@PathVariable Integer id) {
        log.info("Get restaurant {}", id);

        RestaurantRest returnValue = new RestaurantRest();

        RestaurantDto restaurantDto = restaurantService.getById(id);
        BeanUtils.copyProperties(restaurantDto, returnValue);

        return returnValue;
    }

    @PostMapping(path = EndPoins.RESTAURANTS)
    @Transactional
    @ResponseStatus(value = HttpStatus.CREATED)
    public RestaurantRest create(@Valid @RequestBody RestaurantDto restaurantDetails) {
        log.info("Greate restaurant");

        RestaurantRest returnValue = new RestaurantRest();

        RestaurantDto createdRestaurant = restaurantService.create(restaurantDetails);
        BeanUtils.copyProperties(createdRestaurant, returnValue);

        return returnValue;
    }

    @PutMapping(path = EndPoins.RESTAURANTS_ID)
    @Transactional
    public RestaurantRest update(@PathVariable Integer id, @RequestBody RestaurantDto restaurantDetails) {
        log.info("Update restaurant {}", id);

        RestaurantRest returnValue = new RestaurantRest();

        RestaurantDto updatedRestaurant = restaurantService.update(id, restaurantDetails);
        BeanUtils.copyProperties(updatedRestaurant, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = EndPoins.RESTAURANTS_ID)
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("Delete restaurant {}", id);

        restaurantService.delete(id);
    }

}

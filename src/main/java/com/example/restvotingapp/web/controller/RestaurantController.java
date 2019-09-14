package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.RestaurantDto;
import com.example.restvotingapp.service.RestaurantService;
import com.example.restvotingapp.web.response.RestaurantRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("restaurants")
@Transactional(readOnly = true)
public class RestaurantController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private RestaurantService restaurantService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
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

    @GetMapping("/active")
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

    @GetMapping(path = "/{id}")
    public RestaurantRest getById(@PathVariable Integer id) {
        log.info("Get restaurant {}", id);

        RestaurantRest returnValue = new RestaurantRest();

        RestaurantDto restaurantDto = restaurantService.getById(id);
        BeanUtils.copyProperties(restaurantDto, returnValue);

        return returnValue;
    }

    @PostMapping
    @Transactional
    public RestaurantRest create(@RequestBody RestaurantDto restaurantDetails) {
        log.info("Greate restaurant");

        RestaurantRest returnValue = new RestaurantRest();

        RestaurantDto createdRestaurant = restaurantService.create(restaurantDetails);
        BeanUtils.copyProperties(createdRestaurant, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    @Transactional
    public RestaurantRest update(@PathVariable Integer id, @RequestBody RestaurantDto restaurantDetails) {
        log.info("Update restaurant {}", id);

        RestaurantRest returnValue = new RestaurantRest();

        RestaurantDto updatedRestaurant = restaurantService.update(id, restaurantDetails);
        BeanUtils.copyProperties(updatedRestaurant, returnValue);

        return returnValue;
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("Delete restaurant {}", id);

        restaurantService.delete(id);
    }

}

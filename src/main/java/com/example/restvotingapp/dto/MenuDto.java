package com.example.restvotingapp.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class MenuDto {

    private Integer id;

    @NotNull
    private RestaurantDto restaurant;

    @NotNull
    private LocalDate date;

    @NotNull
    private List<MenuItemDto> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantDto getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDto restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<MenuItemDto> getItems() {
        return items;
    }

    public void setItems(List<MenuItemDto> items) {
        this.items = items;
    }
}

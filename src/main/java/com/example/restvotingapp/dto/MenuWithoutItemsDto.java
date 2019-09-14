package com.example.restvotingapp.dto;

import com.example.restvotingapp.entity.Restaurant;

import java.time.LocalDate;

public class MenuWithoutItemsDto {
    private Integer id;
    private Restaurant restaurant;
    private LocalDate date;

    public MenuWithoutItemsDto() {
    }

    public MenuWithoutItemsDto(Integer id, Restaurant restaurant, LocalDate date) {
        this.id = id;
        this.restaurant = restaurant;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

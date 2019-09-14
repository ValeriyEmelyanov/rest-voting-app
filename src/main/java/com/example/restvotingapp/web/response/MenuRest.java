package com.example.restvotingapp.web.response;

import java.time.LocalDate;
import java.util.List;

public class MenuRest {
    private Integer id;
    private RestaurantRest restaurant;
    private LocalDate date;
    private List<MenuItemRest> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantRest getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantRest restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<MenuItemRest> getItems() {
        return items;
    }

    public void setItems(List<MenuItemRest> items) {
        this.items = items;
    }
}

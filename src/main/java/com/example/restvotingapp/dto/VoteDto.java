package com.example.restvotingapp.dto;

import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.entity.Restaurant;
import com.example.restvotingapp.entity.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class VoteDto {

    private Long id;

    private User user;

    private Menu menu;

    @NotNull
    private Restaurant restaurant;

    @NotNull
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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

package com.example.restvotingapp.dto;

import java.time.LocalDate;

public class VotePlainDto {
    private Long id;
    private Integer userId;
    private String userName;
    private Integer menuId;
    private Integer restaurantId;
    private String restaurantName;
    private LocalDate date;

    public VotePlainDto() {
    }

    public VotePlainDto(Long id, Integer userId, String userName, Integer menuId,
                        Integer restaurantId, String restaurantName, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.menuId = menuId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

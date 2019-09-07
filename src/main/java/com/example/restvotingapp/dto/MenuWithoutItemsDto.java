package com.example.restvotingapp.dto;

import com.example.restvotingapp.entity.Restaraunt;

import java.time.LocalDate;

public class MenuWithoutItemsDto {
    private Integer id;
    private Restaraunt restaraunt;
    private LocalDate date;

    public MenuWithoutItemsDto() {
    }

    public MenuWithoutItemsDto(Integer id, Restaraunt restaraunt, LocalDate date) {
        this.id = id;
        this.restaraunt = restaraunt;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaraunt getRestaraunt() {
        return restaraunt;
    }

    public void setRestaraunt(Restaraunt restaraunt) {
        this.restaraunt = restaraunt;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

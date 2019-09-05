package com.example.restvotingapp.dto;

import com.example.restvotingapp.entity.MenuItem;
import com.example.restvotingapp.entity.Restaraunt;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class MenuDto {

    private Integer id;

    private RestarauntDto restaraunt;

    @NotNull
    private LocalDate date;

    private List<MenuItemDto> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestarauntDto getRestaraunt() {
        return restaraunt;
    }

    public void setRestaraunt(RestarauntDto restaraunt) {
        this.restaraunt = restaraunt;
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

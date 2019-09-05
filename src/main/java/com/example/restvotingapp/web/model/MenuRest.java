package com.example.restvotingapp.web.model;

import java.time.LocalDate;
import java.util.List;

public class MenuRest {
    private Integer id;
    private RestarauntRest restaraunt;
    private LocalDate date;
    private List<MenuItemRest> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestarauntRest getRestaraunt() {
        return restaraunt;
    }

    public void setRestaraunt(RestarauntRest restaraunt) {
        this.restaraunt = restaraunt;
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

package com.example.restvotingapp.dto;

import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.entity.Restaraunt;
import com.example.restvotingapp.entity.User;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class VoteDto {

    private Long id;

    private User user;

    private Menu menu;

    private Restaraunt restaraunt;

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

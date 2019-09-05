package com.example.restvotingapp.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaraunt_id", "date"},
        name = "menu_restaraunt_date_idx")})
public class Menu extends AbstractBaseEntity {

    @ManyToOne
    @JoinColumn(name = "restaraunt_id")
    private Restaraunt restaraunt;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp")
    @NotNull
    private LocalDate date;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuItem> items;

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

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
}

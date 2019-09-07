package com.example.restvotingapp.entity;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name="votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"},
        name = "vote_user_date_idx")})
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "restaraunt_id")
    private Restaraunt restaraunt;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp")
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

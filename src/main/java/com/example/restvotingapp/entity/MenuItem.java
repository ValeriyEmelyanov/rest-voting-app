package com.example.restvotingapp.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_items", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "dish"},
        name = "menu_dish_idx")})
public class MenuItem extends AbstractBaseEntity {

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "dish", nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 255)
    private String dish;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    @NotNull
    private BigDecimal price;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

package com.example.restvotingapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RestarauntDto {

    private Integer id;

    @NotBlank
    @Size(min = 2, max = 255)
    private String name;

    private boolean active = true;

    public RestarauntDto() {
    }

    public RestarauntDto(Integer id, @NotBlank @Size(min = 2, max = 255) String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

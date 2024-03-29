package com.example.restvotingapp.dto;

import com.example.restvotingapp.entity.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

public class UserDto {

    private Integer id;

    @NotBlank(message = "Please provide a name")
    @Size(min = 2, max = 255)
    private String name;

    @Email
    @NotBlank(message = "Please provide an email")
    @Size(max = 255)
    private String email;

    @NotBlank(message = "Please provide a password")
    @Size(min = 5, max = 255)
    private String password;

    private Date registered = new Date();

    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

package com.example.restvotingapp.web.response;

import java.time.LocalDate;

public class VoteRest {
    private Long id;
    private Integer userId;
    private String userName;
    private Integer menuId;
    private Integer restarauntId;
    private String restarauntName;
    private LocalDate date;

    public VoteRest() {
    }

    public VoteRest(Long id, Integer userId, String userName, Integer menuId,
                    Integer restarauntId, String restarauntName, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.menuId = menuId;
        this.restarauntId = restarauntId;
        this.restarauntName = restarauntName;
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

    public Integer getRestarauntId() {
        return restarauntId;
    }

    public void setRestarauntId(Integer restarauntId) {
        this.restarauntId = restarauntId;
    }

    public String getRestarauntName() {
        return restarauntName;
    }

    public void setRestarauntName(String restarauntName) {
        this.restarauntName = restarauntName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

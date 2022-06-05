package com.example.cotizaciondolar.models.entities;

public class UserLoginHistory {
    private final String name;
    private final String loginDate;

    public UserLoginHistory(String name, String loginDate) {
        this.name = name;
        this.loginDate = loginDate;
    }

    public String getName() {
        return name;
    }

    public String getLoginDate() {
        return loginDate;
    }
}

package com.example.cotizaciondolar.models.entities;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {
    @SerializedName("env")
    private final String environment;
    private final String name;
    @SerializedName("lastname")
    private final String lastName;
    private final int dni;
    private final String email;
    private final String password;
    private final int commission;
    private final int group;

    public SignUpRequest(
            String name,
            String lastName,
            int dni,
            String email,
            String password,
            int commission,
            int group) {
        this.environment = "TEST";
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
        this.password = password;
        this.commission = commission;
        this.group = group;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getCommission() {
        return commission;
    }

    public int getGroup() {
        return group;
    }
}

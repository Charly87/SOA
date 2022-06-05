package com.example.cotizaciondolar.models.entities;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    private final boolean success;
    @SerializedName("env")
    private final String environment;
    private final String token;
    @SerializedName("token_refresh")
    private final String tokenRefresh;
    @SerializedName("msg")
    private final String message;

    public SignUpResponse(boolean success, String environment, String token, String tokenRefresh, String message) {
        this.success = success;
        this.environment = environment;
        this.token = token;
        this.tokenRefresh = tokenRefresh;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getToken() {
        return token;
    }

    public String getTokenRefresh() {
        return tokenRefresh;
    }

    public String getMessage() {
        return message;
    }
}

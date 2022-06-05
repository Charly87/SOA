package com.example.cotizaciondolar.models.entities;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private final boolean success;
    private final String token;
    @SerializedName("token_refresh")
    private final String tokenRefresh;
    @SerializedName("msg")
    private final String message;

    public LoginResponse(boolean success, String token, String tokenRefresh, String msg) {
        this.success = success;
        this.token = token;
        this.tokenRefresh = tokenRefresh;
        this.message = msg;
    }

    public boolean isSuccess() {
        return success;
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

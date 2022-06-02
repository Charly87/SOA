package com.example.cotizaciondolar.models.entities;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("env")
    private String env;
    @SerializedName("token")
    private String token;
    @SerializedName("token_refresh")
    private String tokenRefresh;
    @SerializedName("msg")
    private String msg;

    public SignUpResponse(boolean success, String env, String token, String tokenRefresh, String msg) {
        this.success = success;
        this.env = env;
        this.token = token;
        this.tokenRefresh = tokenRefresh;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenRefresh() {
        return tokenRefresh;
    }

    public void setTokenRefresh(String tokenRefresh) {
        this.tokenRefresh = tokenRefresh;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

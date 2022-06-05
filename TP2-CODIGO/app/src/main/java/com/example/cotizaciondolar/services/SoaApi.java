package com.example.cotizaciondolar.services;

import com.example.cotizaciondolar.models.entities.EventRequest;
import com.example.cotizaciondolar.models.entities.EventResponse;
import com.example.cotizaciondolar.models.entities.LoginRequest;
import com.example.cotizaciondolar.models.entities.LoginResponse;
import com.example.cotizaciondolar.models.entities.SignUpRequest;
import com.example.cotizaciondolar.models.entities.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SoaApi {
    @POST("register")
    Call<SignUpResponse> postRegister(@Body SignUpRequest request);

    @POST("login")
    Call<LoginResponse> postLogin(@Body LoginRequest request);

    @PUT("refresh")
    Call<LoginResponse> putRefreshToken(@Header("Authorization") String tokenRefresh);

    @POST("event")
    Call<EventResponse> postEvent(@Header("Authorization") String token, @Body EventRequest request);
}

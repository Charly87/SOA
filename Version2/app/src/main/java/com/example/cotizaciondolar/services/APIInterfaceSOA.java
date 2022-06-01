package com.example.cotizaciondolar.services;

import com.example.cotizaciondolar.models.services.EventRequest;
import com.example.cotizaciondolar.models.services.EventResponse;
import com.example.cotizaciondolar.models.services.LoginRequest;
import com.example.cotizaciondolar.models.services.RegisterRequest;
import com.example.cotizaciondolar.models.services.LoginResponse;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface APIInterfaceSOA {
    @POST("register")
    Call<LoginResponse> postRegister(@Body RegisterRequest request);

    @POST("login")
    Call<LoginResponse> postLogin(@Body LoginRequest request);

    @PUT("refresh")
    Call<LoginResponse> putRefreshToken(@HeaderMap Map<String, String> headers);

    @POST("event")
    Call<EventResponse> postEvent(@HeaderMap Map<String, String> headers, @Body EventRequest request);
}

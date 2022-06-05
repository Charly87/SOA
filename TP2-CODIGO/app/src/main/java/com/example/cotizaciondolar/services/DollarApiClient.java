package com.example.cotizaciondolar.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DollarApiClient {
    public static final String BASE_URL = "https://api-dolar-argentina.herokuapp.com/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }

        return retrofit;
    }
}

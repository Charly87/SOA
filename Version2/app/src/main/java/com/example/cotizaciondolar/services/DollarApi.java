package com.example.cotizaciondolar.services;

import com.example.cotizaciondolar.models.QuotationResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DollarApi {
    @GET("dolaroficial")
    Call<QuotationResponse> getOfficialQuotation();

    @GET("dolarblue")
    Call<QuotationResponse> getBlueQuotation();

    @GET("dolarbolsa")
    Call<QuotationResponse> getStockQuotation();
}

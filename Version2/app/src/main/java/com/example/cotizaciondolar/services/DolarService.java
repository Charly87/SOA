package com.example.cotizaciondolar.services;

import com.example.cotizaciondolar.models.QuotationModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DolarService {
    @GET("dolaroficial")
    Call<QuotationModel> getOfficialQuotation();

    @GET("dolarblue")
    Call<QuotationModel> getBlueQuotation();

    @GET("dolarbolsa")
    Call<QuotationModel> getStockQuotation();
}

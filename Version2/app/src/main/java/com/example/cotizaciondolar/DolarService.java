package com.example.cotizaciondolar;

import com.example.cotizaciondolar.ui.models.QuotationModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DolarService {
    @GET("dolarblue")
    Call<QuotationModel> getBlueQuotation();
}

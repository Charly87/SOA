package com.example.cotizaciondolar.models;

import static com.example.cotizaciondolar.views.MainActivity.BLUE_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.STOCK_BUTTON_ID;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cotizaciondolar.contracts.QuotationContract;
import com.example.cotizaciondolar.services.DollarApi;
import com.example.cotizaciondolar.services.DollarApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotationModel implements QuotationContract.Model {
    private final String TAG = "QuotationModel";

    @Override
    public void getDollarQuotation(OnFinishedListener onFinishedListener, int checkedId) {

        DollarApi apiService = DollarApiClient.getClient().create(DollarApi.class);
        Call<QuotationResponse> call;

        switch (checkedId) {
            case BLUE_BUTTON_ID:
                call = apiService.getBlueQuotation();
                break;
            case STOCK_BUTTON_ID:
                call = apiService.getStockQuotation();
                break;
            default:
                call = apiService.getOfficialQuotation();
                break;
        }

        call.enqueue(new Callback<QuotationResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuotationResponse> call, @NonNull Response<QuotationResponse> response) {
                QuotationResponse quotationResponse = response.body();
                // TODO: loggear evento
                Log.d(TAG, "asd");
                // TODO: convertir fecha a GMT-3
                onFinishedListener.onFinished(quotationResponse);
            }

            @Override
            public void onFailure(@NonNull Call<QuotationResponse> call, @NonNull Throwable t) {
                // TODO: loggear evento
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }
}

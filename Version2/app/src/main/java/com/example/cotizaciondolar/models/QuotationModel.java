package com.example.cotizaciondolar.models;

import static com.example.cotizaciondolar.models.entities.EventType.QUOTATION_DATA_RETRIEVED;
import static com.example.cotizaciondolar.views.MainActivity.BLUE_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.STOCK_BUTTON_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cotizaciondolar.contracts.QuotationContract;
import com.example.cotizaciondolar.models.entities.EventRequest;
import com.example.cotizaciondolar.models.entities.QuotationResponse;
import com.example.cotizaciondolar.services.DollarApi;
import com.example.cotizaciondolar.services.DollarApiClient;
import com.example.cotizaciondolar.services.EventService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotationModel implements QuotationContract.Model {
    private final String TAG = "QuotationModel";

    private EventService eventService;
    private final Context context;

    public QuotationModel(Context context) {
        this.context = context;
    }

    @Override
    public void getQuotationData(OnFinishedListener onFinishedListener, int checkedId) {
        DollarApi apiService = DollarApiClient.getClient().create(DollarApi.class);
        String quotationType;
        Call<QuotationResponse> call;

        switch (checkedId) {
            case BLUE_BUTTON_ID:
                call = apiService.getBlueQuotation();
                quotationType = "BLUE DOLLAR";
                break;
            case STOCK_BUTTON_ID:
                call = apiService.getStockQuotation();
                quotationType = "STOCK DOLLAR";
                break;
            default:
                call = apiService.getOfficialQuotation();
                quotationType = "OFFICIAL DOLLAR";
                break;
        }

        call.enqueue(new Callback<QuotationResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuotationResponse> call, @NonNull Response<QuotationResponse> response) {
                // TODO: convertir fecha a GMT-3
                QuotationResponse quotationResponse = response.body();

                EventRequest eventRequest = new EventRequest(
                        QUOTATION_DATA_RETRIEVED,
                        "Quotation data retrieved for " + quotationType
                );

                eventService = new EventService(context);
                eventService.execute(eventRequest);

                onFinishedListener.onFinished(quotationResponse);
            }

            @Override
            public void onFailure(@NonNull Call<QuotationResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Error obteniendo cotización: " + t.getMessage());
                onFinishedListener.onFailure(t);
            }
        });
    }
}

package com.example.cotizaciondolar.presenters;

import static com.example.cotizaciondolar.views.MainActivity.BLUE_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.OFFICIAL_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.STOCK_BUTTON_ID;

import android.hardware.SensorEvent;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.contracts.QuotationContract;
import com.example.cotizaciondolar.models.QuotationModel;
import com.example.cotizaciondolar.services.DolarService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuotationPresenter implements QuotationContract.Presenter {
    private final QuotationContract.View view;
    private final DolarService service;
    private StringBuilder builder;
    private float lastZValue;
    private String[] direction;

    private static final int Z_AXIS_TRESHOLD = 2;

    public QuotationPresenter(QuotationContract.View view) {
        this.view = view;
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(((Fragment) view).getResources().getString(R.string.uri_dolar_argentina))
                .build();

        this.service = retrofit.create(DolarService.class);

        builder = new StringBuilder();
        direction = new String[]{"NONE", "NONE"};
    }

    @Override
    public void getDollarQuotation(int checkedId) {
        Call<QuotationModel> execute;
        if (checkedId == STOCK_BUTTON_ID) {
            execute = service.getStockQuotation();
        } else if (checkedId == BLUE_BUTTON_ID) {
            execute = service.getBlueQuotation();
        } else {
            execute = service.getOfficialQuotation();
        }

        execute.enqueue(new Callback<QuotationModel>() {
            @Override
            public void onResponse(Call<QuotationModel> call, Response<QuotationModel> response) {
                QuotationModel body = response.body();

                if (response.isSuccessful()) {
                    // TODO: validar NP + convertir fecha a GMT-3
                    view.setDateText(body.getDate());
                    view.setPurchaseText(body.getPurchasePrice());
                    view.setSaleText(body.getSalePrice());
                } else {
                    view.setDateText("");
                    view.setPurchaseText("");
                    view.setSaleText("");
                }
            }

            @Override
            public void onFailure(Call<QuotationModel> call, Throwable t) {
                Toast.makeText(
                        ((Fragment) view).getContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO: usar un thread para que chequee cada x tiempo y chequear umbral
        float currentZValue = event.values[0];
        float zValueChange = lastZValue - currentZValue;

        lastZValue = currentZValue;

        if (zValueChange > Z_AXIS_TRESHOLD) {
//            logEvent("RIGHT");
            changeCheckedButton(false);
        } else if (zValueChange < -Z_AXIS_TRESHOLD) {
//            logEvent("LEFT");
            changeCheckedButton(true);
        }
    }

    private void changeCheckedButton(boolean toTheRight) {
        int checkedButton = view.getCheckedButton();

        switch (checkedButton) {
            case OFFICIAL_BUTTON_ID:
                if (toTheRight) {
                    view.setCheckedButton(BLUE_BUTTON_ID);
                } else {
                    view.setCheckedButton(STOCK_BUTTON_ID);
                }
            case BLUE_BUTTON_ID:
                if (toTheRight) {
                    view.setCheckedButton(STOCK_BUTTON_ID);
                } else {
                    view.setCheckedButton(OFFICIAL_BUTTON_ID);
                }
            case STOCK_BUTTON_ID:
                if (toTheRight) {
                    view.setCheckedButton(OFFICIAL_BUTTON_ID);
                } else {
                    view.setCheckedButton(BLUE_BUTTON_ID);
                }
        }
    }

    private void logEvent(final String dir) {
        direction[0] = dir;
        builder.setLength(0);
        builder.append("x: ");
        builder.append(direction[0]);
        Log.i("asd", builder.toString());
    }
}

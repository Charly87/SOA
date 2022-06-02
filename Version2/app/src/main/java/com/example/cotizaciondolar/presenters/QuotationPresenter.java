package com.example.cotizaciondolar.presenters;

import static com.example.cotizaciondolar.views.MainActivity.BLUE_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.OFFICIAL_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.STOCK_BUTTON_ID;

import android.hardware.SensorEvent;
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
    private float lastZValue;
    private boolean recentlyMoved = false;

    // Umbral de deteccion de movimiento del eje Z del acelerometro
    // En 5 detecta movimientos a partir de los 30 grados de diferencia
    // con la posicion anterior
    private static final int Z_AXIS_THRESHOLD = 5;

    public QuotationPresenter(QuotationContract.View view) {
        this.view = view;
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(((Fragment) view).getResources().getString(R.string.uri_dolar_argentina))
                .build();

        this.service = retrofit.create(DolarService.class);
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
        // TODO: mover a una clase utils
        float currentZValue = event.values[0];
        // Solo se tomara como valido un movimiento de hasta 90 grados (valor 9.8 del acelerometro)
        if (currentZValue > -9.8 && currentZValue < 9.8) {

            // Si hubo un movimiento no volvera a realizar accion al volver a la posicion normal del celular
            if (recentlyMoved) {
                if (currentZValue > -Z_AXIS_THRESHOLD && currentZValue < Z_AXIS_THRESHOLD) {
                    lastZValue = 0;
                    recentlyMoved = false;
                    return;
                }
            }

            float zValueChange = lastZValue - currentZValue;

            lastZValue = currentZValue;

            // Solo son leidos como movimientos los que sean mayores a 30 grados
            if (zValueChange > Z_AXIS_THRESHOLD) {
                changeCheckedButton(true);
            } else if (zValueChange < -Z_AXIS_THRESHOLD) {
                changeCheckedButton(false);
            }
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
                recentlyMoved = true;
                break;
            case BLUE_BUTTON_ID:
                if (toTheRight) {
                    view.setCheckedButton(STOCK_BUTTON_ID);
                } else {
                    view.setCheckedButton(OFFICIAL_BUTTON_ID);
                }
                recentlyMoved = true;
                break;
            case STOCK_BUTTON_ID:
                if (toTheRight) {
                    view.setCheckedButton(OFFICIAL_BUTTON_ID);
                } else {
                    view.setCheckedButton(BLUE_BUTTON_ID);
                }
                recentlyMoved = true;
                break;
            default:
                break;
        }
    }
}

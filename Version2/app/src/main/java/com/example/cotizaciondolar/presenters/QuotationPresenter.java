package com.example.cotizaciondolar.presenters;

import static com.example.cotizaciondolar.views.MainActivity.BLUE_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.OFFICIAL_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.STOCK_BUTTON_ID;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.example.cotizaciondolar.contracts.QuotationContract;
import com.example.cotizaciondolar.models.QuotationModel;
import com.example.cotizaciondolar.models.entities.QuotationResponse;

public class QuotationPresenter implements
        QuotationContract.Presenter,
        QuotationContract.Model.OnFinishedListener,
        SensorEventListener {
    private final QuotationContract.View view;
    private final QuotationContract.Model model;

    private static final int Z_AXIS_THRESHOLD = 5;
    private boolean recentlyMoved = false;
    private float lastZValue;

    public QuotationPresenter(QuotationContract.View view) {
        this.view = view;
        this.model = new QuotationModel();
    }

    @Override
    public void requestDataFromServer(int checkedId, boolean isChecked) {
        // Solo llama a la API cuando el boton de cotizacion esta presionado
        if (isChecked) {
            model.getDollarQuotation(this, checkedId);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float currentZValue = event.values[0];

        // Solo se tomara como valido un movimiento de hasta 90 grados (valor 9.81 del acelerometro)
        if (currentZValue > -9.81 && currentZValue < 9.81) {

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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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

    @Override
    public void onFinished(QuotationResponse quotationResponse) {
        view.setDateText(quotationResponse.getDate());
        view.setPurchaseText(quotationResponse.getPurchasePrice());
        view.setSaleText(quotationResponse.getSalePrice());
    }

    @Override
    public void onFailure(Throwable t) {
        view.showLongToast(t.getMessage());
    }
}

package com.example.cotizaciondolar.presenters;

import static com.example.cotizaciondolar.views.MainActivity.BLUE_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.OFFICIAL_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.STOCK_BUTTON_ID;

import android.content.Context;
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
    private static final double ACCELEROMETER_THRESHOLD = 6;
    private boolean recentlyMoved;
    private float lastZValue;

    public QuotationPresenter(QuotationContract.View view, Context context) {
        this.view = view;
        this.model = new QuotationModel(context);
        recentlyMoved = false;
        lastZValue = 0;
    }

    @Override
    public void requestDataFromServer(int checkedId, boolean isChecked) {
        // Solo llama a la API cuando el boton que disparó el evento está presionado
        // ya que el evento se dispara al presionar y al dejar de estar presionado
        if (isChecked) {
            model.getQuotationData(this, checkedId);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float currentZValue = event.values[0];

        // Si hubo un movimiento anteriormente no volvera a realizar accion al volver
        // a la posicion  habitual del celular, es decir, vertical
        if (recentlyMoved) {
            if (currentZValue > -Z_AXIS_THRESHOLD && currentZValue < Z_AXIS_THRESHOLD) {
                lastZValue = 0;
                recentlyMoved = false;
                return;
            }
        }

        // Guardamos la diferencia de posicion del eje Z para compararla posteriormente
        float zValueChange = lastZValue - currentZValue;
        lastZValue = currentZValue;

        // Solo son leidos como movimientos los que sean mayores a un angulo 30 grados
        // con respecto a la posicion anterior. Si la diferencia de posicion es positiva, el
        // movimiento es hacia la izquierda, de lo contrario, es hacia la derecha
        if (zValueChange > Z_AXIS_THRESHOLD) {
            changeCheckedButton(true);
        } else if (zValueChange < -Z_AXIS_THRESHOLD) {
            changeCheckedButton(false);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No realizamos accion al cambiar la accuracy
    }

    private void changeCheckedButton(boolean toTheRight) {
        int checkedButton = view.getCheckedButton();

        // Segun para que lado haya sido el movimiento, cambiamos el boton presionado al que
        // se encuentra de dicho lado del boton presionado actualmente
        switch (checkedButton) {
            case OFFICIAL_BUTTON_ID:
                if (toTheRight) {
                    view.setCheckedButton(BLUE_BUTTON_ID);
                    recentlyMoved = true;
                }

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
                if (!toTheRight) {
                    view.setCheckedButton(BLUE_BUTTON_ID);
                    recentlyMoved = true;
                }

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

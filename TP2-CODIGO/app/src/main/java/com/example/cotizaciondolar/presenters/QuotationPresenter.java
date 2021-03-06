package com.example.cotizaciondolar.presenters;

import static com.example.cotizaciondolar.views.MainActivity.BLUE_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.OFFICIAL_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.STOCK_BUTTON_ID;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.cotizaciondolar.contracts.QuotationContract;
import com.example.cotizaciondolar.models.QuotationModel;
import com.example.cotizaciondolar.models.entities.QuotationResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuotationPresenter implements
        QuotationContract.Presenter,
        QuotationContract.Model.OnFinishedListener,
        SensorEventListener {
    private final QuotationContract.View view;
    private final QuotationContract.Model model;
    private final Context context;

    private static final int Z_AXIS_THRESHOLD = 5;
    private boolean recentlyMoved;
    private float lastZValue;

    public QuotationPresenter(QuotationContract.View view, Context context) {
        this.view = view;
        this.model = new QuotationModel(context);
        this.context = context;
        this.recentlyMoved = false;
        this.lastZValue = 0;

        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        manager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    @Override
    public void requestDataFromServer(int checkedId, boolean isChecked) {
        // Solo llama a la API cuando el boton que disparó el evento está presionado
        // ya que el evento se dispara al presionar y al dejar de estar presionado
        if (isChecked) {
            if (isConnected()) {

                model.getQuotationData(this, checkedId);
            } else {
                view.showLongToast("No hay conexión a internet");
            }
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

                model.registerSensorEvent(toTheRight);
                break;
            case BLUE_BUTTON_ID:
                if (toTheRight) {
                    view.setCheckedButton(STOCK_BUTTON_ID);
                } else {
                    view.setCheckedButton(OFFICIAL_BUTTON_ID);
                }

                recentlyMoved = true;
                model.registerSensorEvent(toTheRight);
                break;
            case STOCK_BUTTON_ID:
                if (!toTheRight) {
                    view.setCheckedButton(BLUE_BUTTON_ID);
                    recentlyMoved = true;
                }

                model.registerSensorEvent(toTheRight);
                break;
            default:
                break;
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }


    @Override
    public void onFinished(QuotationResponse quotationResponse) {
        String dateString = quotationResponse.getDate();
        String purchaseText = quotationResponse.getPurchasePrice();
        String saleText = quotationResponse.getSalePrice();

        // Convierte la fecha y hora a la local de Argentina (GMT-3) y ajusta el formato
        DateTimeFormatter formatterGmt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTimeGmt = LocalDateTime.parse(dateString, formatterGmt);
        LocalDateTime dateTimeArg = dateTimeGmt.minusHours(3);
        DateTimeFormatter formatterArg = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        String dateText = dateTimeArg.format(formatterArg);

        view.setDateText(dateText);
        view.setPurchaseText(purchaseText);
        view.setSaleText(saleText);
    }

    @Override
    public void onFailure(Throwable t) {
        view.showLongToast(t.getMessage());
    }
}

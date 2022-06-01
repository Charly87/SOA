package com.example.cotizaciondolar.views;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.contracts.QuotationContract;
import com.example.cotizaciondolar.databinding.FragmentQuotationBinding;
import com.example.cotizaciondolar.presenters.QuotationPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class QuotationFragment extends Fragment implements
        QuotationContract.View,
        SensorEventListener {
    private static final String TAG = "QuotationFragment";

    private MaterialButtonToggleGroup buttonToggleGroup;
    private MaterialButton officialButton;
    private MaterialButton blueButton;
    private MaterialButton stockButton;
    private TextView dateText;
    private TextView purchaseText;
    private TextView saleText;
    private FragmentQuotationBinding binding;
    private QuotationContract.Presenter presenter;

    private StringBuilder builder;
    private float[] history;
    private String[] direction;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentQuotationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        presenter = new QuotationPresenter(this);

        buttonToggleGroup = binding.buttonToggleGroup;
        dateText = binding.textDate;
        purchaseText = binding.textPurchase;
        saleText = binding.textSale;
        officialButton = binding.btnOfficial;
        blueButton = binding.btnBlue;
        stockButton = binding.btnStock;

        SensorManager manager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        setListeners();
        builder = new StringBuilder();
        history = new float[2];
        direction = new String[]{"NONE", "NONE"};

        return root;
    }

    private void setListeners() {
        // Selecciona el boton de cot. oficial
        buttonToggleGroup.addOnButtonCheckedListener(
                new MaterialButtonToggleGroup.OnButtonCheckedListener() {
                    @Override
                    public void onButtonChecked(
                            MaterialButtonToggleGroup group,
                            int checkedId,
                            boolean isChecked) {
                        // Solo ejecutamos este metodo cuando el boton esta checked
                        if (isChecked) {
                            presenter.getDollarQuotation(checkedId);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        buttonToggleGroup.check(R.id.btn_official);
    }

    @Override
    public void setDateText(String date) {
        dateText.setText(date);
    }

    @Override
    public void setPurchaseText(final String purchase) {
        purchaseText.setText(purchase);
    }

    @Override
    public void setSaleText(String sale) {
        saleText.setText(sale);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO: usar un thread para que chequee cada x tiempo y chequear umbral
        float xChange = history[0] - event.values[0];
//        float yChange = history[1] - event.values[1];

        history[0] = event.values[0];
//        history[1] = event.values[1];

        if (xChange > 2) {
            logEvent("RIGHT");
        } else if (xChange < -2) {
            logEvent("LEFT");
        }

//        if (yChange > 2){
//            direction[1] = "DOWN";
//        }
//        else if (yChange < -2){
//            direction[1] = "UP";
//        }

//        builder.append(" y: ");
//        builder.append(direction[1]);


    }

    private void logEvent(final String dir) {
        direction[0] = dir;
        builder.setLength(0);
        builder.append("x: ");
        builder.append(direction[0]);
        Log.i(TAG, builder.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

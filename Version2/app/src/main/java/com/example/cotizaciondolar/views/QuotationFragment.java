package com.example.cotizaciondolar.views;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        setListeners();

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
    public int getCheckedButton() {
        return buttonToggleGroup.getCheckedButtonId();
    }

    @Override
    public void setCheckedButton(int buttonId) {
        buttonToggleGroup.check(buttonId);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        presenter.onSensorChanged(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

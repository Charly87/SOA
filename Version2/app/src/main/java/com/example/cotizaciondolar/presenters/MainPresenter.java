package com.example.cotizaciondolar.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.example.cotizaciondolar.contracts.MainContract;
import com.example.cotizaciondolar.models.MainModel;
import com.example.cotizaciondolar.views.CodeActivity;

public class MainPresenter implements
        MainContract.Presenter,
        SensorEventListener {

    private final MainContract.View view;
    private final MainContract.Model model;

    public MainPresenter(MainContract.View view, Context context) {
        this.view = view;
        this.model = new MainModel(context);
    }

    @Override
    public void onLogoutClick() {
        model.logoutUser();

        Intent intent = new Intent((Activity) this.view, CodeActivity.class);
        ((Activity) this.view).startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float proximity = event.values[0];
        if (proximity < 5) {
            view.showLogoutDialog();
            model.registerSensorEvent(proximity);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No realizamos accion al cambiar la accuracy
    }
}

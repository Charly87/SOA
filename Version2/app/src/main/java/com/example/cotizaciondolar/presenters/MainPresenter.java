package com.example.cotizaciondolar.presenters;

import android.app.Activity;
import android.content.Intent;

import com.example.cotizaciondolar.contracts.MainContract;
import com.example.cotizaciondolar.models.GlobalSession;
import com.example.cotizaciondolar.views.CodeActivity;

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mainView;

    public MainPresenter(MainContract.View mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onLogoutClick() {
        // TODO: resetear los datos del usuario logueado
        GlobalSession.authToken = null;
        GlobalSession.refreshToken = null;
        Intent intent = new Intent((Activity) this.mainView, CodeActivity.class);
        ((Activity) this.mainView).startActivity(intent);
    }
}

package com.example.cotizaciondolar.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.cotizaciondolar.contracts.MainContract;
import com.example.cotizaciondolar.models.MainModel;
import com.example.cotizaciondolar.views.CodeActivity;

public class MainPresenter implements
        MainContract.Presenter {

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
}

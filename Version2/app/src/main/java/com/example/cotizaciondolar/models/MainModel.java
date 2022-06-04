package com.example.cotizaciondolar.models;

import android.content.Context;

import com.example.cotizaciondolar.contracts.MainContract;
import com.example.cotizaciondolar.services.SessionManager;

public class MainModel implements MainContract.Model {
    private final SessionManager sessionManager;

    public MainModel(Context context) {
        sessionManager = new SessionManager(context);
    }

    @Override
    public void logoutUser() {
        sessionManager.logoutUser();
    }
}

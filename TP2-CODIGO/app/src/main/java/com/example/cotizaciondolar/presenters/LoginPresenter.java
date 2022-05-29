package com.example.cotizaciondolar.presenters;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.cotizaciondolar.LoginActivityContract;

public class LoginPresenter implements LoginActivityContract.Presenter {

    private LoginActivityContract.View view;
    private LoginActivityContract.Model model;

    public LoginPresenter(LoginActivityContract.View mainView, LoginActivityContract.Model model){
        this.view = mainView;
        this.model = model;

        this.Initialize();
    }

    private void Initialize()
    {
        Intent intent=((Activity) this.view).getIntent();
        Bundle extras=intent.getExtras();
        String texto= extras.getString("text");
        this.view.setText(texto);
    }
}

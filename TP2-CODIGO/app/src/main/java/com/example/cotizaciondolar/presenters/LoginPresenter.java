package com.example.cotizaciondolar.presenters;

import android.app.Activity;
import android.content.Intent;

import com.example.cotizaciondolar.contracts.LoginContract;
import com.example.cotizaciondolar.models.LoginModel;
import com.example.cotizaciondolar.models.entities.LoginRequest;
import com.example.cotizaciondolar.views.MainActivity;
import com.example.cotizaciondolar.views.SignUpActivity;

public class LoginPresenter implements
        LoginContract.Presenter,
        LoginContract.Model.OnFinishedListener {

    private final LoginContract.View view;
    private final LoginContract.Model model;

    public LoginPresenter(LoginContract.View mainView) {
        this.view = mainView;
        this.model = new LoginModel((Activity) mainView);
    }

    @Override
    public void onLoginButtonClicked() {
        String email = this.view.getUsername();
        String password = this.view.getPassword();

        LoginRequest request = new LoginRequest(email, password);

        this.model.loginUser(request, this);
    }

    @Override
    public void onSignUpButtonClicked() {
        Intent intent = new Intent((Activity) this.view, SignUpActivity.class);
        ((Activity) this.view).startActivity(intent);
    }

    @Override
    public void onSuccess() {
        Intent intent = new Intent((Activity) this.view, MainActivity.class);
        ((Activity) this.view).startActivity(intent);
    }

    @Override
    public void onError() {
        this.view.showLongToast("Usuario o contraseña incorrectos");
    }
}

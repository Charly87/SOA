package com.example.cotizaciondolar.presenters;

import android.app.Activity;
import android.content.Intent;

import com.example.cotizaciondolar.DataAccess;
import com.example.cotizaciondolar.contracts.LoginContract;
import com.example.cotizaciondolar.models.LoginModel;
import com.example.cotizaciondolar.views.MainActivity;
import com.example.cotizaciondolar.views.SignUpActivity;

public class LoginPresenter implements
        LoginContract.Presenter,
        LoginContract.Model.OnFinishedListener {

    private final LoginContract.View view;
    private final LoginContract.Model model;

    public LoginPresenter(LoginContract.View mainView) {
        this.view = mainView;
        this.model = new LoginModel();
    }

    @Override
    public void login() {
        this.model.validateUser(this.view.getUsername(), this.view.getPassword(), this);
    }

    // boton para ir de loggin a registrar
    @Override
    public void signUp() {
        Intent intent = new Intent((Activity) this.view, SignUpActivity.class);
        ((Activity) this.view).startActivity(intent);
    }

    @Override
    public void onSuccess() {
        DataAccess dal = new DataAccess(((Activity) this.view));
        dal.insertUserHistory(this.view.getUsername());

        Intent intent = new Intent((Activity) this.view, MainActivity.class);
        ((Activity) this.view).startActivity(intent);
    }

    @Override
    public void onError(String msg) {
        this.view.showLongToast(msg);
    }

    @Override
    public void onFailure(Throwable t) {
        this.view.showLongToast("Error general");
    }
}

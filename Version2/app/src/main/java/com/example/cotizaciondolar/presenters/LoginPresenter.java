package com.example.cotizaciondolar.presenters;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;

import com.example.cotizaciondolar.DataAccess;
import com.example.cotizaciondolar.contracts.LoginActivityContract;
import com.example.cotizaciondolar.models.LoginModel;
import com.example.cotizaciondolar.views.MainActivity;

import java.util.List;

public class LoginPresenter implements LoginActivityContract.Presenter, LoginActivityContract.Model.OnFinishedListener {

    private LoginActivityContract.View view;
    private LoginActivityContract.Model model;


    public LoginPresenter(LoginActivityContract.View mainView) {
        this.view = mainView;
        this.model = new LoginModel();
    }

    @Override
    public void Login() {
        this.model.ValidateUser(this.view.getUsername(), this.view.getPassword(),this);
    }

    @Override
    public void onSuccess() {
        DataAccess dal = new DataAccess(((Activity)this.view));
        dal.insertUserHistory(this.view.getUsername());

        Intent intent = new Intent((Activity) this.view, MainActivity.class);
        ((Activity) this.view).startActivity(intent);
    }

    @Override
    public void onError(String msg) {
        this.view.setMessage(msg);
    }

    @Override
    public void onFailure(Throwable t) {
        this.view.setMessage("Error general");
    }
}

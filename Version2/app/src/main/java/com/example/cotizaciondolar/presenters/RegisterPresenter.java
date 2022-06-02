package com.example.cotizaciondolar.presenters;

import android.app.Activity;
import android.content.Intent;

import com.example.cotizaciondolar.DataAccess;
import com.example.cotizaciondolar.contracts.LoginActivityContract;
import com.example.cotizaciondolar.contracts.RegisterActivityContract;
import com.example.cotizaciondolar.models.LoginModel;
import com.example.cotizaciondolar.models.RegisterModel;
import com.example.cotizaciondolar.views.MainActivity;
import com.example.cotizaciondolar.views.RegisterActivity;

public class RegisterPresenter implements RegisterActivityContract.Presenter, RegisterActivityContract.Model.OnFinishedListener {

    private RegisterActivityContract.View view;
    private RegisterActivityContract.Model model;

    public RegisterPresenter(RegisterActivityContract.View mainView) {
        this.view = mainView;
        this.model = new RegisterModel();
    }

    @Override
    public void onSuccess() {
        DataAccess dal = new DataAccess(((Activity)this.view));
        dal.insertUserHistory(this.view.getEmail());

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

    @Override
    public void Register() {
        this.model.RegisterUser(this.view.getName(),this.view.getLastName(),this.view.getDni(), this.view.getEmail(), this.view.getPassword(),this.view.getCommission(),this.view.getGroup(),this);
    }
}

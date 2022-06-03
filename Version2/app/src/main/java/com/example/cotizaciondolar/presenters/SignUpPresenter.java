package com.example.cotizaciondolar.presenters;

import android.app.Activity;
import android.content.Intent;

import com.example.cotizaciondolar.DataAccess;
import com.example.cotizaciondolar.contracts.SignUpContract;
import com.example.cotizaciondolar.models.SignUpModel;
import com.example.cotizaciondolar.models.entities.SignUpRequest;
import com.example.cotizaciondolar.views.LoginActivity;
import com.example.cotizaciondolar.views.MainActivity;

public class SignUpPresenter implements
        SignUpContract.Presenter,
        SignUpContract.Model.OnFinishedListener {

    private final SignUpContract.View view;
    private final SignUpContract.Model model;

    public SignUpPresenter(SignUpContract.View mainView) {
        this.view = mainView;
        this.model = new SignUpModel();
    }

    @Override
    public void onSuccess() {
        DataAccess dal = new DataAccess(((Activity) this.view));
        dal.insertUserHistory(this.view.getEmail());

        Intent intent = new Intent((Activity) this.view, MainActivity.class);
        ((Activity) this.view).startActivity(intent);
    }

    @Override
    public void onError(String msg) {
        this.view.showShortToast(msg);
    }

    @Override
    public void onFailure(Throwable t) {
        this.view.showShortToast("Error general");
    }

    @Override
    public void onConfirmButtonClick() {
        SignUpRequest signUpRequest = new SignUpRequest(
                this.view.getName(),
                this.view.getLastName(),
                this.view.getDni(),
                this.view.getEmail(),
                this.view.getPassword(),
                this.view.getCommission(),
                this.view.getGroup()

        );

        this.model.signUpUser(signUpRequest, this);
    }

    @Override
    public void onCancelButtonClick() {
        Intent intent = new Intent((Activity) view, LoginActivity.class);
        ((Activity) view).startActivity(intent);
    }
}

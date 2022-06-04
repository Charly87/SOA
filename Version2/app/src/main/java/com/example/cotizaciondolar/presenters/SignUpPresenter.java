package com.example.cotizaciondolar.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;

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

    public SignUpPresenter(SignUpContract.View mainView, Context context) {
        this.view = mainView;
        this.model = new SignUpModel(context);
    }

    @Override
    public void onSuccess() {
        Intent intent = new Intent((Activity) this.view, MainActivity.class);
        ((Activity) this.view).startActivity(intent);
    }

    @Override
    public void onError(String msg) {
        this.view.showShortToast("Error en el registro: " + msg);
    }

    @Override
    public void onConfirmButtonClick() {
        SignUpRequest signUpRequest = validateSignUpData(
                this.view.getName(),
                this.view.getLastName(),
                this.view.getDni(),
                this.view.getEmail(),
                this.view.getPassword(),
                this.view.getCommission(),
                this.view.getGroup()
        );

        if (signUpRequest == null) {
            view.showShortToast("Alguno de los campos es incorrecto o está vacío");
        } else {
            this.model.signUpUser(signUpRequest, this);
        }
    }

    private SignUpRequest validateSignUpData(
            Editable name,
            Editable lastName,
            Editable dni,
            Editable email,
            Editable password,
            Editable commission,
            Editable group) {
        if (name == null ||
                lastName == null ||
                dni == null ||
                email == null ||
                password == null ||
                commission == null ||
                group == null) {
            return null;
        } else if (name.toString().isEmpty() ||
                lastName.toString().isEmpty() ||
                dni.toString().isEmpty() ||
                email.toString().isEmpty() ||
                password.toString().isEmpty() ||
                commission.toString().isEmpty() ||
                group.toString().isEmpty()) {
            return null;
        } else if (password.toString().length() < 8 || !commission.toString().equals("1900")) {
            return null;
        }

        return new SignUpRequest(
                name.toString(),
                lastName.toString(),
                Integer.parseInt(dni.toString()),
                email.toString(),
                password.toString(),
                Integer.parseInt(commission.toString()),
                Integer.parseInt(group.toString())
        );
    }

    @Override
    public void onCancelButtonClick() {
        Intent intent = new Intent((Activity) view, LoginActivity.class);
        ((Activity) view).startActivity(intent);
    }
}

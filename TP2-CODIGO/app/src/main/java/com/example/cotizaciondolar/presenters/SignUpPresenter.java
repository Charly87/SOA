package com.example.cotizaciondolar.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;

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
    private final Context context;

    public SignUpPresenter(SignUpContract.View mainView, Context context) {
        this.context = context;
        this.view = mainView;
        this.model = new SignUpModel(context);
    }

    @Override
    public void onSuccess() {
        view.showShortToast("Usuario registrado");
        Intent intent = new Intent((Activity) this.view, MainActivity.class);
        ((Activity) this.view).startActivity(intent);
    }

    @Override
    public void onError(String msg) {
        this.view.showShortToast("Error en el registro: " + msg);
    }

    @Override
    public void onConfirmButtonClick() {
        if (isConnected()) {
            Editable name = view.getName();
            Editable lastName = view.getLastName();
            Editable dni = view.getDni();
            Editable email = view.getEmail();
            Editable password = view.getPassword();
            Editable commission = view.getCommission();
            Editable group = view.getGroup();

            boolean noErrors = true;

            if (TextUtils.isEmpty(name) ||
                    TextUtils.isEmpty(lastName) ||
                    TextUtils.isEmpty(dni) ||
                    TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password) ||
                    TextUtils.isEmpty(commission) ||
                    TextUtils.isEmpty(group)) {

                view.showShortToast("Debe completar todos los campos");
                return;
            }

            view.setEmailError(null);
            view.setCommissionError(null);
            view.setPasswordError(null);


            if (password.length() < 8) {
                view.setPasswordError("Mínimo 8 caracteres");
                noErrors = false;
            }

            if (!commission.toString().equals("1900") &&
                    !commission.toString().equals("3900")) {
                view.setCommissionError("Comisión no válida");
                noErrors = false;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                view.setEmailError("Email no válido");
                noErrors = false;
            }

            if (noErrors) {
                SignUpRequest signUpRequest = new SignUpRequest(
                        name.toString(),
                        lastName.toString(),
                        Integer.parseInt(dni.toString()),
                        email.toString(),
                        password.toString(),
                        Integer.parseInt(commission.toString()),
                        Integer.parseInt(group.toString())
                );

                this.model.signUpUser(signUpRequest, this);
            }
        } else {
            view.showShortToast("No hay conexión a internet");
        }
    }

    @Override
    public void onCancelButtonClick() {
        Intent intent = new Intent((Activity) view, LoginActivity.class);
        ((Activity) view).startActivity(intent);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}

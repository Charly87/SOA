package com.example.cotizaciondolar.contracts;

import com.example.cotizaciondolar.models.entities.LoginRequest;

public interface LoginContract {

    interface View {
        void showLongToast(String msg);

        String getUsername();

        String getPassword();
    }

    interface Model {
        interface OnFinishedListener {
            void onSuccess();

            void onError();
        }

        void loginUser(LoginRequest loginRequest, OnFinishedListener onFinishedListener);
    }

    interface Presenter {
        void onLoginButtonClicked();

        void onSignUpButtonClicked();
    }

}

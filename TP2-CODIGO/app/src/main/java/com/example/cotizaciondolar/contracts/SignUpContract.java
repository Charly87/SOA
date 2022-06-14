package com.example.cotizaciondolar.contracts;

import android.text.Editable;

import com.example.cotizaciondolar.models.entities.SignUpRequest;

public interface SignUpContract {

    interface View {
        void showShortToast(String msg);

        void setPasswordError(String error);

        void setCommissionError(String error);

        void setEmailError(String error);

        Editable getName();

        Editable getLastName();

        Editable getDni();

        Editable getEmail();

        Editable getPassword();

        Editable getCommission();

        Editable getGroup();
    }

    interface Model {
        interface OnFinishedListener {
            void onSuccess();

            void onError(String msg);
        }

        void signUpUser(SignUpRequest signUpRequest, OnFinishedListener onFinishedListener);
    }

    interface Presenter {
        void onConfirmButtonClick();

        void onCancelButtonClick();
    }
}

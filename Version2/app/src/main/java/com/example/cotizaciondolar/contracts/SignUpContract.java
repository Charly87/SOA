package com.example.cotizaciondolar.contracts;

import android.text.Editable;

import com.example.cotizaciondolar.models.entities.SignUpRequest;

public interface SignUpContract {

    interface View {

        void showShortToast(String msg);

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

            void onFailure(Throwable t);
        }

        void signUpUser(SignUpRequest signUpRequest, OnFinishedListener onFinishedListener);
    }

    interface Presenter {
        void onConfirmButtonClick();

        void onCancelButtonClick();
    }
}

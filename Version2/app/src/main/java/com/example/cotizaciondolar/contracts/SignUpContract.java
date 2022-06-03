package com.example.cotizaciondolar.contracts;

import com.example.cotizaciondolar.models.entities.SignUpRequest;

public interface SignUpContract {

    interface View {

        void showShortToast(String msg);

        String getName();

        String getLastName();

        String getDni();

        String getEmail();

        String getPassword();

        String getCommission();

        String getGroup();

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

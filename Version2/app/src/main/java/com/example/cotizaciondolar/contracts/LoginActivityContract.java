package com.example.cotizaciondolar.contracts;

public interface LoginActivityContract {

    interface View {
        void setMessage(String msg);
        String getUsername();
        String getPassword();
    }

    interface Model {
        interface OnFinishedListener {
            void onSuccess();
            void onError(String msg);
            void onFailure(Throwable t);
        }

        void ValidateUser(String username, String password, OnFinishedListener onFinishedListener);
    }

    interface Presenter {
        void Login();
        void Regis();
    }

}

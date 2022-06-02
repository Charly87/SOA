package com.example.cotizaciondolar.contracts;

public interface LoginContract {

    interface View {
        void showLongToast(String msg);

        String getUsername();

        String getPassword();
    }

    interface Model {
        interface OnFinishedListener {
            void onSuccess();

            void onError(String msg);

            void onFailure(Throwable t);
        }

        void validateUser(String username, String password, OnFinishedListener onFinishedListener);
    }

    interface Presenter {
        void login();

        void signUp();
    }

}

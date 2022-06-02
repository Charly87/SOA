package com.example.cotizaciondolar.contracts;

public interface RegisterActivityContract {

    void setMessage(String msg);

    String getName();

    String getLastName();

    String getDni();

    String getEmail();

    String getPassword();

    String getCommission();

    String getGroup();

    interface View {

        void setMessage(String msg);
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

        void RegisterUser(String name, String lastname, String dni, String email, String password, String commission, String group, RegisterActivityContract.Model.OnFinishedListener onFinishedListener);
    }

    interface Presenter {

        void Register();
    }
}

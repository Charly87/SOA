package com.example.cotizaciondolar.contracts;

public interface CodeContract {

    interface View {
        String getCode();

        void cleanError();

        void setError(String error);
    }

    interface Model {
        String getActiveCode();

        String generateNewCode();
    }

    interface Presenter {
        void onConfirmCode();

        void onGenerateNewCode();
    }
}
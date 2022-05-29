package com.example.cotizaciondolar;

public interface LoginActivityContract {

    interface View {
        void setText(String text);
    }

    interface Model {

        interface OnEventListener {
            void onEvent(String string);
        }
        void getNextName(LoginActivityContract.Model.OnEventListener listener);
    }

    interface Presenter {
    }
}
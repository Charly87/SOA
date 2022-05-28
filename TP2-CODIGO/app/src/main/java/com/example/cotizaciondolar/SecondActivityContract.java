package com.example.cotizaciondolar;

public interface SecondActivityContract {

    interface View {
        void setText(String text);
    }

    interface Model {

        interface OnEventListener {
            void onEvent(String string);
        }
        void getNextName(SecondActivityContract.Model.OnEventListener listener);
    }

    interface Presenter {
    }
}
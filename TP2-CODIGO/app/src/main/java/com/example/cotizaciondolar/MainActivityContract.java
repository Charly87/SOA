package com.example.cotizaciondolar;

import android.content.Intent;
import android.text.Editable;

public interface MainActivityContract {

    interface View {
        String getText();
    }

    interface Model {

        interface OnEventListener {
            void onEvent(String string);
        }
        void getNextName(MainActivityContract.Model.OnEventListener listener);
    }

    interface Presenter {
        void onButtonClick();
        void onDestroy();
    }
}
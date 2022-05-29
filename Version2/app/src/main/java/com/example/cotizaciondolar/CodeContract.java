package com.example.cotizaciondolar;

import android.content.Intent;
import android.text.Editable;

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
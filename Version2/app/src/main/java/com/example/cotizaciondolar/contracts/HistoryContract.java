package com.example.cotizaciondolar.contracts;

import android.hardware.SensorEvent;

import com.example.cotizaciondolar.models.entities.QuotationResponse;

public interface HistoryContract {
    interface View {

    }

    interface Model {

        interface OnFinishedListener {
            //void onFinished(QuotationResponse quotationResponse);

            void onFailure(Throwable t);
        }


    }

    interface Presenter {

    }
}

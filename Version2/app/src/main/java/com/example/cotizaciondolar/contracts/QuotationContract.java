package com.example.cotizaciondolar.contracts;

import android.hardware.SensorEvent;

import com.example.cotizaciondolar.models.QuotationResponse;

public interface QuotationContract {
    interface View {
        void setDateText(String date);

        void setPurchaseText(String purchase);

        void setSaleText(String sale);

        int getCheckedButton();

        void setCheckedButton(int buttonId);

        void showLongToast(String message);
    }

    interface Model {

        interface OnFinishedListener {
            void onFinished(QuotationResponse quotationResponse);

            void onFailure(Throwable t);
        }

        void getDollarQuotation(OnFinishedListener onFinishedListener, int checkedId);
    }

    interface Presenter {
        void requestDataFromServer(int checkedId, boolean isChecked);

        void onSensorChanged(SensorEvent event);
    }
}

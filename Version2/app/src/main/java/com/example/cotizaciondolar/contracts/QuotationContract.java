package com.example.cotizaciondolar.contracts;

import android.hardware.SensorEvent;

public interface QuotationContract {
    interface View {
        void setDateText(String date);

        void setPurchaseText(String purchase);

        void setSaleText(String sale);

        int getCheckedButton();

        void setCheckedButton(int buttonId);
    }

    interface Model {
        String getDate();

        String getPurchasePrice();

        String getSalePrice();
    }

    interface Presenter {
        void getDollarQuotation(int checkedId);

        void onSensorChanged(SensorEvent event);
    }
}

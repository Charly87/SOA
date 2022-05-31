package com.example.cotizaciondolar;

public interface QuotationContract {
    interface View {
        void setDateText(String date);

        void setPurchaseText(String purchase);

        void setSaleText(String sale);
    }

    interface Model {
        String getDate();

        String getBuy();

        String getSell();
    }

    interface Presenter {
        void getDollarQuotation(int checkedId);
    }
}

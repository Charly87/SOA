package com.example.cotizaciondolar;

public interface QuotationContract {
    interface View {
        void setQuotation(String quotation);
    }

    interface Model {
        String getDate();

        String getBuy();

        String getSell();
    }

    interface Presenter {
        void getDollarBlueQuotation();
    }
}

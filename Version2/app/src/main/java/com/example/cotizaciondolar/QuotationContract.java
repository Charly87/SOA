package com.example.cotizaciondolar;

public interface QuotationContract {
    interface View {
        void setQuotation(String quotation);
    }

    interface Model {
        String getDate();

        String getBuyPrice();

        String getSellPrice();
    }

    interface Presenter {
        void getDollarBlueQuotation();
    }
}

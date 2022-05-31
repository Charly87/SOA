package com.example.cotizaciondolar.contracts;

public interface QuotationContract {
    interface View {
        void setDateText(String date);

        void setPurchaseText(String purchase);

        void setSaleText(String sale);
    }

    interface Model {
        String getDate();

        String getPurchasePrice();

        String getSalePrice();
    }

    interface Presenter {
        void getDollarQuotation(int checkedId);
    }
}

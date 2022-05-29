package com.example.cotizaciondolar.ui.models;

import com.example.cotizaciondolar.QuotationContract;

public class QuotationModel implements QuotationContract.Model {
    private final String date;
    private final String buyPrice;
    private final String sellPrice;

    public QuotationModel(String date, String buyPrice, String sellPrice) {
        this.date = date;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public String getDate() {
        return date;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }
}

package com.example.cotizaciondolar.ui.models;

import com.example.cotizaciondolar.QuotationContract;
import com.google.gson.annotations.SerializedName;

public class QuotationModel implements QuotationContract.Model {
    @SerializedName("fecha")
    private final String date;
    @SerializedName("compra")
    private final String buy;
    @SerializedName("venta")
    private final String sell;

    public QuotationModel(String date, String buy, String sell) {
        this.date = date;
        this.buy = buy;
        this.sell = sell;
    }

    public String getDate() {
        return date;
    }

    public String getBuy() {
        return buy;
    }

    public String getSell() {
        return sell;
    }
}

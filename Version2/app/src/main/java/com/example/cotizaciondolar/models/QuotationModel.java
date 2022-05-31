package com.example.cotizaciondolar.models;

import com.example.cotizaciondolar.contracts.QuotationContract;
import com.google.gson.annotations.SerializedName;

public class QuotationModel implements QuotationContract.Model {
    @SerializedName("fecha")
    private final String date;
    @SerializedName("compra")
    private final String purchasePrice;
    @SerializedName("venta")
    private final String salePrice;

    public QuotationModel(String date, String purchasePrice, String salePrice) {
        this.date = date;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
    }

    public String getDate() {
        return date;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public String getSalePrice() {
        return salePrice;
    }
}

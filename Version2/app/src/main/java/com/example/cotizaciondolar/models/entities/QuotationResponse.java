package com.example.cotizaciondolar.models.entities;

import com.google.gson.annotations.SerializedName;

public class QuotationResponse {
    @SerializedName("fecha")
    private final String date;
    @SerializedName("compra")
    private final String purchasePrice;
    @SerializedName("venta")
    private final String salePrice;

    public QuotationResponse(String date, String purchasePrice, String salePrice) {
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

package com.example.cotizaciondolar.models;


import android.content.Context;
import android.util.Log;

import com.example.cotizaciondolar.contracts.CodeContract;

import java.util.Random;

public class CodeModel implements CodeContract.Model {
    private static final String TAG = "CodeModel";
    private String activeCode;

    public CodeModel(Context context) {
        this.generateNewCode();
    }

    @Override
    public String getActiveCode() {
        return activeCode;
    }

    @Override
    public String generateNewCode() {
        Random random = new Random();
        int code = random.nextInt(10000);
        this.activeCode = String.format("%04d", code);

        Log.d(TAG, "Codigo de doble factor SMS generado: " + activeCode);

        return this.activeCode;
    }
}

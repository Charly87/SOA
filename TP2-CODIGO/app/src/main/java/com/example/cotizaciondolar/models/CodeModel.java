package com.example.cotizaciondolar.models;

import com.example.cotizaciondolar.CodeContract;

import java.util.Random;

public class CodeModel implements CodeContract.Model {

    private String activeCode;

    public CodeModel()
    {
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
        this.activeCode = String.format("%04d", code) ;
        return this.activeCode;
    }
}

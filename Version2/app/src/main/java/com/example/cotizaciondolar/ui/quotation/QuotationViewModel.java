package com.example.cotizaciondolar.ui.quotation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuotationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public QuotationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is quotation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
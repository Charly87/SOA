package com.example.cotizaciondolar.presenters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cotizaciondolar.MainActivityContract;
import com.example.cotizaciondolar.SecondActivity;
import com.example.cotizaciondolar.SecondActivityContract;

public class SecondPresenter implements SecondActivityContract.Presenter {

    private SecondActivityContract.View view;
    private SecondActivityContract.Model model;

    public SecondPresenter(SecondActivityContract.View mainView, SecondActivityContract.Model model){
        this.view = mainView;
        this.model = model;

        this.Initialize();
    }

    private void Initialize()
    {
        Intent intent=((Activity) this.view).getIntent();
        Bundle extras=intent.getExtras();
        String texto= extras.getString("text");
        this.view.setText(texto);
    }
}

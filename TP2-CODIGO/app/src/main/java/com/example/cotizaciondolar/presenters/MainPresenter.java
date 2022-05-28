package com.example.cotizaciondolar.presenters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.cotizaciondolar.MainActivityContract;
import com.example.cotizaciondolar.SecondActivity;

public class MainPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mainView;
    private MainActivityContract.Model model;

    public MainPresenter(MainActivityContract.View mainView, MainActivityContract.Model model){
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void onButtonClick() {

        Intent intent=new Intent((Activity) this.mainView, SecondActivity.class);
        intent.putExtra("text",this.mainView.getText());
        ((Activity) this.mainView).startActivity(intent);
    }

    @Override
    public void onDestroy() {

    }
}

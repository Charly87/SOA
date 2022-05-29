package com.example.cotizaciondolar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cotizaciondolar.models.LoginModel;
import com.example.cotizaciondolar.presenters.LoginPresenter;

public class LoginActivity extends Activity implements LoginActivityContract.View {

    // Defino mis objetos a utilizar
    TextView textView;

    // Defino mi presenter
    LoginActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Realizo el binding de los objetos del XML a mis objetos en el Activity
        textView = this.findViewById(R.id.textView);

        presenter = new LoginPresenter(this, new LoginModel());
    }

    @Override
    public void setText(String text) {
        textView.setText(text);
    }

    @Override
    public void onBackPressed() {
//        if (shouldAllowBack()) {
//            super.onBackPressed();
//        } else {
//            doSomething();
//        }
    }
}
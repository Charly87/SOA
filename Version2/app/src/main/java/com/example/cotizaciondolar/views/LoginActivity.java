package com.example.cotizaciondolar.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.contracts.LoginActivityContract;
import com.example.cotizaciondolar.models.LoginModel;
import com.example.cotizaciondolar.presenters.LoginPresenter;

public class LoginActivity extends Activity implements LoginActivityContract.View {

    // lo cree como prueba solo para poder pasar al menu principal
    Button btnConfirm;

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

        // lo cree como prueba solo para poder pasar al menu principal
        btnConfirm = this.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                ((Activity) LoginActivity.this).startActivity(intent);
            }
        });

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
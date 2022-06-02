package com.example.cotizaciondolar.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.contracts.LoginActivityContract;
import com.example.cotizaciondolar.models.LoginModel;
import com.example.cotizaciondolar.presenters.LoginPresenter;

public class LoginActivity extends Activity implements LoginActivityContract.View {

    // Defino mis objetos a utilizar
    EditText userEditText;
    EditText passEditText;
    Button btnConfirm;
    Button btnRegis;

    // Defino mi presenter
    LoginActivityContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Realizo el binding de los objetos del XML a mis objetos en el Activity
        userEditText = this.findViewById(R.id.userEditText);
        passEditText = this.findViewById(R.id.passEditText);
        btnConfirm = this.findViewById(R.id.btnConfirm);
        btnRegis = this.findViewById(R.id.btnRegis);

        presenter = new LoginPresenter(this);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { presenter.Regis(); }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               presenter.Login();
            }
        });

    }

    @Override
    public void setMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUsername() {
        return userEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passEditText.getText().toString();
    }

    @Override
    public void onBackPressed() {
        // Deshabilitamos que no pueda volver a la pantalla anterior
    }
}
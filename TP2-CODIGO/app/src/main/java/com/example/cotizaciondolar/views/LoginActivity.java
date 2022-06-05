package com.example.cotizaciondolar.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.contracts.LoginContract;
import com.example.cotizaciondolar.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    // Defino mis objetos a utilizar
    EditText userEditText;
    EditText passEditText;
    Button loginButton;
    Button signUpButton;

    // Defino mi presenter
    LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Iniciar sesión");

        // Realizo el binding de los objetos del XML a mis objetos en el Activity
        userEditText = this.findViewById(R.id.userEditText);
        passEditText = this.findViewById(R.id.passEditText);
        loginButton = this.findViewById(R.id.btnConfirm);
        signUpButton = this.findViewById(R.id.btnRegis);

        presenter = new LoginPresenter(this);

        setListeners();
    }

    private void setListeners() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSignUpButtonClicked();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLoginButtonClicked();
            }
        });
    }

    @Override
    public void showLongToast(String msg) {
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

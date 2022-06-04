package com.example.cotizaciondolar.views;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.contracts.SignUpContract;
import com.example.cotizaciondolar.presenters.SignUpPresenter;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    // Defino mis objetos a utilizar
    TextInputEditText nameEditText;
    TextInputEditText lastNameEditText;
    TextInputEditText dniEditText;
    TextInputEditText emailEditText;
    TextInputEditText passwordEditText;
    TextInputEditText comissionEditText;
    TextInputEditText groupEditText;

    Button cancelButton;
    Button confirmButton;

    // Defino mi presenter
    SignUpContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle("Registrar usuario");

        // Realizo el binding de los objetos del XML a mis objetos en el Activity
        nameEditText = this.findViewById(R.id.nameEditText);
        lastNameEditText = this.findViewById(R.id.lastNameEditText);
        dniEditText = this.findViewById(R.id.dniEditText);
        emailEditText = this.findViewById(R.id.emailEditText);
        passwordEditText = this.findViewById(R.id.passwordEditText);
        comissionEditText = this.findViewById(R.id.comissionEditText);
        groupEditText = this.findViewById(R.id.groupEditText);
        cancelButton = this.findViewById(R.id.cancelButton);
        confirmButton = this.findViewById(R.id.confirmButton);

        presenter = new SignUpPresenter(this, getApplicationContext());

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.onConfirmButtonClick();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCancelButtonClick();
            }
        });
    }

    @Override
    public void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // TODO: validar todos estos campos

    @Override
    public Editable getName() {
        return nameEditText.getText();
    }

    @Override
    public Editable getLastName() {
        return lastNameEditText.getText();
    }

    @Override
    public Editable getDni() {
        return dniEditText.getText();
    }

    @Override
    public Editable getEmail() {
        return emailEditText.getText();
    }

    @Override
    public Editable getPassword() {
        return passwordEditText.getText();
    }

    @Override
    public Editable getCommission() {
        return comissionEditText.getText();
    }

    @Override
    public Editable getGroup() {
        return groupEditText.getText();
    }


}

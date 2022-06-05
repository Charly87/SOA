package com.example.cotizaciondolar.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.contracts.CodeContract;
import com.example.cotizaciondolar.presenters.CodePresenter;

public class CodeActivity extends AppCompatActivity implements CodeContract.View {

    // Defino mis objetos a utilizar
    Button btnConfirm;
    Button btnGenerate;
    EditText codeEditText;

    // Defino mi presenter
    CodeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        getSupportActionBar().setTitle("Código de doble factor");

        // Realizo el binding de los objetos del XML a mis objetos en el Activity
        btnConfirm = this.findViewById(R.id.btnConfirm);
        btnGenerate = this.findViewById(R.id.btnGenerate);
        codeEditText = this.findViewById(R.id.txtCode);

        presenter = new CodePresenter(this, getApplicationContext());
        presenter.onGenerateNewCode();

        setListeners();
    }

    private void setListeners() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onConfirmCode();
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onGenerateNewCode();
            }
        });
    }


    @Override
    public String getCode() {
        return codeEditText.getText().toString();
    }

    @Override
    public void cleanError() {
        codeEditText.setError(null);
    }

    @Override
    public void setError(String error) {
        codeEditText.setError(error);
    }
}

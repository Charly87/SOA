package com.example.cotizaciondolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cotizaciondolar.models.MainModel;
import com.example.cotizaciondolar.presenters.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    // Defino mis objetos a utilizar
    Button btnOk;
    EditText txtName;

    // Defino mi presenter
    MainActivityContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Realizo el binding de los objetos del XML a mis objetos en el Activity
        btnOk = this.findViewById(R.id.btnOk);
        txtName = this.findViewById(R.id.txtName);

        // Instancio mi presentador pasandole este activity y el model
        presenter = new MainPresenter(this, new MainModel());

        // Suscribo un callback del Presenter cuando se realice el evento OnClick del botón
        btnOk.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 presenter.onButtonClick();
             }
         }
        );
    }

    @Override
    public String getText() {
        return txtName.getText().toString();
    }
}
package com.example.cotizaciondolar.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        // Cons esta línea la primera vez la app solicita permisos para leer SMS,
        // enviar SMS, acceder al numero, etc
 

        getSupportActionBar().setTitle("Código de doble factor");

        // Realizo el binding de los objetos del XML a mis objetos en el Activity
        btnConfirm = this.findViewById(R.id.btnConfirm);
        btnGenerate = this.findViewById(R.id.btnGenerate);
        codeEditText = this.findViewById(R.id.txtCode);

        // Instancio mi presentador pasandole este activity y el model
        presenter = new CodePresenter(this, getApplicationContext());
        presenter.onGenerateNewCode();

        btnConfirm.setOnClickListener(buttonListeners);
        btnGenerate.setOnClickListener(buttonListeners);
    }

    private View.OnClickListener buttonListeners = new View.OnClickListener() {
        public void onClick(View v) {
            //Se determina que componente genero un evento
            switch (v.getId()) {
                //Si se ocurrio un evento en el boton Confirmar
                case R.id.btnConfirm:
                    presenter.onConfirmCode();
                    break;
                //Si se ocurrio un evento en el boton Generar
                case R.id.btnGenerate:
                    presenter.onGenerateNewCode();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
            }
        }
    };

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

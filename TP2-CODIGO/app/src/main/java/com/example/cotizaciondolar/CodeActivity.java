package com.example.cotizaciondolar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotizaciondolar.models.CodeModel;
import com.example.cotizaciondolar.presenters.CodePresenter;

public class CodeActivity extends AppCompatActivity implements CodeContract.View {

    // Defino mis objetos a utilizar
    Button btnConfirm;
    Button btnGenerate;
    EditText txtCode;
    TextView lblMessage;

    // Defino mi presenter
    CodeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        // Cons esta línea la primera vez la app solicita permisos para leer SMS, enviar SMS, acceder al numero, etc
        ActivityCompat.requestPermissions(CodeActivity.this,   new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS},PackageManager.PERMISSION_GRANTED);

        // Realizo el binding de los objetos del XML a mis objetos en el Activity
        btnConfirm = this.findViewById(R.id.btnConfirm);
        btnGenerate = this.findViewById(R.id.btnGenerate);
        txtCode = this.findViewById(R.id.txtCode);
        lblMessage = this.findViewById(R.id.lblMessage);

        // Instancio mi presentador pasandole este activity y el model
        presenter = new CodePresenter(this, new CodeModel());
        presenter.onGenerateNewCode();

        btnConfirm.setOnClickListener(buttonListeners);
        btnGenerate.setOnClickListener(buttonListeners);
    }

    private View.OnClickListener buttonListeners = new View.OnClickListener() {
        public void onClick(View v)
        {
            //Se determina que componente genero un evento
            switch (v.getId())
            {
                //Si se ocurrio un evento en el boton Confirmar
                case R.id.btnConfirm:
                    presenter.onConfirmCode();
                    break;
                //Si se ocurrio un evento en el boton Generar
                case R.id.btnGenerate:
                    presenter.onGenerateNewCode();
                    break;
                default:
                    Toast.makeText(getApplicationContext(),"Error en Listener de botones",Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public String getCode() {
        return txtCode.getText().toString();
    }

    @Override
    public void cleanError() {
        this.lblMessage.setVisibility(View.INVISIBLE);
        this.lblMessage.setText("");
    }

    @Override
    public void setError(String error) {
        this.lblMessage.setText(error);
        this.lblMessage.setVisibility(View.VISIBLE);
    }
}
package com.example.cotizaciondolar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cotizaciondolar.models.MainModel;
import com.example.cotizaciondolar.presenters.MainPresenter;

import java.util.concurrent.ExecutionException;

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

        // Cons esta línea la primera vez la app solicita permisos para leer SMS, enviar SMS, acceder al numero, etc
        ActivityCompat.requestPermissions(MainActivity.this,   new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS},PackageManager.PERMISSION_GRANTED);

        // Realizo el binding de los objetos del XML a mis objetos en el Activity
        btnOk = this.findViewById(R.id.btnOk);
        txtName = this.findViewById(R.id.txtName);

        // Instancio mi presentador pasandole este activity y el model
        presenter = new MainPresenter(this, new MainModel());

        // Suscribo un callback del Presenter cuando se realice el evento OnClick del botón
        btnOk.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //presenter.onButtonClick(); //Por ahora se comentó
                 SendSMS();
             }
         }
        );
    }

    private void SendSMS()
    {
        TelephonyManager tMgr = (TelephonyManager)MainActivity.this.getSystemService(MainActivity.this.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String mPhoneNumber = tMgr.getLine1Number();

        try {
            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(mPhoneNumber,null,getText(),null,null);
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this,
                    ex.toString(), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(MainActivity.this,
                "Mensaje enviado a: " + mPhoneNumber, Toast.LENGTH_SHORT).show();
    }
    @Override
    public String getText() {
        return txtName.getText().toString();
    }
}
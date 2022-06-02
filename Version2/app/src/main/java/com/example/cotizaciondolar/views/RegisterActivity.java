package com.example.cotizaciondolar.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.contracts.LoginActivityContract;
import com.example.cotizaciondolar.contracts.RegisterActivityContract;
import com.example.cotizaciondolar.presenters.LoginPresenter;
import com.example.cotizaciondolar.presenters.RegisterPresenter;

public class RegisterActivity extends Activity implements RegisterActivityContract.View {

    // Defino mis objetos a utilizar
    EditText txtUsr;
    EditText txtLast;
    EditText txtDni;
    EditText txtMail;
    EditText txtConfirm;
    EditText txtCom;
    EditText txtGrp;

    Button btnCancel;
    Button btnRegister;

    // Defino mi presenter
    RegisterActivityContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Realizo el binding de los objetos del XML a mis objetos en el Activity
        txtUsr = this.findViewById(R.id.txtUsr);
        txtLast = this.findViewById(R.id.txtLast);
        txtDni = this.findViewById(R.id.txtDni);
        txtMail = this.findViewById(R.id.txtMail);
        txtConfirm = this.findViewById(R.id.txtConfirm);
        txtCom = this.findViewById(R.id.txtCom);
        txtGrp = this.findViewById(R.id.txtGrp);
        btnCancel = this.findViewById(R.id.btnCancel);
        btnRegister = this.findViewById(R.id.btnRegister);

        presenter = new RegisterPresenter(this);

        btnRegister.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.Register();
            }
        });

    }

    @Override
    public void setMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    public String getName() {
        return txtUsr.getText().toString();
    }
    @Override
    public String getLastName() {
        return txtLast.getText().toString();
    }
    @Override
    public String getDni() { return txtDni.getText().toString(); }// ver si funca o hay que pasar a integer
    @Override
    public String getEmail() {
        return txtMail.getText().toString();
    }
    @Override
    public String getPassword() {
        return txtConfirm.getText().toString();
    }
    @Override
    public String getCommission() {
        return txtCom.getText().toString();
    }
    @Override
    public String getGroup() {
        return txtGrp.getText().toString();
    }


}


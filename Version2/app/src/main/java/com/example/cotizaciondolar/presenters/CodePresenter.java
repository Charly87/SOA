package com.example.cotizaciondolar.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

import com.example.cotizaciondolar.contracts.CodeContract;
import com.example.cotizaciondolar.models.CodeModel;
import com.example.cotizaciondolar.views.LoginActivity;

public class CodePresenter implements CodeContract.Presenter {

    private final CodeContract.View mainView;
    private final CodeContract.Model model;
    private Context context;

    public CodePresenter(CodeContract.View mainView, Context context) {
        this.context = context;
        this.mainView = mainView;
        this.model = new CodeModel(context);
    }

    private void sendCode() {
        TelephonyManager telephonyManager = (TelephonyManager) ((Activity) this.mainView)
                .getSystemService(Context.TELEPHONY_SERVICE);

        // Cons esta línea la primera vez la app solicita permisos para leer SMS,
        // enviar SMS, acceder al numero, etc
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) !=
                        PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) !=
                        PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    (Activity) mainView,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_SMS,
                            Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.SEND_SMS},
                    PackageManager.PERMISSION_GRANTED);
            return;
        }
        String mPhoneNumber = telephonyManager.getLine1Number();

        SmsManager manager = SmsManager.getDefault();

        manager.sendTextMessage(
                mPhoneNumber,
                null,
                "Su código de acceso es " + this.model.getActiveCode(),
                null,
                null
        );
    }

    @Override
    public void onConfirmCode() {
        if (this.mainView.getCode().equals(this.model.getActiveCode())) {
            this.mainView.cleanError();
            Intent intent = new Intent((Activity) this.mainView, LoginActivity.class);
            intent.putExtra("text", this.mainView.getCode());
            ((Activity) this.mainView).startActivity(intent);
        } else {
            this.mainView.setError("Código incorrecto");
        }
    }

    @Override
    public void onGenerateNewCode() {
        this.mainView.cleanError();
        this.model.generateNewCode();
        this.sendCode();
    }
}

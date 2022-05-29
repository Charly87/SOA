package com.example.cotizaciondolar.ui.presenters;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.example.cotizaciondolar.CodeContract;
import com.example.cotizaciondolar.LoginActivity;

public class CodePresenter implements CodeContract.Presenter {

    private CodeContract.View mainView;
    private CodeContract.Model model;

    public CodePresenter(CodeContract.View mainView, CodeContract.Model model){
        this.mainView = mainView;
        this.model = model;
    }

    private void SendCode()
    {
        TelephonyManager tMgr = (TelephonyManager) ((Activity)this.mainView).getSystemService(((Activity)this.mainView).TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String mPhoneNumber = tMgr.getLine1Number();

        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(mPhoneNumber,null,"Su código de acceso es " + this.model.getActiveCode(),null,null);

    }
    @Override
    public void onConfirmCode() {

        if(this.mainView.getCode().equals(this.model.getActiveCode())) {
            this.mainView.cleanError();
            Intent intent = new Intent((Activity) this.mainView, LoginActivity.class);
            intent.putExtra("text", this.mainView.getCode());
            ((Activity) this.mainView).startActivity(intent);
        }
        else
        {
            this.mainView.setError("Código Incorrecto");
        }

    }

    @Override
    public void onGenerateNewCode() {
        this.mainView.cleanError();
        this.model.generateNewCode();
        this.SendCode();
    }
}

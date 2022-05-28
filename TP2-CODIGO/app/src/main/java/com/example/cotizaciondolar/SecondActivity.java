package com.example.cotizaciondolar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cotizaciondolar.models.SecondModel;
import com.example.cotizaciondolar.presenters.SecondPresenter;

public class SecondActivity extends Activity implements SecondActivityContract.View {

    // Defino mis objetos a utilizar
    TextView textView;

    // Defino mi presenter
    SecondActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Realizo el binding de los objetos del XML a mis objetos en el Activity
        textView = this.findViewById(R.id.textView);

        presenter = new SecondPresenter(this, new SecondModel());
    }

    @Override
    public void setText(String text) {
        textView.setText(text);
    }
}
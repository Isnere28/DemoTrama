package com.example.demolecturatarjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MensajeActivity extends Activity implements View.OnClickListener{

    private Button button;
    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        button = findViewById(R.id.button);
        texto = findViewById(R.id.textView);
        String s = getIntent().getExtras().getString("Mensaje");
        texto.setText(s);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                startActivity( new Intent(this, LecturaTarjetaActivity.class));
                break;
        }
    }
}
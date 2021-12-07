package com.example.demolecturatarjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demolecturatarjeta.api.SmartPosApplication;
import com.example.demolecturatarjeta.iso.TramasISO8583;
import com.example.demolecturatarjeta.post.EnviarTrama;

public class InformacionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView informacionTextView;
    private Button enviarTramaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        informacionTextView = findViewById(R.id.informacionTextView);
        enviarTramaButton = findViewById(R.id.enviartramaButton);

        String cardNo = SmartPosApplication.getApp().mConsumeData.getCardno();
        informacionTextView.setText(SmartPosApplication.getApp().mConsumeData.getCardno());

        enviarTramaButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.enviartramaButton:
                TramasISO8583 tramasISO8583 = new TramasISO8583();
                Bundle camposRequeridos = tramasISO8583.obtenerCamposRequeridosPrueba();
                Bundle informacionCampos = tramasISO8583.obtenerInformacionCampos(camposRequeridos,null,"Chip","Venta","Credito","Credito");
                byte[] tramaCreada = tramasISO8583.crearTrama(informacionCampos);
                EnviarTrama enviarTrama = new EnviarTrama(this,"Venta","Credito",tramaCreada);
                enviarTrama.start();
                break;
        }
    }
}
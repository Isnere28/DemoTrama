package com.example.demolecturatarjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.demolecturatarjeta.api.SmartPosApplication;

public class InformacionActivity extends AppCompatActivity {

    private TextView informacionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        informacionTextView = findViewById(R.id.informacionTextView);

        informacionTextView.setText(SmartPosApplication.getApp().mConsumeData.getCardno());
    }
}
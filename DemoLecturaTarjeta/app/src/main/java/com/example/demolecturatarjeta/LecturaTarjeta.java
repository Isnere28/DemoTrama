package com.example.demolecturatarjeta;

import android.os.Bundle;

import com.example.demolecturatarjeta.api.DeviceService;
import com.example.demolecturatarjeta.card.CardManager;
import com.topwise.cloudpos.aidl.AidlDeviceService;

public class LecturaTarjeta extends DeviceService {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura_tarjeta);

    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        CardManager.getInstance().startCardDealService(this);
        String s = "";
    }
}
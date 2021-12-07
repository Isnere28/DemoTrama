package com.example.demolecturatarjeta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.demolecturatarjeta.api.DeviceService;
import com.example.demolecturatarjeta.card.CardManager;
import com.topwise.cloudpos.aidl.AidlDeviceService;

public class LecturaTarjetaActivity extends DeviceService {

    private static final String TAG = "Prueba - Lectura de tarjeta - Clase: LecturaTarjetaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura_tarjeta);
    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        CardManager.getInstance().startCardDealService(this);
        //String s = "";

        CardManager.getInstance().initCardExceptionCallBack(exceptionCallBack);
    }

    private final CardManager.CardExceptionCallBack exceptionCallBack = new CardManager.CardExceptionCallBack() {
        @Override
        public void callBackTimeOut() {
            Intent intent = new Intent(getApplicationContext(), MensajeActivity.class);
            intent.putExtra("Mensaje", "Se cumplio el tiempo de lectura");
            startActivity(intent);
        }

        @SuppressLint("LongLogTag")
        @Override
        public void callBackError(int errorCode) {
            Log.d(TAG, "callBackError errorCode : " + errorCode);
            //mHandle.sendEmptyMessage(errorCode);
            if (MainActivity.intentoLectura == 3){
                MainActivity.intentoLectura = 0;
                MainActivity.tipodelectura = "Call back";
                Intent intent = new Intent(getApplicationContext(), MensajeActivity.class);
                intent.putExtra("Mensaje", "Se cumplieron los 3 intentos");
                startActivity(intent);
            } else {
                MainActivity.intentoLectura+=1;
                MainActivity.tipodelectura = "chip";
                Intent intent = new Intent(getApplicationContext(), MensajeActivity.class);
                intent.putExtra("Mensaje", "Intente de nuevo");
                startActivity(intent);

            }
        }

        @Override
        public void callBackCanceled() {

        }

        @Override
        public void callBackTransResult(int result) {
            String resultDetail = null;
            if (result == CardSearchErrorUtil.TRANS_REASON_REJECT) {
                resultDetail = "Transacción Rechazada";
            } else if (result == CardSearchErrorUtil.TRANS_REASON_STOP) {
                resultDetail = "Transacción Detenida";
            } else if (result == CardSearchErrorUtil.TRANS_REASON_FALLBACK) {
                resultDetail = "Transacción FallBack";
            } else if (result == CardSearchErrorUtil.TRANS_REASON_OTHER_UI) {
                resultDetail = "Por Favor use otras opciones";
            } else if (result == CardSearchErrorUtil.TRANS_REASON_STOP_OTHERS) {
                resultDetail = "Otros";
            }
        }

        @Override
        public void finishPreActivity() {

        }
    };
}
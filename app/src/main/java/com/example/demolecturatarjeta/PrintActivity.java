package com.example.demolecturatarjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.demolecturatarjeta.api.DeviceService;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.printer.AidlPrinter;
import com.topwise.cloudpos.aidl.printer.AidlPrinterListener;
import com.topwise.cloudpos.aidl.printer.Align;
import com.topwise.cloudpos.aidl.printer.ImageUnit;
import com.topwise.cloudpos.aidl.printer.PrintTemplate;
import com.topwise.cloudpos.aidl.printer.TextUnit;

public class PrintActivity extends DeviceService implements View.OnClickListener {

    private Button buttonText;
    private Button buttonImage;
    private ImageView imagen;
    public static AidlPrinter printerDev;
    private static final String TAG = "Prueba - PrintActivity - Clase: PrintActivity";
    private String lineasdeimpresion;
    private String[] cadenadeimpresion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        buttonText = findViewById(R.id.button2);
        buttonText.setOnClickListener(this);
        buttonImage = findViewById(R.id.button3);
        buttonImage.setOnClickListener(this);
        imagen = (ImageView) findViewById(R.id.imageView);
        imagen.setImageResource(R.drawable.print_image);
        lineasdeimpresion = "Esta es su factura" + "\n" + "Prueba1" + "\n" + "Prueba2" + "\n" + "Prueba3";
        cadenadeimpresion = new String[]{};
    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        try {
            printerDev = AidlPrinter.Stub.asInterface(serviceManager.getPrinter());
        } catch (Exception E){

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                try {
                    PrintTemplate.getInstance().init(getApplicationContext(), null);
                    PrintTemplate template = PrintTemplate.getInstance();
                    imagen.buildDrawingCache();
                    Bitmap Bitmap = imagen.getDrawingCache();
                    template.clear();//Para limpiar la hoja
                    cadenadeimpresion = lineasdeimpresion.split("\n");
                    for (int itemObjetIterator = 0; itemObjetIterator < cadenadeimpresion.length; itemObjetIterator++) {
                        if (itemObjetIterator == 0) {
                            template.add(new TextUnit(cadenadeimpresion[itemObjetIterator], 24, Align.CENTER).setBold(true));
                        }
                        if (itemObjetIterator == 1) {
                            template.add(1, new TextUnit(cadenadeimpresion[1], 21, Align.CENTER).setBold(false).setLineSpacing(20).setBold(false),
                                    1, new TextUnit(cadenadeimpresion[2], 21, Align.CENTER).setBold(false).setLineSpacing(20).setBold(false),
                                    1, new TextUnit(cadenadeimpresion[3], 21, Align.CENTER).setBold(false).setLineSpacing(20).setBold(false));
                        }
                    }

                    printerDev.addRuiImage(template.getPrintBitmap(), 0);
                    printerDev.printRuiQueue(mlistener);
                } catch (Exception E){

                }

                break;
            case R.id.button3:
                try {
                    PrintTemplate.getInstance().init(getApplicationContext(), null);
                    PrintTemplate template = PrintTemplate.getInstance();
                    imagen.buildDrawingCache();
                    Bitmap Bitmap = imagen.getDrawingCache();
                    template.clear();//Para limpiar la hoja
                    template.add(new ImageUnit(Bitmap, 180,180));
                    printerDev.addRuiImage(template.getPrintBitmap(), 0);
                    printerDev.printRuiQueue(mlistener);
                } catch (Exception E){

                }

                break;
        }

    }
    AidlPrinterListener mlistener = new AidlPrinterListener.Stub() {
        @SuppressLint("LongLogTag")
        @Override
        public void onError(int i) throws RemoteException {
            Log.d(TAG, "Impresión Fallida");
        }

        @SuppressLint("LongLogTag")
        @Override
        public void onPrintFinish() throws RemoteException {
            Log.d(TAG, "Impresión Terminada");
        }
    };


}
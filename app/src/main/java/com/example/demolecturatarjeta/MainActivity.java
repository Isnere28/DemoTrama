package com.example.demolecturatarjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.demolecturatarjeta.database.BaseDeDatos;
import com.example.demolecturatarjeta.database.DBPerfilConfiguracion;
import com.example.demolecturatarjeta.database.object.PerfilConfiguracion;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Creando base de datos y perfil configuracion.
    public static BaseDeDatos baseDeDatos;
    public static DBPerfilConfiguracion dbPerfilConfiguracion;

    private Button button_lectura;
    private Button button_imprimir;
    public static int intentoLectura;
    public static String tipodelectura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_lectura = findViewById(R.id.button_lectura);
        button_imprimir = findViewById(R.id.button_imprimir);

        baseDeDatos  = new BaseDeDatos(getApplicationContext());
        dbPerfilConfiguracion = new DBPerfilConfiguracion(baseDeDatos);

        if (dbPerfilConfiguracion.leer() == 0){
            dbPerfilConfiguracion.agregarPerfilConfiguracion(new PerfilConfiguracion("1",
                    "1234",
                    "BANCARIBE",
                    "logo.jpg",
                    "441132",
                    "J",
                    "J-000029490",
                    "Compumundo Hipermega Red",
                    "Carrera 100 No. 13 - 25",
                    "0010000017",
                    "FP35000001",
                    "J",
                    "J-12345678-9",
                    "200.74.230.29",
                    "4001",
                    "00001009",
                    "Bs.",
                    "+57",
                    "",
                    "0",
                    "0",
                    "600",
                    "600"));
        }

        button_lectura.setOnClickListener(this);
        button_imprimir.setOnClickListener(this);
        tipodelectura = "chip";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_lectura:
                //Toast.makeText(this,"Abrir pantalla de lectura", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, LecturaTarjetaActivity.class));
                break;
            case R.id.button_imprimir:
                startActivity(new Intent(this, PrintActivity.class));
                break;
        }
    }
}
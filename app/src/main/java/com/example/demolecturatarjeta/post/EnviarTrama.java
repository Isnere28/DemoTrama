package com.example.demolecturatarjeta.post;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;

import com.example.demolecturatarjeta.MainActivity;
import com.example.demolecturatarjeta.MensajeActivity;
import com.example.demolecturatarjeta.database.DBPerfilConfiguracion;
import com.example.demolecturatarjeta.database.DBRespuestaISO;
import com.example.demolecturatarjeta.database.DBTransaccion;
import com.example.demolecturatarjeta.iso.PlantillaCredicardCreditoLlegada16;
import com.example.demolecturatarjeta.iso.PlantillaCredicardCreditoLlegada19;
import com.example.demolecturatarjeta.iso.PlantillaCredicardDebitoLlegada16;
import com.example.demolecturatarjeta.iso.PlantillaCredicardDebitoLlegada19;

import org.jpos.iso.ISOMsg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EnviarTrama extends Thread {

    private static final String TAG = "Prueba - Envio de tramas - Clase: EnvioDeTramaEcoTest";

    private static final int ENVIADA_AL_HOST = 0;
    private static final int APROBADA_SIN_ADVICE = 1;
    private static final int APROBADA_CON_ADVICE = 4;
    private static final int REVERSADA = 5;
    private static final int ANULADA = 2;
    private static final int NEGADA_Y_OTRAS = 3;

    private static final int DURACION_INTERVALO = 1000;
    private static final int NUMERO_INTERVALOS = 15;

    private Context context;
    private Socket sk;
    private byte[] tramaSalida;

    private String tipoTransaccion;
    private String tipoTarjeta;

    private DBPerfilConfiguracion dbPerfilConfiguracion;
    private DBRespuestaISO dbRespuestaISO;
    private DBTransaccion dbTransaccion;

    private PlantillaCredicardCreditoLlegada16 packagerCreditoLlegada16;
    private PlantillaCredicardCreditoLlegada19 packagerCreditoLlegada19;
    private PlantillaCredicardDebitoLlegada16 packagerDebitoLlegada16;
    private PlantillaCredicardDebitoLlegada19 packagerDebitoLlegada19;

    public EnviarTrama(Context context, String tipoTransaccion, String tipoTarjeta, byte[] tramaSalida){
        this.tramaSalida = tramaSalida;
        this.context = context;
        this.tipoTransaccion = tipoTransaccion;
        this.tipoTarjeta = tipoTarjeta;
        packagerCreditoLlegada16 = new PlantillaCredicardCreditoLlegada16();
        packagerCreditoLlegada19 = new PlantillaCredicardCreditoLlegada19();
        packagerDebitoLlegada16 = new PlantillaCredicardDebitoLlegada16();
        packagerDebitoLlegada19 = new PlantillaCredicardDebitoLlegada19();
        dbPerfilConfiguracion = new DBPerfilConfiguracion(MainActivity.baseDeDatos);
        dbRespuestaISO = new DBRespuestaISO(MainActivity.baseDeDatos);
        dbTransaccion = new DBTransaccion(MainActivity.baseDeDatos);
    }

    @Override
    public void run() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitNetwork().build());
        try {
            Socket sk = new Socket();
            //Socket sk = new Socket(dbPerfilConfiguracion.obtenerPerfilConfiguracionporId("1").getDireccionIp(), Integer.parseInt(dbPerfilConfiguracion.obtenerPerfilConfiguracionporId("1").getPuerto()));
            sk.connect(new InetSocketAddress(dbPerfilConfiguracion.obtenerPerfilConfiguracionporId("1").getDireccionIp(), Integer.parseInt(dbPerfilConfiguracion.obtenerPerfilConfiguracionporId("1").getPuerto())),30000);

            DataOutputStream dOut = new DataOutputStream(sk.getOutputStream());
            DataInputStream dIn = new DataInputStream(sk.getInputStream());
            dOut.write(tramaSalida);

            int n = dIn.readByte();
            int n2 = dIn.readByte();
            String longitud = n + "" + n2;
            byte[] TPDU = {dIn.readByte(),dIn.readByte(),dIn.readByte(),dIn.readByte(),dIn.readByte()};
            byte[] bytesEntrada = new byte[Integer.parseInt(longitud) - 5];
            for(int i =0;i<bytesEntrada.length;i++){
                bytesEntrada[i] = dIn.readByte();
            }
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.unpack(bytesEntrada);
            resultadoDeTransaccion(isoMsg);

        } catch (Exception e) {
            resultadoDeTransaccion(null);
        }
    }

    private void resultadoDeTransaccion(ISOMsg resultado) {

        Intent intentOpenActivity = null;
        String codigoMensaje = null;

        if(resultado != null)
            switch (tipoTransaccion) {

            }
        else{
            Intent intent = new Intent(context, MensajeActivity.class);
            intent.putExtra("Mensaje", "Error durante el envio de la trama.");
            context.startActivity(intent);
        }
    }
}



package com.example.demolecturatarjeta.iso;

import android.os.Bundle;

import com.example.demolecturatarjeta.BCDASCII;
import com.example.demolecturatarjeta.MainActivity;
import com.example.demolecturatarjeta.api.SmartPosApplication;
import com.example.demolecturatarjeta.card.object.Anulacion;
import com.example.demolecturatarjeta.card.object.Venta;
import com.example.demolecturatarjeta.database.BaseDeDatos;
import com.example.demolecturatarjeta.database.DBPerfilConfiguracion;
import com.example.demolecturatarjeta.database.DBRespuestaISO;
import com.example.demolecturatarjeta.database.DBTransaccion;
import com.example.demolecturatarjeta.database.object.PerfilConfiguracion;
import com.example.demolecturatarjeta.database.object.RespuestaISO;
import com.example.demolecturatarjeta.database.object.Transaccion;
import com.example.demolecturatarjeta.util.Impresion;
import com.google.gson.Gson;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class TramasISO8583 {

    private static final String TAG = "Prueba - Creacion de tramas - Clase: TramasISO";

    private static final byte[] MENSAJE_ECOTEST_SEND = {0x08,0x00};
    private static final byte[] MENSAJE_VENTA_SEND = {0x02,0x00};
    private static final byte[] MENSAJE_ADVICE_SEND = {0x02,0x20};
    private static final byte[] MENSAJE_REVERSO_SEND = {0x04,0x00};
    private static final byte[] MENSAJE_CIERRE_SEND = {0x05,0x00};
    private static final byte[] MENSAJE_BATCH_SEND = {0x03,0x20};

    private static final byte[] CODIGO_ECOTEST = {(byte) 0x99, 0x00, 0x00};

    private static final byte[] CODIGO_COMPRA_CREDITO = {0x00, 0x30, 0x00};
    private static final byte[] CODIGO_COMPRA_AHORROS = {0x00, 0x10, 0x00};
    private static final byte[] CODIGO_COMPRA_CORRIENTE = {0x00, 0x20, 0x00};
    private static final byte[] CODIGO_COMPRA_PRINCIPAL = {0x00, 0x00, 0x00};

    private static final byte[] CODIGO_ADVICE_CREDITO = {0x23, 0x30, 0x00};
    private static final byte[] CODIGO_ADVICE_AHORROS = {0x23, 0x10, 0x00};
    private static final byte[] CODIGO_ADVICE_CORRIENTE = {0x23, 0x20, 0x00};
    private static final byte[] CODIGO_ADVICE_PRINCIPAL = {0x23, 0x00, 0x00};

    private static final byte[] CODIGO_ANULACION_CREDITO = {0x02, 0x30, 0x00};
    private static final byte[] CODIGO_ANULACION_AHORROS = {0x02, 0x10, 0x00};
    private static final byte[] CODIGO_ANULACION_CORRIENTE = {0x02, 0x20, 0x00};
    private static final byte[] CODIGO_ANULACION_PRINCIPAL = {0x02, 0x00, 0x00};

    private static final byte[] CODIGO_CIERRE_PREVIO = {-0x6E, 0x00, 0x00};
    private static final byte[] CODIGO_CIERRE_POSTERIOR = {(byte)0x96, 0x00, 0x00};

    private static final byte[] MODO_ENTRADA_CHIP = {0x00, 0x51};
    private static final byte[] MODO_ENTRADA_TAP = {0x09, 0x01};
    private static final byte[] MODO_ENTRADA_FALLBACK = {0x08, 0x01};

    private static final byte[] STAN = {0x00, 0x00};

    private static final byte[] NII = {0x00, 0x03};

    private static final byte[] CONDITION_CODE = {0x00};

    private static final byte[] CURRENCY_CODE = {'9','2','8'};

    private static final byte[] LITERAL_MONEDA = {0x00,0x04,'B','s','.',' '};

    private static final byte[] ADITIONAL_DATA_2 = {0x00, 0x39, 0x00, 0x03, 0x30, 0x32, 0x31, 0x00, 0x22, 0x30, 0x33, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x4A, 0x37, 0x34, 0x39, 0x30,
            0x30, 0x32, 0x38, 0x31, 0x32, 0x31, 0x30, 0x00, 0x03, 0x30, 0x35, 0x30, 0x00, 0x03, 0x30, 0x36, 0x30};

    private BaseDeDatos baseDeDatos;
    private DBPerfilConfiguracion dbPerfilConfiguracion;
    private DBRespuestaISO dbRespuestaISO;
    private DBTransaccion dbTransaccion;
    private PerfilConfiguracion perfilConfiguracion;

    private PlantillaCredicard packager;

    private byte[] TPDUSinCifrar = {0x60,0x00,0x03,0x00,0x00};

    private Bundle bundleCamposTrama = new Bundle();

    private int[] indiceCampos = {0,2,3,4,11,12,13,14,22,23,24,25,32,35,37,38,39,41,42,45,47,48,49,52,55,56,57,60,61,62,63};

    public TramasISO8583() {
        baseDeDatos = MainActivity.baseDeDatos;
        packager = new PlantillaCredicard();
        dbPerfilConfiguracion = new DBPerfilConfiguracion(baseDeDatos);
        dbRespuestaISO = new DBRespuestaISO(baseDeDatos);
        dbTransaccion = new DBTransaccion(baseDeDatos);
        perfilConfiguracion = dbPerfilConfiguracion.obtenerPerfilConfiguracionporId("1");
    }

    public Bundle obtenerInformacionCampos(Bundle camposRequeridosTrama, Transaccion transaccion, String tipoLectura, String tipoTransaccion, String tipoTarjeta, String tipoCuenta) {
        byte[] arregloAuxiliarBytes = null;

        String tipoTransaccionGuardada = "";
        String tipoCuentaGuardada = "";
        String PAN = "";
        int monto = 0;
        Long trace = Long.valueOf(0);
        String fechaVencimiento = "";
        String adquirienteConlength = "06" + perfilConfiguracion.getCodigoDeAdquiriente();
        byte[] secondTrakConLength = null;
        String referenciaEmisor = "";
        String codigoAutorizacion = "";
        String codigoRespuesta = "";
        String terminal = dbPerfilConfiguracion.obtenerPerfilConfiguracionporId("1").getTerminalId();
        String numeroAfiliadoconEspacios = dbPerfilConfiguracion.obtenerPerfilConfiguracionporId("1").getNumeroAfiliado() + "     ";
        byte[] pinVenta = new byte[]{};
        byte[] dataIC = new byte[]{};
        byte[] KSN = new byte[]{};
        byte[] documentoIdentidad = new byte[]{};
        String CVV = "";
        int CVVInt = 0;
        byte[] CVVFormateado = new byte[]{};
        byte[] campo45 = new byte[]{};
        byte[] campo48 = new byte[]{};
        if (tipoLectura == null) {
            tipoLectura = "Chip";
        }

        if (transaccion == null) {
            tipoTransaccionGuardada = tipoTransaccion;
            if (!tipoTransaccion.equals("Ecotest") && !tipoTransaccion.equals("Cierrepre") && !tipoTransaccion.equals("Cierrepost")) {
                tipoCuentaGuardada = tipoCuenta;

                PAN = SmartPosApplication.getApp().mConsumeData.getCardno();
                monto = Integer.parseInt("100");
                //monto = Integer.parseInt(SmartPosApplication.getApp().mConsumeData.getAmount());
                trace = Long.valueOf(aumentarTrace());
                //documentoIdentidad = SmartPosApplication.getApp().mConsumeData.getDocumento().getBytes();
                //documentoIdentidad = BCDASCII.hexStringToBytes("0007000001");
                documentoIdentidad = new byte[]{48, 48, 48, 48, 48, 48, 48, 48, 53, 52};
                fechaVencimiento = SmartPosApplication.getApp().mConsumeData.getExpiryData();
                secondTrakConLength = unirCadenasDeBytes(longitudDeTramaTrack2(SmartPosApplication.getApp().mConsumeData.getSecondTrackData().length()), BCDASCII.hexStringToBytes(SmartPosApplication.getApp().mConsumeData.getSecondTrackData()+"0"));
                if (!tipoLectura.equals("Tap") && !tipoLectura.equals("Fallback")) {
                    dataIC = unirCadenasDeBytes(campo55Longitud(SmartPosApplication.getApp().mConsumeData.getICData().length), SmartPosApplication.getApp().mConsumeData.getICData());
                    CVV = "    ";
                    CVVFormateado = CVV.getBytes();
                } else {
                    CVV = SmartPosApplication.getApp().mConsumeData.getCVV();
                    CVVInt = Integer.parseInt(CVV);
                    CVVFormateado = String.format("%04d", CVVInt).getBytes();
                    campo45 = SmartPosApplication.getApp().mConsumeData.getmFirstTrackData();
                }
                campo48 = unirCadenasDeBytes(new byte[]{0x00, 0x14}, unirCadenasDeBytes(CVVFormateado, documentoIdentidad));
                if (tipoTransaccion.equals("Venta") && !tipoCuenta.equals("Credito")) {
                    pinVenta = SmartPosApplication.getApp().mConsumeData.getPin();
                    KSN = BCDASCII.bytesToHexString(SmartPosApplication.getApp().mConsumeData.getKsnValue()).getBytes();
                }
            }
        } else {
            Venta venta = null;
            Anulacion anulacion = null;
            switch (transaccion.getTipoTransaccion()) {
                case "Venta":
                    venta = new Gson().fromJson(transaccion.getTransaccionJSON(), Venta.class);
                    PAN = venta.getPAN();
                    monto = Integer.parseInt(venta.getMonto());
                    trace = transaccion.getTrace();
                    campo48 = unirCadenasDeBytes(new byte[]{0x00, 0x14, 0x20, 0x20, 0x20, 0x20}, venta.getDocumento().getBytes());
                    fechaVencimiento = venta.getFechaExpiracion();
                    referenciaEmisor = venta.getNumeroReferenciaEmisor();
                    codigoAutorizacion = venta.getCodigoAutorizacion();
                    codigoRespuesta = venta.getCodigoRespuesta();
                    break;
                case "Anulacion":
                    anulacion = new Gson().fromJson(transaccion.getTransaccionJSON(), Anulacion.class);
                    PAN = anulacion.getPAN();
                    monto = Integer.parseInt(anulacion.getMonto());
                    trace = transaccion.getTrace();
                    //documentoIdentidad = unirCadenasDeBytes(new byte[]{0x00, 0x14, 0x20, 0x20, 0x20, 0x20},anulacion.getDocumento().getBytes());
                    fechaVencimiento = anulacion.getFechaExpiracion();
                    referenciaEmisor = anulacion.getNumeroReferenciaEmisor();
                    codigoAutorizacion = anulacion.getCodigoAutorizacion();
                    codigoRespuesta = anulacion.getCodigoRespuesta();
            }
            if (tipoTransaccion.equals("Advice") || tipoTransaccion.equals("Anulacion")) {
                tipoTransaccionGuardada = tipoTransaccion;
                if(tipoCuenta != null){
                    tipoCuentaGuardada = tipoCuenta;
                }else{
                    tipoCuentaGuardada = transaccion.getTipoCuenta();
                }
                secondTrakConLength = unirCadenasDeBytes(longitudDeTramaTrack2(SmartPosApplication.getApp().mConsumeData.getSecondTrackData().length()), BCDASCII.hexStringToBytes(SmartPosApplication.getApp().mConsumeData.getSecondTrackData()));
                dataIC = unirCadenasDeBytes(campo55Longitud(SmartPosApplication.getApp().mConsumeData.getICData().length), SmartPosApplication.getApp().mConsumeData.getICData());
            } else {
                tipoTransaccionGuardada = transaccion.getTipoTransaccion();
                if (transaccion.getTipoTransaccion().equals("Venta")) {
                    tipoCuentaGuardada = transaccion.getTipoCuenta();
                } else {
                    tipoCuentaGuardada = transaccion.getTipoCuenta();
                }
                if (!tipoTransaccion.equals("Batch")) {
                    if (!tipoLectura.equals("Tap") && !tipoLectura.equals("Fallback")) {
                        dataIC = unirCadenasDeBytes(campo55Longitud(SmartPosApplication.getApp().mConsumeData.getICData().length), SmartPosApplication.getApp().mConsumeData.getICData());
                    }
                }
            }
        }

        byte[] versionAplicacion = dbPerfilConfiguracion.obtenerPerfilConfiguracionporId("1").getVersionAplicacion().getBytes();
        byte[] version = unirCadenasDeBytes(new byte[]{0x00, 0x10}, versionAplicacion);

        byte[] campo22 = null;
        if (tipoLectura.equals("Tap")) {
            campo22 = MODO_ENTRADA_TAP;
        } else if (tipoLectura.equals("Chip")) {
            campo22 = MODO_ENTRADA_CHIP;
        } else if (tipoLectura.equals("Fallback")){
            campo22 = MODO_ENTRADA_FALLBACK;
        }

        String tipoTerminal;
        if(tipoTarjeta.equals("Credito")){
            tipoTerminal = terminal;
        }else{
            tipoTerminal = terminal.replace(terminal.substring(0, 5), "00002");
        }

        String loteFormateado;
        if(tipoTarjeta.equals("Credito")){
            long loteCredito = Long.valueOf(perfilConfiguracion.getLoteCredito());
            loteFormateado = String.format("%06d",loteCredito);
        }else{
            long loteDebito = Long.valueOf(perfilConfiguracion.getLoteDebito());
            loteFormateado = String.format("%06d",loteDebito);
        }
        byte[] lote = loteFormateado.getBytes();

        byte[] campo60;
        byte[] traceF = String.format("%06d",trace).getBytes();
        if(tipoTransaccion.equals("Cierrepre") || tipoTransaccion.equals("Cierrepost")){
            campo60 = new byte[]{0x00, 0x06, lote[0], lote[1], lote[2], lote[3], lote[4], lote[5]};
        }else{
            campo60 = new byte[]{0x00, 0x22, 0x30, 0x32, 0x30, 0x30, traceF[0], traceF[1], traceF[2], traceF[3], traceF[4], traceF[5], 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20};
        }

        byte[] campo63;

        if(tipoTransaccion.equals("Venta") && !tipoTarjeta.equals("Credito")){
            campo63 = new byte[]{0x00, 0x34, 0x00, 0x22, 0x33, 0x33, KSN[0], KSN[1], KSN[2], KSN[3], KSN[4], KSN[5], KSN[6], KSN[7], KSN[8], KSN[9], KSN[10], KSN[11], KSN[12], KSN[13], KSN[14], KSN[15], KSN[16], KSN[17], KSN[18], KSN[19], 0x00, 0x08, 0x33, 0x37, lote[0], lote[1], lote[2], lote[3], lote[4], lote[5]};
        }else{
            if(tipoTransaccion.equals("Cierrepre") || tipoTransaccion.equals("Cierrepost")){
                //Totales Credito
                Bundle totalesCredito = Impresion.calcularTotales("Credito", null);
                byte[] numeroComprasCredito = String.format("%03d",totalesCredito.getIntArray("CANTIDADES_VENTA")[5]).getBytes();
                byte[] montoComprasCredito = String.format("%012d",totalesCredito.getIntArray("TOTALES_VENTA")[5]).getBytes();
                byte[] numeroDevolucionesCredito = String.format("%03d",totalesCredito.getIntArray("CANTIDADES_ANULACION")[5]).getBytes();
                byte[] montoDevolucionesCredito = String.format("%012d",totalesCredito.getIntArray("TOTALES_ANULACION")[5]).getBytes();
                /////////////////
                //Totales Debito
                Bundle totalesDebito = Impresion.calcularTotales("Debito", null);
                byte[] numeroComprasDebito = String.format("%03d",totalesDebito.getIntArray("CANTIDADES_VENTA")[5]).getBytes();
                byte[] montoComprasDebito = String.format("%012d",totalesDebito.getIntArray("TOTALES_VENTA")[5]).getBytes();
                byte[] numeroDevolucionesDebito = String.format("%03d",totalesDebito.getIntArray("CANTIDADES_ANULACION")[5]).getBytes();
                byte[] montoDevolucionesDebito = String.format("%012d",totalesDebito.getIntArray("TOTALES_ANULACION")[5]).getBytes();
                /////////////////
                if(tipoTarjeta.equals("Credito")){
                    campo63 = new byte[]{0x00, 0x60,
                            numeroComprasCredito[0], numeroComprasCredito[1], numeroComprasCredito[2],
                            montoComprasCredito[0], montoComprasCredito[1], montoComprasCredito[2], montoComprasCredito[3], montoComprasCredito[4], montoComprasCredito[5],
                            montoComprasCredito[6], montoComprasCredito[7], montoComprasCredito[8], montoComprasCredito[9], montoComprasCredito[10], montoComprasCredito[11],
                            numeroDevolucionesCredito[0], numeroDevolucionesCredito[1], numeroDevolucionesCredito[2],
                            montoDevolucionesCredito[0], montoDevolucionesCredito[1], montoDevolucionesCredito[2], montoDevolucionesCredito[3], montoDevolucionesCredito[4], montoDevolucionesCredito[5],
                            montoDevolucionesCredito[6], montoDevolucionesCredito[7], montoDevolucionesCredito[8], montoDevolucionesCredito[9], montoDevolucionesCredito[10], montoDevolucionesCredito[11],
                            0x30, 0x30, 0x30,
                            0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
                            0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
                            0x30, 0x30, 0x30,
                            0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
                            0x30, 0x30, 0x30, 0x30, 0x30, 0x30};
                }else{
                    campo63 = new byte[]{0x00, 0x60,
                            0x30, 0x30, 0x30,
                            0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
                            0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
                            0x30, 0x30, 0x30,
                            0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
                            0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
                            numeroComprasDebito[0], numeroComprasDebito[1], numeroComprasDebito[2],
                            montoComprasDebito[0], montoComprasDebito[1], montoComprasDebito[2], montoComprasDebito[3], montoComprasDebito[4], montoComprasDebito[5],
                            montoComprasDebito[6], montoComprasDebito[7], montoComprasDebito[8], montoComprasDebito[9], montoComprasDebito[10], montoComprasDebito[11],
                            numeroDevolucionesDebito[0], numeroDevolucionesDebito[1], numeroDevolucionesDebito[2],
                            montoDevolucionesDebito[0], montoDevolucionesDebito[1], montoDevolucionesDebito[2], montoDevolucionesDebito[3], montoDevolucionesDebito[4], montoDevolucionesDebito[5],
                            montoDevolucionesDebito[6], montoDevolucionesDebito[7], montoDevolucionesDebito[8], montoDevolucionesDebito[9], montoDevolucionesDebito[10], montoDevolucionesDebito[11]};
                }
            }else{
                campo63 = new byte[]{0x00, 0x10, 0x00, 0x08, 0x33, 0x37, lote[0], lote[1], lote[2], lote[3], lote[4], lote[5]};
            }
        }

        String PANconlength = PAN.length() + PAN;

        for(int indiceCampo = 0; indiceCampo <= indiceCampos.length - 1; indiceCampo++){

            if(camposRequeridosTrama.getBoolean("CAMPO"+indiceCampos[indiceCampo])){

                switch (indiceCampos[indiceCampo]){

                    case 0:
                        arregloAuxiliarBytes = obtenerTipoMensaje(tipoTransaccion);
                        break;

                    case 2:
                        arregloAuxiliarBytes = PANconlength.getBytes();
                        break;

                    case 3:
                        arregloAuxiliarBytes = obtenerCodigo(tipoTransaccionGuardada,tipoCuentaGuardada);
                        break;

                    case 4:
                        arregloAuxiliarBytes = String.format("%012d",monto).getBytes();
                        break;

                    case 11:
                        arregloAuxiliarBytes = String.format("%06d",trace).getBytes();
                        break;

                    case 12:
                        //arregloAuxiliarBytes = horaLocalOriginal;
                        break;

                    case 13:
                        //arregloAuxiliarBytes = fechaLocalOriginal;
                        break;

                    case 14:
                        arregloAuxiliarBytes = fechaVencimiento.getBytes();
                        break;

                    case 22:
                        arregloAuxiliarBytes = campo22;
                        break;

                    case 23:
                        arregloAuxiliarBytes = STAN;
                        break;

                    case 24:
                        arregloAuxiliarBytes = NII;
                        break;

                    case 25:
                        arregloAuxiliarBytes = CONDITION_CODE;
                        break;

                    case 32:
                        arregloAuxiliarBytes = adquirienteConlength.getBytes();
                        break;

                    case 35:
                        arregloAuxiliarBytes = secondTrakConLength;
                        break;

                    case 37:
                        arregloAuxiliarBytes = referenciaEmisor.getBytes();
                        break;

                    case 38:
                        //String provivional = "062345";
                        //arregloAuxiliarBytes = provivional.getBytes();
                        arregloAuxiliarBytes = codigoAutorizacion.getBytes();
                        break;

                    case 39:
                        arregloAuxiliarBytes = codigoRespuesta.getBytes();
                        break;

                    case 41:
                        arregloAuxiliarBytes = tipoTerminal.getBytes();
                        break;

                    case 42:
                        arregloAuxiliarBytes = numeroAfiliadoconEspacios.getBytes();
                        break;

                    case 45:
                        arregloAuxiliarBytes = campo45;
                        break;

                    case 47:
                        arregloAuxiliarBytes = version;
                        break;

                    case 48:
                        arregloAuxiliarBytes = campo48;
                        break;

                    case 49:
                        arregloAuxiliarBytes = CURRENCY_CODE;
                        break;

                    case 52:
                        arregloAuxiliarBytes = pinVenta;
                        break;

                    case 55:
                        arregloAuxiliarBytes = dataIC;
                        break;

                    case 56:
                        arregloAuxiliarBytes = LITERAL_MONEDA;
                        break;

                    case 57:
                        arregloAuxiliarBytes = ADITIONAL_DATA_2;
                        break;

                    case 60:
                        arregloAuxiliarBytes = campo60;
                        break;

                    case 61:
                        arregloAuxiliarBytes = new byte[]{};
                        break;

                    case 62:
                        arregloAuxiliarBytes = new byte[]{0x00, 0x06, 0x30, 0x30, 0x30, 0x30, 0x37, 0x36};
                        break;

                    case 63:
                        arregloAuxiliarBytes = campo63;
                        break;
                }

                bundleCamposTrama.putByteArray("CAMPO"+indiceCampos[indiceCampo],arregloAuxiliarBytes);
            }
        }
        return bundleCamposTrama;
    }

    //Creación de Trama Venta
    public byte[] crearTrama(Bundle informacionTrama){

        ISOMsg m = new ISOMsg();
        byte[] arregloAuxiliarbytes;

        try {

            for(int indiceCampo = 0; indiceCampo <= indiceCampos.length - 1; indiceCampo++){
                arregloAuxiliarbytes = informacionTrama.getByteArray("CAMPO"+indiceCampos[indiceCampo]);
                if(arregloAuxiliarbytes != null){
                    m.set(indiceCampos[indiceCampo],arregloAuxiliarbytes);
                    packager.fld[indiceCampos[indiceCampo]].setLength(arregloAuxiliarbytes.length);
                }
            }

            m.setPackager(packager);
            byte[] binaryImage = m.pack();
            byte[] conTPDU = unirCadenasDeBytes(TPDUSinCifrar , binaryImage);
            byte[] longitud = longitudDeTrama(conTPDU.length);
            return unirCadenasDeBytes(longitud , conTPDU);

        } catch (ISOException e) {
            //LogInterno.d(TAG,"Error de ISO");
            return null;
        }
    }

    private Long aumentarTrace(){
        String trace = perfilConfiguracion.getTrace();
        Long traceLong = Long.valueOf(trace);
        Long siguienteTrace;
        if (traceLong >= 999999){
            siguienteTrace = Long.valueOf(1);
        }else{
            siguienteTrace = traceLong + 1;
        }
        dbPerfilConfiguracion.updateTraceporId("1", String.valueOf(siguienteTrace));
        return siguienteTrace;
    }

    private Long aumentarReferencia(){
        String referencia = perfilConfiguracion.getReferencia();
        Long referenciaLong = Long.valueOf(referencia);
        Long siguienteReferencia;
        if (referenciaLong >= 999999){
            siguienteReferencia = Long.valueOf(1);
        }else{
            siguienteReferencia = referenciaLong + 1;
        }
        dbPerfilConfiguracion.updateReferenciaporId("1", String.valueOf(siguienteReferencia));
        return siguienteReferencia;
    }

    public Long aumentarLoteCredito(){
        String numeroLoteCredito = perfilConfiguracion.getLoteCredito();
        Long numeroLoteCreditoLong = Long.valueOf(numeroLoteCredito);
        Long siguienteNumeroLoteCredito;
        if (numeroLoteCreditoLong >= 999){
            siguienteNumeroLoteCredito = Long.valueOf(1);
        }else{
            siguienteNumeroLoteCredito = numeroLoteCreditoLong + 1;
        }
        dbPerfilConfiguracion.updateLoteCreditoporId("1", String.valueOf(siguienteNumeroLoteCredito));
        return siguienteNumeroLoteCredito;
    }

    public Long aumentarLoteDebito(){
        String numeroLoteDebito = perfilConfiguracion.getLoteDebito();
        Long numeroLoteDebitoLong = Long.valueOf(numeroLoteDebito);
        Long siguienteNumeroLoteDebito;
        if (numeroLoteDebitoLong >= 999){
            siguienteNumeroLoteDebito = Long.valueOf(1);
        }else{
            siguienteNumeroLoteDebito = numeroLoteDebitoLong + 1;
        }
        dbPerfilConfiguracion.updateLoteDebitoporId("1", String.valueOf(siguienteNumeroLoteDebito));
        return siguienteNumeroLoteDebito;
    }

    public void agregarABaseDeDatosTransaccion(String tipoTarjeta, String tipoCuenta, String tipo, String JSON){

        Long numeroTrace = Long.valueOf("0");
        Long numeroTraceActual = Long.valueOf(perfilConfiguracion.getTrace());
        Long numeroReferencia = Long.valueOf("0");
        Long numeroLoteActual;
        if(tipoTarjeta.equals("Credito")){
            numeroLoteActual = Long.valueOf(perfilConfiguracion.getLoteCredito());
        }else{
            numeroLoteActual = Long.valueOf(perfilConfiguracion.getLoteDebito());
        }

        switch (tipo){

            case "EcoTest":
                numeroTrace = Long.valueOf(aumentarTrace());
                dbTransaccion.agregarTransaccion(new Transaccion(null, numeroTrace,null,tipoTarjeta,null,numeroLoteActual,tipo,JSON,null));
                break;

            case "Venta":
                //al crear la transaccion le pongo estado 4 que segun la tabla de estados es aprobada con advice
                dbTransaccion.agregarTransaccion(new Transaccion(null, numeroTraceActual, null,tipoTarjeta,tipoCuenta, numeroLoteActual,tipo,JSON,0));
                break;

            case "Advice":
                numeroTrace = Long.valueOf(aumentarTrace());
                numeroReferencia = Long.valueOf(aumentarReferencia());
                dbTransaccion.agregarTransaccion(new Transaccion(null, numeroTrace, null,tipoTarjeta,null,numeroLoteActual,tipo,JSON,null));
                break;

            case "Reverso":
                numeroTrace = Long.valueOf(aumentarTrace());
                dbTransaccion.agregarTransaccion(new Transaccion(null, numeroTrace,null,tipoTarjeta,null,numeroLoteActual,tipo,JSON,null));
                break;

            case "Anulacion":
                numeroTrace = Long.valueOf(aumentarTrace());
                numeroReferencia = Long.valueOf(aumentarReferencia());
                dbTransaccion.agregarTransaccion(new Transaccion(null, numeroTrace, numeroReferencia,tipoTarjeta,tipoCuenta,numeroLoteActual,tipo,JSON,4));
                break;

            case "Cierrepre":
            case "Cierrepost":
                numeroTrace = Long.valueOf(aumentarTrace());
                dbTransaccion.agregarTransaccion(new Transaccion(null, numeroTrace, null,tipoTarjeta,null,numeroLoteActual,tipo,JSON,4));
                //Long numeroLote;
                //if(tipoTarjeta.equals("Credito")){
                    //dbTransaccion.eliminarTransaccionesporNumeroLoteCredito(tipoTarjeta,numeroLoteActual);
                    //numeroLote = aumentarLoteCredito();
                //}else{
                    //dbTransaccion.eliminarTransaccionesporNumeroLoteDebito(tipoTarjeta,numeroLoteActual);
                    //numeroLote = aumentarLoteDebito();
                //}
                break;
        }
    }

    //Obtención del Mensaje de llegada
    public String obtenerMensaje(ISOMsg isoMsg) {

        String resultado = "ERROR NO ESPECIFICADO";

        char n = (char) isoMsg.getBytes(39)[0];
        char n2 = (char) isoMsg.getBytes(39)[1];

        RespuestaISO respuestaISO = dbRespuestaISO.obtenerRespuestaISOporCodigo(String.valueOf(n) + n2);

        if(respuestaISO != null){
            //LogInterno.d(TAG,"Esto llega " + dbRespuestaISO.obtenerRespuestaISOporCodigo( String.valueOf(n) + n2).getDescripcion() + " esto " + n + n2);
            resultado = dbRespuestaISO.obtenerRespuestaISOporCodigo( String.valueOf(n) + n2).getCodigo();
        }

        return resultado;
    }

    //Se Ingresa una longitud en decimal y la arroja en Hexadecimal
    private static byte[] longitudDeTrama(Integer val){
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(val));
        while(sb.length() < 4){
            sb.insert(0,'0');// pad with leading zero
        }
        int l = sb.length(); // total string length before spaces
        int r = l/2; //num of rquired iterations
        for (int i=1; i < r;  i++){
            int x = l-(2*i); //space postion
            sb.insert(x, ' ');
        }
        return new byte[]{(byte) Integer.parseInt(sb.toString().toUpperCase().substring(0,2),16),
                (byte) Integer.parseInt(sb.toString().toUpperCase().substring(3,5),16)};
    }

    private static byte[] longitudDeTramaTrack2(Integer val) {
       // val = (val)-1;
        byte[] cadenaDeBytes ={0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10,
                0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x20,
                0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x30,
                0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x40,
                0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x50,
                0x51, 0x52, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x60,
                0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x70,
                0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, (byte) 0x80};
        byte[] longitudTrack2 = new byte[] {cadenaDeBytes[val]};
        //LogInterno.d(TAG, "Longitud de track 2: " + val);
        //LogInterno.d(TAG, "Longitud de track 2: " + cadenaDeBytes[val]);
        return longitudTrack2;
    }

    //Se Ingresa una longitud en decimal y la arroja en Hexadecimal
    private static byte[] campo55Longitud(int val) {
        val = val *2;
        byte[] cadenaDeBytes ={0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10,
                0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x20,
                0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x30,
                0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x40,
                0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x50,
                0x51, 0x52, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x60,
                0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x70,
                0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, (byte) 0x80,
                (byte) 0x81, (byte) 0x82, (byte) 0x83, (byte) 0x84, (byte) 0x85, (byte) 0x86, (byte) 0x87, (byte) 0x88, (byte) 0x89, (byte) 0x90,
                (byte) 0x91, (byte) 0x92, (byte) 0x93, (byte) 0x94, (byte) 0x95, (byte) 0x96, (byte) 0x97, (byte) 0x98, (byte) 0x99};
        String palabra = String.valueOf(val);
        int primero = Integer.parseInt(palabra.substring(palabra.length()-2));
        int segundo = val/100;
        return new byte[] {cadenaDeBytes[segundo],cadenaDeBytes[primero]};
    }

    //Concatena cadenas de Bytes
    private byte[] unirCadenasDeBytes(byte[] Cadena1, byte[] Cadena2) {
        byte[] cadenaDeSalida = new byte[Cadena1.length + Cadena2.length];
        System.arraycopy(Cadena1, 0, cadenaDeSalida, 0, Cadena1.length);
        System.arraycopy(Cadena2, 0, cadenaDeSalida, Cadena1.length, Cadena2.length);
        return cadenaDeSalida;
    }

    //Función que muestra en Log un campo y sus valores
    private void mostrar(int field, ISOMsg isoMsg) {
        //LogInterno.d(TAG,"Field "+field+": " + isoMsg.getBytes(field).length);
        for(int i = 0; i<isoMsg.getBytes(field).length;i++){
            int mostrar = isoMsg.getBytes(field)[i];
            //LogInterno.d(TAG,"Field "+field+" byte "+i+": " + (byte) mostrar);
        }
    }

    private byte[] obtenerTipoMensaje(String tipoTransaccion) {
        byte[] arregloAuxiliarBytes = null;
        switch(tipoTransaccion){
            case "Ecotest":
                arregloAuxiliarBytes = MENSAJE_ECOTEST_SEND;
                break;
            case "Venta":
                arregloAuxiliarBytes = MENSAJE_VENTA_SEND;
                break;
            case "Advice":
                arregloAuxiliarBytes = MENSAJE_ADVICE_SEND;
                break;
            case "Reverso":
                arregloAuxiliarBytes = MENSAJE_REVERSO_SEND;
                break;
            case "Anulacion":
                arregloAuxiliarBytes = MENSAJE_VENTA_SEND;
                break;
            case "Cierrepre":
            case "Cierrepost":
                arregloAuxiliarBytes = MENSAJE_CIERRE_SEND;
                break;
            case "Batch":
                arregloAuxiliarBytes = MENSAJE_BATCH_SEND;
                break;
        }
        return arregloAuxiliarBytes;
    }

    private byte[] obtenerCodigo(String tipoTransaccion, String tipoCuenta) {
        byte[] arregloAuxiliarBytes = null;
        switch (tipoTransaccion) {
            case "Ecotest":
                arregloAuxiliarBytes = CODIGO_ECOTEST;
                break;
            case "Venta":
                switch (tipoCuenta) {
                    case "Credito":
                        arregloAuxiliarBytes = CODIGO_COMPRA_CREDITO;
                        break;
                    case "Ahorro":
                        arregloAuxiliarBytes = CODIGO_COMPRA_AHORROS;
                        break;
                    case "Corriente":
                        arregloAuxiliarBytes = CODIGO_COMPRA_CORRIENTE;
                        break;
                    case "Principal":
                        arregloAuxiliarBytes = CODIGO_COMPRA_PRINCIPAL;
                }
                break;
            case "Advice":
                switch (tipoCuenta) {
                    case "Credito":
                        arregloAuxiliarBytes = CODIGO_ADVICE_CREDITO;
                        break;
                    case "Ahorro":
                        arregloAuxiliarBytes = CODIGO_ADVICE_AHORROS;
                        break;
                    case "Corriente":
                        arregloAuxiliarBytes = CODIGO_ADVICE_CORRIENTE;
                        break;
                    case "Principal":
                        arregloAuxiliarBytes = CODIGO_ADVICE_PRINCIPAL;
                        break;
                }
                break;
            case "Anulacion":
                switch (tipoCuenta) {
                    case "Credito":
                        arregloAuxiliarBytes = CODIGO_ANULACION_CREDITO;
                        break;
                    case "Ahorro":
                        arregloAuxiliarBytes = CODIGO_ANULACION_AHORROS;
                        break;
                    case "Corriente":
                        arregloAuxiliarBytes = CODIGO_ANULACION_CORRIENTE;
                        break;
                    case "Principal":
                        arregloAuxiliarBytes = CODIGO_ANULACION_PRINCIPAL;
                        break;
                }
                break;
            case "Cierrepre":
                arregloAuxiliarBytes = CODIGO_CIERRE_PREVIO;
                break;
            case "Cierrepost":
                arregloAuxiliarBytes = CODIGO_CIERRE_POSTERIOR;
                break;
        }
        return arregloAuxiliarBytes;
    }

    public Bundle obtenerCamposRequeridosEcotest(){
        Bundle bundleCamposRequeridos = new Bundle();
        bundleCamposRequeridos.putBoolean("CAMPO0",true);
        bundleCamposRequeridos.putBoolean("CAMPO2",false);
        bundleCamposRequeridos.putBoolean("CAMPO3",true);
        bundleCamposRequeridos.putBoolean("CAMPO4",false);
        bundleCamposRequeridos.putBoolean("CAMPO11",false);
        bundleCamposRequeridos.putBoolean("CAMPO12",false);
        bundleCamposRequeridos.putBoolean("CAMPO13",false);
        bundleCamposRequeridos.putBoolean("CAMPO14",false);
        bundleCamposRequeridos.putBoolean("CAMPO22",false);
        bundleCamposRequeridos.putBoolean("CAMPO23",false);
        bundleCamposRequeridos.putBoolean("CAMPO24",false);
        bundleCamposRequeridos.putBoolean("CAMPO25",false);
        bundleCamposRequeridos.putBoolean("CAMPO32",false);
        bundleCamposRequeridos.putBoolean("CAMPO35",false);
        bundleCamposRequeridos.putBoolean("CAMPO37",false);
        bundleCamposRequeridos.putBoolean("CAMPO38",false);
        bundleCamposRequeridos.putBoolean("CAMPO39",false);
        bundleCamposRequeridos.putBoolean("CAMPO41",true);
        bundleCamposRequeridos.putBoolean("CAMPO42",true);
        bundleCamposRequeridos.putBoolean("CAMPO45",false);
        bundleCamposRequeridos.putBoolean("CAMPO47",true);
        bundleCamposRequeridos.putBoolean("CAMPO48",false);
        bundleCamposRequeridos.putBoolean("CAMPO49",false);
        bundleCamposRequeridos.putBoolean("CAMPO52",false);
        bundleCamposRequeridos.putBoolean("CAMPO55",false);
        bundleCamposRequeridos.putBoolean("CAMPO56",true);
        bundleCamposRequeridos.putBoolean("CAMPO57",false);
        bundleCamposRequeridos.putBoolean("CAMPO60",false);
        bundleCamposRequeridos.putBoolean("CAMPO61",false);
        bundleCamposRequeridos.putBoolean("CAMPO62",false);
        bundleCamposRequeridos.putBoolean("CAMPO63",false);
        return bundleCamposRequeridos;
    }

    public Bundle obtenerCamposRequeridosVenta(String tipoLectura, String tipoTarjeta){
        Bundle bundleCamposRequeridos = new Bundle();
        bundleCamposRequeridos.putBoolean("CAMPO0",true);
        bundleCamposRequeridos.putBoolean("CAMPO2",false);
        bundleCamposRequeridos.putBoolean("CAMPO3",true);
        bundleCamposRequeridos.putBoolean("CAMPO4",true);
        bundleCamposRequeridos.putBoolean("CAMPO11",true);
        bundleCamposRequeridos.putBoolean("CAMPO12",false);
        bundleCamposRequeridos.putBoolean("CAMPO13",false);
        bundleCamposRequeridos.putBoolean("CAMPO14",true);
        bundleCamposRequeridos.putBoolean("CAMPO22",true);
        bundleCamposRequeridos.putBoolean("CAMPO23",true);
        bundleCamposRequeridos.putBoolean("CAMPO24",true);
        bundleCamposRequeridos.putBoolean("CAMPO25",true);
        bundleCamposRequeridos.putBoolean("CAMPO32",true);
        bundleCamposRequeridos.putBoolean("CAMPO35",true);
        bundleCamposRequeridos.putBoolean("CAMPO37",false);
        bundleCamposRequeridos.putBoolean("CAMPO38",false);
        bundleCamposRequeridos.putBoolean("CAMPO39",false);
        bundleCamposRequeridos.putBoolean("CAMPO41",true);
        bundleCamposRequeridos.putBoolean("CAMPO42",true);
        if(tipoLectura.equals("Tap") || tipoLectura.equals("Fallback")) {
            bundleCamposRequeridos.putBoolean("CAMPO45", false);
        }else{
            bundleCamposRequeridos.putBoolean("CAMPO45", false);
        }
        bundleCamposRequeridos.putBoolean("CAMPO47",true);
        bundleCamposRequeridos.putBoolean("CAMPO48",true);
        bundleCamposRequeridos.putBoolean("CAMPO49",true);
        if(tipoTarjeta.equals("Credito")){
            bundleCamposRequeridos.putBoolean("CAMPO52",false);
        }else{
            bundleCamposRequeridos.putBoolean("CAMPO52",true);
        }
        if(tipoLectura.equals("Tap") || tipoLectura.equals("Fallback")){
            bundleCamposRequeridos.putBoolean("CAMPO55",false);
        }else{
            bundleCamposRequeridos.putBoolean("CAMPO55",true);
        }
        bundleCamposRequeridos.putBoolean("CAMPO56",true);
        bundleCamposRequeridos.putBoolean("CAMPO57",true);
        bundleCamposRequeridos.putBoolean("CAMPO60",false);
        bundleCamposRequeridos.putBoolean("CAMPO61",false);
        bundleCamposRequeridos.putBoolean("CAMPO62",true);
        bundleCamposRequeridos.putBoolean("CAMPO63",true);
        return bundleCamposRequeridos;
    }

    public Bundle obtenerCamposRequeridosAdvice(){
        Bundle bundleCamposRequeridos = new Bundle();
        bundleCamposRequeridos.putBoolean("CAMPO0",true);
        bundleCamposRequeridos.putBoolean("CAMPO2",true);
        bundleCamposRequeridos.putBoolean("CAMPO3",true);
        bundleCamposRequeridos.putBoolean("CAMPO4",true);
        bundleCamposRequeridos.putBoolean("CAMPO11",true);
        bundleCamposRequeridos.putBoolean("CAMPO12",false);
        bundleCamposRequeridos.putBoolean("CAMPO13",false);
        bundleCamposRequeridos.putBoolean("CAMPO14",false);
        bundleCamposRequeridos.putBoolean("CAMPO22",true);
        bundleCamposRequeridos.putBoolean("CAMPO23",true);
        bundleCamposRequeridos.putBoolean("CAMPO24",true);
        bundleCamposRequeridos.putBoolean("CAMPO25",true);
        bundleCamposRequeridos.putBoolean("CAMPO32",false);
        bundleCamposRequeridos.putBoolean("CAMPO35",false);
        bundleCamposRequeridos.putBoolean("CAMPO37",false);
        bundleCamposRequeridos.putBoolean("CAMPO38",true);
        bundleCamposRequeridos.putBoolean("CAMPO39",true);
        bundleCamposRequeridos.putBoolean("CAMPO41",true);
        bundleCamposRequeridos.putBoolean("CAMPO42",true);
        bundleCamposRequeridos.putBoolean("CAMPO45",false);
        bundleCamposRequeridos.putBoolean("CAMPO47",true);
        bundleCamposRequeridos.putBoolean("CAMPO48",false);
        bundleCamposRequeridos.putBoolean("CAMPO49",true);
        bundleCamposRequeridos.putBoolean("CAMPO52",false);
        bundleCamposRequeridos.putBoolean("CAMPO55",true);
        bundleCamposRequeridos.putBoolean("CAMPO56",true);
        bundleCamposRequeridos.putBoolean("CAMPO57",true);
        bundleCamposRequeridos.putBoolean("CAMPO60",false);
        bundleCamposRequeridos.putBoolean("CAMPO61",false);
        bundleCamposRequeridos.putBoolean("CAMPO62",true);
        bundleCamposRequeridos.putBoolean("CAMPO63",true);
        return bundleCamposRequeridos;
    }

    public Bundle obtenerCamposRequeridosReverso(String tipoLectura){
        Bundle bundleCamposRequeridos = new Bundle();
        bundleCamposRequeridos.putBoolean("CAMPO0",true);
        bundleCamposRequeridos.putBoolean("CAMPO2",true);
        bundleCamposRequeridos.putBoolean("CAMPO3",true);
        bundleCamposRequeridos.putBoolean("CAMPO4",true);
        bundleCamposRequeridos.putBoolean("CAMPO11",true);
        bundleCamposRequeridos.putBoolean("CAMPO12",false);
        bundleCamposRequeridos.putBoolean("CAMPO13",false);
        bundleCamposRequeridos.putBoolean("CAMPO14",true);
        bundleCamposRequeridos.putBoolean("CAMPO22",true);
        bundleCamposRequeridos.putBoolean("CAMPO23",true);
        bundleCamposRequeridos.putBoolean("CAMPO24",true);
        bundleCamposRequeridos.putBoolean("CAMPO25",true);
        bundleCamposRequeridos.putBoolean("CAMPO32",true);
        bundleCamposRequeridos.putBoolean("CAMPO35",false);
        bundleCamposRequeridos.putBoolean("CAMPO37",false);
        bundleCamposRequeridos.putBoolean("CAMPO38",false);
        bundleCamposRequeridos.putBoolean("CAMPO39",false);
        bundleCamposRequeridos.putBoolean("CAMPO41",true);
        bundleCamposRequeridos.putBoolean("CAMPO42",true);
        bundleCamposRequeridos.putBoolean("CAMPO45",false);
        bundleCamposRequeridos.putBoolean("CAMPO47",true);
        bundleCamposRequeridos.putBoolean("CAMPO48",false);
        bundleCamposRequeridos.putBoolean("CAMPO49",true);
        bundleCamposRequeridos.putBoolean("CAMPO52",false);
        if(tipoLectura.equals("Tap") || tipoLectura.equals("Fallback")){
            bundleCamposRequeridos.putBoolean("CAMPO55",false);
        }else {
            bundleCamposRequeridos.putBoolean("CAMPO55",true);
        }
        bundleCamposRequeridos.putBoolean("CAMPO56",true);
        bundleCamposRequeridos.putBoolean("CAMPO57",true);
        bundleCamposRequeridos.putBoolean("CAMPO60",false);
        bundleCamposRequeridos.putBoolean("CAMPO61",false);
        bundleCamposRequeridos.putBoolean("CAMPO62",true);
        bundleCamposRequeridos.putBoolean("CAMPO63",true);
        return bundleCamposRequeridos;
    }

    public Bundle obtenerCamposRequeridosAnulacion(){
        Bundle bundleCamposRequeridos = new Bundle();
        bundleCamposRequeridos.putBoolean("CAMPO0",true);
        bundleCamposRequeridos.putBoolean("CAMPO2",false);
        bundleCamposRequeridos.putBoolean("CAMPO3",true);
        bundleCamposRequeridos.putBoolean("CAMPO4",true);
        bundleCamposRequeridos.putBoolean("CAMPO11",true);
        bundleCamposRequeridos.putBoolean("CAMPO12",false);
        bundleCamposRequeridos.putBoolean("CAMPO13",false);
        bundleCamposRequeridos.putBoolean("CAMPO14",true);
        bundleCamposRequeridos.putBoolean("CAMPO22",true);
        bundleCamposRequeridos.putBoolean("CAMPO23",true);
        bundleCamposRequeridos.putBoolean("CAMPO24",true);
        bundleCamposRequeridos.putBoolean("CAMPO25",true);
        bundleCamposRequeridos.putBoolean("CAMPO32",true);
        bundleCamposRequeridos.putBoolean("CAMPO35",true);
        bundleCamposRequeridos.putBoolean("CAMPO37",true);
        bundleCamposRequeridos.putBoolean("CAMPO38",true);
        bundleCamposRequeridos.putBoolean("CAMPO39",true);
        bundleCamposRequeridos.putBoolean("CAMPO41",true);
        bundleCamposRequeridos.putBoolean("CAMPO42",true);
        bundleCamposRequeridos.putBoolean("CAMPO45",false);
        bundleCamposRequeridos.putBoolean("CAMPO47",true);
        bundleCamposRequeridos.putBoolean("CAMPO48",true);
        bundleCamposRequeridos.putBoolean("CAMPO49",true);
        bundleCamposRequeridos.putBoolean("CAMPO52",false);
        bundleCamposRequeridos.putBoolean("CAMPO55",true);
        bundleCamposRequeridos.putBoolean("CAMPO56",true);
        bundleCamposRequeridos.putBoolean("CAMPO57",true);
        bundleCamposRequeridos.putBoolean("CAMPO60",false);
        bundleCamposRequeridos.putBoolean("CAMPO61",false);
        bundleCamposRequeridos.putBoolean("CAMPO62",true);
        bundleCamposRequeridos.putBoolean("CAMPO63",true);
        return bundleCamposRequeridos;
    }

    public Bundle obtenerCamposRequeridosCierre(){
        Bundle bundleCamposRequeridos = new Bundle();
        bundleCamposRequeridos.putBoolean("CAMPO0",true);
        bundleCamposRequeridos.putBoolean("CAMPO2",false);
        bundleCamposRequeridos.putBoolean("CAMPO3",true);
        bundleCamposRequeridos.putBoolean("CAMPO4",false);
        bundleCamposRequeridos.putBoolean("CAMPO11",true);
        bundleCamposRequeridos.putBoolean("CAMPO12",false);
        bundleCamposRequeridos.putBoolean("CAMPO13",false);
        bundleCamposRequeridos.putBoolean("CAMPO14",false);
        bundleCamposRequeridos.putBoolean("CAMPO22",false);
        bundleCamposRequeridos.putBoolean("CAMPO23",false);
        bundleCamposRequeridos.putBoolean("CAMPO24",true);
        bundleCamposRequeridos.putBoolean("CAMPO25",false);
        bundleCamposRequeridos.putBoolean("CAMPO32",false);
        bundleCamposRequeridos.putBoolean("CAMPO35",false);
        bundleCamposRequeridos.putBoolean("CAMPO37",false);
        bundleCamposRequeridos.putBoolean("CAMPO38",false);
        bundleCamposRequeridos.putBoolean("CAMPO39",false);
        bundleCamposRequeridos.putBoolean("CAMPO41",true);
        bundleCamposRequeridos.putBoolean("CAMPO42",true);
        bundleCamposRequeridos.putBoolean("CAMPO45",false);
        bundleCamposRequeridos.putBoolean("CAMPO47",true);
        bundleCamposRequeridos.putBoolean("CAMPO48",false);
        bundleCamposRequeridos.putBoolean("CAMPO49",false);
        bundleCamposRequeridos.putBoolean("CAMPO52",false);
        bundleCamposRequeridos.putBoolean("CAMPO55",false);
        bundleCamposRequeridos.putBoolean("CAMPO56",false);
        bundleCamposRequeridos.putBoolean("CAMPO57",false);
        bundleCamposRequeridos.putBoolean("CAMPO60",true);
        bundleCamposRequeridos.putBoolean("CAMPO61",false);
        bundleCamposRequeridos.putBoolean("CAMPO62",false);
        bundleCamposRequeridos.putBoolean("CAMPO63",true);
        return bundleCamposRequeridos;
    }

    public Bundle obtenerCamposRequeridosPrueba(){
        Bundle bundleCamposRequeridos = new Bundle();
        bundleCamposRequeridos.putBoolean("CAMPO0",true);
        bundleCamposRequeridos.putBoolean("CAMPO2",true);
        bundleCamposRequeridos.putBoolean("CAMPO3",true);
        bundleCamposRequeridos.putBoolean("CAMPO4",true);
        bundleCamposRequeridos.putBoolean("CAMPO11",true);
        bundleCamposRequeridos.putBoolean("CAMPO12",false);
        bundleCamposRequeridos.putBoolean("CAMPO13",false);
        bundleCamposRequeridos.putBoolean("CAMPO14",true);
        bundleCamposRequeridos.putBoolean("CAMPO22",true);
        bundleCamposRequeridos.putBoolean("CAMPO23",true);
        bundleCamposRequeridos.putBoolean("CAMPO24",true);
        bundleCamposRequeridos.putBoolean("CAMPO25",true);
        bundleCamposRequeridos.putBoolean("CAMPO32",true);
        bundleCamposRequeridos.putBoolean("CAMPO35",true);
        bundleCamposRequeridos.putBoolean("CAMPO37",false);
        bundleCamposRequeridos.putBoolean("CAMPO38",false);
        bundleCamposRequeridos.putBoolean("CAMPO39",false);
        bundleCamposRequeridos.putBoolean("CAMPO41",true);
        bundleCamposRequeridos.putBoolean("CAMPO42",true);
        bundleCamposRequeridos.putBoolean("CAMPO45",false);
        bundleCamposRequeridos.putBoolean("CAMPO47",true);
        bundleCamposRequeridos.putBoolean("CAMPO48",true);
        bundleCamposRequeridos.putBoolean("CAMPO49",false);
        bundleCamposRequeridos.putBoolean("CAMPO52",false);
        bundleCamposRequeridos.putBoolean("CAMPO55",true);
        bundleCamposRequeridos.putBoolean("CAMPO56",true);
        bundleCamposRequeridos.putBoolean("CAMPO57",true);
        bundleCamposRequeridos.putBoolean("CAMPO60",false);
        bundleCamposRequeridos.putBoolean("CAMPO61",false);
        bundleCamposRequeridos.putBoolean("CAMPO62",true);
        bundleCamposRequeridos.putBoolean("CAMPO63",true);
        return bundleCamposRequeridos;
    }

    public Bundle obtenerCamposRequeridosBatch(){
        Bundle bundleCamposRequeridos = new Bundle();
        bundleCamposRequeridos.putBoolean("CAMPO0",true);
        bundleCamposRequeridos.putBoolean("CAMPO2",true);
        bundleCamposRequeridos.putBoolean("CAMPO3",true);
        bundleCamposRequeridos.putBoolean("CAMPO4",true);
        bundleCamposRequeridos.putBoolean("CAMPO11",true);
        bundleCamposRequeridos.putBoolean("CAMPO12",false);
        bundleCamposRequeridos.putBoolean("CAMPO13",false);
        bundleCamposRequeridos.putBoolean("CAMPO14",true);
        bundleCamposRequeridos.putBoolean("CAMPO22",true);
        bundleCamposRequeridos.putBoolean("CAMPO23",true);
        bundleCamposRequeridos.putBoolean("CAMPO24",true);
        bundleCamposRequeridos.putBoolean("CAMPO25",true);
        bundleCamposRequeridos.putBoolean("CAMPO32",true);
        bundleCamposRequeridos.putBoolean("CAMPO35",false);
        bundleCamposRequeridos.putBoolean("CAMPO37",true);
        bundleCamposRequeridos.putBoolean("CAMPO38",true);
        bundleCamposRequeridos.putBoolean("CAMPO39",false);
        bundleCamposRequeridos.putBoolean("CAMPO41",true);
        bundleCamposRequeridos.putBoolean("CAMPO42",true);
        bundleCamposRequeridos.putBoolean("CAMPO45",false);
        bundleCamposRequeridos.putBoolean("CAMPO47",true);
        bundleCamposRequeridos.putBoolean("CAMPO48",false);
        bundleCamposRequeridos.putBoolean("CAMPO49",false);
        bundleCamposRequeridos.putBoolean("CAMPO52",false);
        bundleCamposRequeridos.putBoolean("CAMPO55",false);
        bundleCamposRequeridos.putBoolean("CAMPO56",false);
        bundleCamposRequeridos.putBoolean("CAMPO57",true);
        bundleCamposRequeridos.putBoolean("CAMPO60",true);
        bundleCamposRequeridos.putBoolean("CAMPO61",false);
        bundleCamposRequeridos.putBoolean("CAMPO62",true);
        bundleCamposRequeridos.putBoolean("CAMPO63",true);
        return bundleCamposRequeridos;
    }
}

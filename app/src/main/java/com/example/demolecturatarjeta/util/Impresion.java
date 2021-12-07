package com.example.demolecturatarjeta.util;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.demolecturatarjeta.MainActivity;
import com.example.demolecturatarjeta.card.object.Anulacion;
import com.example.demolecturatarjeta.card.object.Venta;
import com.example.demolecturatarjeta.database.DBBines;
import com.example.demolecturatarjeta.database.DBPerfilConfiguracion;
import com.example.demolecturatarjeta.database.DBTransaccion;
import com.example.demolecturatarjeta.database.object.PerfilConfiguracion;
import com.example.demolecturatarjeta.database.object.Transaccion;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Impresion {

    private static String TAG = "Prueba - Imprimir - Clase: Impresion";

    public static final int VISA = 0;
    public static final int MASTER_CARD = 1;
    public static final int MAESTRO = 2;
    public static final int AMEX = 3;
    public static final int PRIVADA = 4;
    public static final int TOTAL = 5;

    //Array marcas tarjeta
    public static String[] marcasTarjeta = {"VISA", "MASTER CARD", "MAESTRO", "AMEX", "PRIVADA"};

    //Bases de datos
    public static DBPerfilConfiguracion dbPerfilConfiguracion;
    public static DBTransaccion dbTransaccion;
    public static DBBines dbBines;

    //Objetos PerfilConfiguracion y Transaccion
    public static PerfilConfiguracion perfilConfiguracion;
    public static Transaccion transaccion;

    //Declarando objetos ultimaVenta y ultimaAnulacion.
    public static Venta ultimaVenta;

    //Declarando objetos ultimaVentaAprobada y ultimaAnulacionAprobada.
    public static Venta ultimaVentaAprobada;
    public static Anulacion ultimaAnulacionAprobada;

    //Declarando objetos ultimaVentaAprobada y ultimaAnulacionAprobada.
    public static Venta venta;
    public static Anulacion anulacion;

    //Variables para fecha y hora de impresion.
    public static Calendar calendar = Calendar.getInstance();
    public static SimpleDateFormat fechaFormateada = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    public static SimpleDateFormat horaFormateada = new SimpleDateFormat("HHmmss", Locale.getDefault());

    //Variables para formato numerico.
    public static DecimalFormat df = new DecimalFormat("#,##0.00");

    //Lista de transacciones
    public static ArrayList<Transaccion> transaccionesLista;
    public static ArrayList<Venta> ventasLista;
    public static ArrayList<Anulacion> anulacionesLista;
    public static ArrayList<String> marcaTarjetaVentaLista;
    public static ArrayList<String> marcaTarjetaAnulacionLista;
    public static ArrayList<String> referenciaVentaLista;
    public static ArrayList<String> referenciaAnulacionLista;

    //Funcion para abrir la tablas.
    public static void iniciarTablasUsadasenImpresion() {

        dbPerfilConfiguracion = new DBPerfilConfiguracion(MainActivity.baseDeDatos);
        dbTransaccion = new DBTransaccion(MainActivity.baseDeDatos);
        dbBines = new DBBines(MainActivity.baseDeDatos);
        //Obteniendo registro de configuracion
        perfilConfiguracion = dbPerfilConfiguracion.obtenerPerfilConfiguracionporId("1");
        //Establecer simbolos del formato decimal
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));
    }

    public static String obtenerFormatoNegada(String tipoTransaccion, String codigoError, String descripcionError){
        String cadenaImpresion = "";
        Gson gson = new Gson();

        //Obteniendo los parametros de configuracion desde la tabla Perfil configuracion.
        perfilConfiguracion = dbPerfilConfiguracion.obtenerPerfilConfiguracionporId("1");

        //Obteniendo la ultima transaccion aprobada.
        transaccionesLista = dbTransaccion.obtenerTransaccionesPorTipoTransaccion(tipoTransaccion);
        transaccion = transaccionesLista.get(transaccionesLista.size() - 1);

        if (transaccion != null) {
            int numeroLetrasTerminal = perfilConfiguracion.getTerminalId().length();
            String rifAdquirienteSinGuion = TextUtils.join("", perfilConfiguracion.getNumeroRIFAdquiriente().split("-"));
            String rifComercioSinGuion = TextUtils.join("", perfilConfiguracion.getNumeroRIFComercio().split("-"));

            String marcaTarjeta = "";
            String numeroTarjeta = "";
            Long numeroLote = Long.valueOf(0);
            String numeroLoteFormateado;

            if (!transaccion.getTransaccionJSON().equals("{}")) {

                //Switch para conocer el tipo de objeto almacenado en referencia.
                switch (tipoTransaccion) {

                    //Si el objeto almacenado en referencia es una venta.
                    case "Venta":

                        //Convirtiendo el JSONString en un objeto tipo venta.
                        ultimaVenta = gson.fromJson(transaccion.getTransaccionJSON(), Venta.class);

                        marcaTarjeta = dbBines.obtenerMarcaTarjetaporNumeroTarjeta(ultimaVenta.getPAN().substring(0, 6));
                        numeroTarjeta = ultimaVenta.getPAN();

                        //
                        numeroLote = Long.valueOf(transaccion.getLote());
                        numeroLoteFormateado = String.format("%03d", numeroLote);

                        //Construccion del texto para ultima venta aprobada.
                        cadenaImpresion = perfilConfiguracion.getNombreDelAdquiriente() + " " + rifAdquirienteSinGuion
                                + "\n" +
                                perfilConfiguracion.getNombreDelComercio()
                                + "\n" +
                                "RIF:" + rifComercioSinGuion + " AFIL:" + perfilConfiguracion.getNumeroAfiliado();

                        if (transaccion.getTipoTarjeta().equals("Credito")) {
                            cadenaImpresion = cadenaImpresion + "\n" + "RECIBO DE COMPRA CREDITO" + "\n";
                        } else {
                            cadenaImpresion = cadenaImpresion + "\n" + "RECIBO DE COMPRA DEBITO" + "\n";
                        }

                        cadenaImpresion = cadenaImpresion + marcaTarjeta + " " + numeroTarjeta.replace(numeroTarjeta.substring(6, 12), "******")
                                + "\n" +
                                "T:" + perfilConfiguracion.getTerminalId().substring((numeroLetrasTerminal - 4), numeroLetrasTerminal) + " L:" + numeroLoteFormateado + " F:" + fechaFormateada.format(calendar.getTime()) + " H:" + horaFormateada.format(calendar.getTime())
                                + "\n" +
                                " "
                                + "\n" +
                                "           " + codigoError + " , " + descripcionError
                                + "\n" +
                                " "
                                + "\n" +
                                " ";
                }

            } else {

                cadenaImpresion = "El objeto almacenado en la transaccion no es valido."
                        + "\n" +
                        " "
                        + "\n" +
                        " ";
            }

        } else {

            cadenaImpresion = "No hay transacciones aprobadas en la tabla"
                        + "\n" +
                        " "
                        + "\n" +
                        " ";
        }

        return cadenaImpresion;
    }

    public static String obtenerFormatoUltimaAprobada(String tipoFormato) {

        String cadenaImpresion = "";
        Gson gson = new Gson();

        //Obteniendo los parametros de configuracion desde la tabla Perfil configuracion.
        perfilConfiguracion = dbPerfilConfiguracion.obtenerPerfilConfiguracionporId("1");

        //Obteniendo la ultima transaccion aprobada.
        transaccion = dbTransaccion.obtenerTransaccionPorNumeroReferenciaActual(Long.valueOf(perfilConfiguracion.getReferencia()));

        if (transaccion != null) {
            int numeroLetrasTerminal = perfilConfiguracion.getTerminalId().length();
            String rifAdquirienteSinGuion = TextUtils.join("", perfilConfiguracion.getNumeroRIFAdquiriente().split("-"));
            String rifComercioSinGuion = TextUtils.join("", perfilConfiguracion.getNumeroRIFComercio().split("-"));

            String marcaTarjeta = "";
            String numeroTarjeta = "";
            Long numeroLote = Long.valueOf(0);
            String numeroLoteFormateado;

            if (!transaccion.getTransaccionJSON().equals("{}")) {

                //Switch para conocer el tipo de objeto almacenado en referencia.
                switch (transaccion.getTipoTransaccion()) {

                    //Si el objeto almacenado en referencia es una venta.
                    case "Venta":

                        //Convirtiendo el JSONString en un objeto tipo venta.
                        ultimaVentaAprobada = gson.fromJson(transaccion.getTransaccionJSON(), Venta.class);

                        marcaTarjeta = dbBines.obtenerMarcaTarjetaporNumeroTarjeta(ultimaVentaAprobada.getPAN().substring(0, 6));
                        numeroTarjeta = ultimaVentaAprobada.getPAN();

                        //
                        numeroLote = Long.valueOf(transaccion.getLote());
                        numeroLoteFormateado = String.format("%03d", numeroLote);

                        //Construccion del texto para ultima venta aprobada.
                        cadenaImpresion = perfilConfiguracion.getNombreDelAdquiriente() + " " + rifAdquirienteSinGuion
                                + "\n" +
                                perfilConfiguracion.getNombreDelComercio()
                                + "\n" +
                                "RIF:" + rifComercioSinGuion + " AFIL:" + perfilConfiguracion.getNumeroAfiliado();

                        if (transaccion.getTipoTarjeta().equals("Credito")) {
                            cadenaImpresion = cadenaImpresion + "\n" + "RECIBO DE COMPRA CREDITO" + "\n";
                        } else {
                            cadenaImpresion = cadenaImpresion + "\n" + "RECIBO DE COMPRA DEBITO" + "\n";
                        }

                        cadenaImpresion = cadenaImpresion + marcaTarjeta + " " + numeroTarjeta.replace(numeroTarjeta.substring(6, 12), "******")
                                + "\n" +
                                "T:" + perfilConfiguracion.getTerminalId().substring((numeroLetrasTerminal - 4), numeroLetrasTerminal) + " L:" + numeroLoteFormateado + " F:" + fechaFormateada.format(calendar.getTime()) + " H:" + horaFormateada.format(calendar.getTime())
                                + "\n" +
                                "APRO:" + ultimaVentaAprobada.getCodigoAutorizacion() + " REF:" + String.valueOf(transaccion.getReferencia()) + " TRACE:" + String.valueOf(transaccion.getTrace())
                                + "\n" +
                                "MONTO A PAGAR " + perfilConfiguracion.getCurrencyCode() + " " + obtenerDecimalString(ultimaVentaAprobada.getMonto())
                                + "\n" +
                                " "
                                + "\n";
                        break;

                    //Si el objeto almacenado en referencia es una anulacion.
                    case "Anulacion":

                        //Convirtiendo el JSONString en un objeto tipo anulacion.
                        ultimaAnulacionAprobada = gson.fromJson(transaccion.getTransaccionJSON(), Anulacion.class);

                        marcaTarjeta = dbBines.obtenerMarcaTarjetaporNumeroTarjeta(ultimaAnulacionAprobada.getPAN().substring(0, 6));
                        numeroTarjeta = ultimaAnulacionAprobada.getPAN();

                        //
                        numeroLote = Long.valueOf(transaccion.getLote());
                        numeroLoteFormateado = String.format("%03d", numeroLote);

                        //Construccion del texto para ultima anulacion aprobada.
                        cadenaImpresion = perfilConfiguracion.getNombreDelAdquiriente() + " " + rifAdquirienteSinGuion
                                + "\n" +
                                perfilConfiguracion.getNombreDelComercio()
                                + "\n" +
                                "RIF:" + rifComercioSinGuion + " AFIL:" + perfilConfiguracion.getNumeroAfiliado();

                        if (transaccion.getTipoTarjeta().equals("Credito")) {
                            cadenaImpresion = cadenaImpresion + "\n" + "RECIBO DE ANULACION CREDITO" + "\n";
                        } else {
                            cadenaImpresion = cadenaImpresion + "\n" + "RECIBO DE ANULACION DEBITO" + "\n";
                        }

                        cadenaImpresion = cadenaImpresion + marcaTarjeta + " " + numeroTarjeta.replace(numeroTarjeta.substring(6, 12), "******")
                                + "\n" +
                                "T:" + perfilConfiguracion.getTerminalId().substring((numeroLetrasTerminal - 4), numeroLetrasTerminal) + " L:" + numeroLoteFormateado + " F:" + fechaFormateada.format(calendar.getTime()) + " H:" + horaFormateada.format(calendar.getTime())
                                + "\n" +
                                "APRO:" + ultimaAnulacionAprobada.getCodigoAutorizacion() + " REF:" + String.valueOf(transaccion.getReferencia()) + " TRACE:" + String.valueOf(transaccion.getTrace())
                                + "\n";

                        if(transaccion.getTipoTransaccion().equals("Venta")){
                            cadenaImpresion = cadenaImpresion + "MONTO A PAGAR ";
                        }else{
                            cadenaImpresion = cadenaImpresion + "MONTO ANULADO ";
                        }

                        cadenaImpresion = cadenaImpresion + perfilConfiguracion.getCurrencyCode() + " -" + obtenerDecimalString(ultimaAnulacionAprobada.getMonto())
                                + "\n" +
                                " "
                                + "\n";
                        break;
                }

                if (transaccion.getTipoTarjeta().equals("Credito")) {
                    cadenaImpresion = cadenaImpresion + "Firma: ________________________"
                            + "\n";

                    if (tipoFormato.equals("COPIA")) {
                        cadenaImpresion = cadenaImpresion + " " + "\n" + "***COPIA***" + "\n" + " ";
                    }

                    cadenaImpresion = cadenaImpresion
                            + "\n" +
                            "ME OBLIGO A PAGAR AL BANCO"
                            + "\n" +
                            "EMISOR DE LA TARJETA EL MONTO"
                            + "\n" +
                            "DE ESTA NOTA DE CONSUMO"
                            + "\n" +
                            " "
                            + "\n" +
                            " ";
                } else {
                    cadenaImpresion = cadenaImpresion + " ";
                }

            } else {

                cadenaImpresion = "El objeto almacenado en la transaccion no es valido."
                        + "\n" +
                        " "
                        + "\n" +
                        " ";
            }

        } else {

            cadenaImpresion = "No hay transacciones aprobadas en la tabla"
                    + "\n" +
                    " "
                    + "\n" +
                    " ";

        }

        return cadenaImpresion;
    }

    public static String obtenerFormatoResumen(String tipoTarjeta) {

        String cadenaImpresion = "";
        String listaVentas = "";
        String listaAnulaciones = "";
        String totalesVentaPorMarcaTarjeta = "";
        String totalesAnulacionPorMarcaTarjeta = "";

        int numeroLote = 0;
        if(tipoTarjeta.equals("Debito")){
            numeroLote = Integer.parseInt(perfilConfiguracion.getLoteDebito());
        }else{
            numeroLote = Integer.parseInt(perfilConfiguracion.getLoteCredito());
        }

        transaccionesLista = dbTransaccion.obtenerTransaccionesPorTipoTarjetayNumeroLoteActual(tipoTarjeta, Long.valueOf(numeroLote));

        Bundle totalesDebito = calcularTotales("Debito", transaccionesLista);

        int[] cantidadesVenta = totalesDebito.getIntArray("CANTIDADES_VENTA");
        int[] totalesVenta = totalesDebito.getIntArray("TOTALES_VENTA");
        int[] cantidadesAnulacion = totalesDebito.getIntArray("CANTIDADES_ANULACION");
        int[] totalesAnulacion = totalesDebito.getIntArray("TOTALES_ANULACION");

        int numeroVentasDebito = cantidadesVenta[TOTAL];
        int totalVentasDebito = totalesVenta[TOTAL];

        int numeroAnulacionesDebito = cantidadesAnulacion[TOTAL];
        int totalAnulacionesDebito = totalesAnulacion[TOTAL];

        int numeroTransaccionesLote = numeroVentasDebito + numeroAnulacionesDebito;
        int totalTransaccionesLote = totalVentasDebito - totalAnulacionesDebito;

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        int iReferenciaVenta = 0;
        int iMarcaTarjetaVenta = 0;
        int iReferenciaAnulacion = 0;
        int iMarcaTarjetaAnulacion = 0;
        int numeroLetrasTerminal = perfilConfiguracion.getTerminalId().length();
        String numeroLoteFormateado = String.format("%03d", numeroLote);
        String rifAdquirienteSinGuion = TextUtils.join("", perfilConfiguracion.getNumeroRIFAdquiriente().split("-"));
        String rifComercioSinGuion = TextUtils.join("", perfilConfiguracion.getNumeroRIFComercio().split("-"));

        cadenaImpresion = perfilConfiguracion.getNombreDelAdquiriente() + " " + rifAdquirienteSinGuion + "\n";

        if(tipoTarjeta.equals("Debito")){
            cadenaImpresion = cadenaImpresion + "RESUMEN DEBITO" + "\n";
        }else{
            cadenaImpresion = cadenaImpresion + "RESUMEN CREDITO" + "\n";
        }

        cadenaImpresion = cadenaImpresion + perfilConfiguracion.getNombreDelComercio()
                + "\n" +
                "RIF:" + rifComercioSinGuion + " AFIL:" + perfilConfiguracion.getNumeroAfiliado()
                + "\n" +
                "T:" + perfilConfiguracion.getTerminalId().substring((numeroLetrasTerminal - 4), numeroLetrasTerminal) + " L:" + numeroLoteFormateado + " F:" + fechaFormateada.format(calendar.getTime()) + " H:" + horaFormateada.format(calendar.getTime())
                + "\n" +
                " "
                + "\n";

        for (Venta ventaI : ventasLista) {
            String numeroTarjeta = ventaI.getPAN();
            listaVentas = listaVentas + numeroTarjeta.replace(numeroTarjeta.substring(6, 12), "******") + " Ref: " + referenciaVentaLista.get(iReferenciaVenta) + "\n" +
                    marcaTarjetaVentaLista.get(iMarcaTarjetaVenta) + " " + perfilConfiguracion.getCurrencyCode() + " " + obtenerDecimalString(ventaI.getMonto()) + "\n";
            iReferenciaVenta = iReferenciaVenta + 1;
            iMarcaTarjetaVenta = iMarcaTarjetaVenta + 1;
        }

        cadenaImpresion = cadenaImpresion + listaVentas;

        cadenaImpresion = cadenaImpresion + " " + "\n" + "COMPRAS" + "\n";

        int iMarcaTarjeta = 0;

        for(String marcaTarjeta : marcasTarjeta){
            if (cantidadesVenta[iMarcaTarjeta] != 0) {
                totalesVentaPorMarcaTarjeta = totalesVentaPorMarcaTarjeta + marcaTarjeta + " " + cantidadesVenta[iMarcaTarjeta] + " " + perfilConfiguracion.getCurrencyCode() + " " + obtenerDecimalString(String.valueOf(totalesVenta[iMarcaTarjeta])) + "\n";
            }
            iMarcaTarjeta = iMarcaTarjeta + 1;
        }

        cadenaImpresion = cadenaImpresion + totalesVentaPorMarcaTarjeta;

        cadenaImpresion = cadenaImpresion + "Cantidad: " + numeroVentasDebito + " " + perfilConfiguracion.getCurrencyCode() + " " + obtenerDecimalString(String.valueOf(totalVentasDebito)) + "\n" + "\n";

        for (Anulacion anulacionI : anulacionesLista) {
            String numeroTarjeta = anulacionI.getPAN();
            listaAnulaciones = listaAnulaciones + numeroTarjeta.replace(numeroTarjeta.substring(6, 12), "******") + " Ref: " + referenciaAnulacionLista.get(iReferenciaAnulacion) + "\n" +
                    marcaTarjetaAnulacionLista.get(iMarcaTarjetaAnulacion) + " " + perfilConfiguracion.getCurrencyCode() + " -" + obtenerDecimalString(anulacionI.getMonto()) + "\n";
            iReferenciaAnulacion = iReferenciaAnulacion + 1;
            iMarcaTarjetaAnulacion = iMarcaTarjetaAnulacion + 1;
        }

        cadenaImpresion = cadenaImpresion + listaAnulaciones;

        cadenaImpresion = cadenaImpresion + "\n" + "ANULACIONES" + "\n";

        iMarcaTarjeta = 0;

        for (String marcaTarjeta : marcasTarjeta) {
            if (cantidadesAnulacion[iMarcaTarjeta] != 0) {
                totalesAnulacionPorMarcaTarjeta = totalesAnulacionPorMarcaTarjeta + marcaTarjeta + " " + cantidadesAnulacion[iMarcaTarjeta] + " " + perfilConfiguracion.getCurrencyCode() + " -" + obtenerDecimalString(String.valueOf(totalesAnulacion[iMarcaTarjeta])) + "\n";
            }
            iMarcaTarjeta = iMarcaTarjeta + 1;
        }

        cadenaImpresion = cadenaImpresion + totalesAnulacionPorMarcaTarjeta;

        cadenaImpresion = cadenaImpresion + "Cantidad: " + numeroAnulacionesDebito + " " + perfilConfiguracion.getCurrencyCode() + " -" + obtenerDecimalString(String.valueOf(totalAnulacionesDebito))
                + "\n" +
                " "
                + "\n" +
                "MONTO LOTE"
                + "\n" +
                "MONTOS LOTE" + " " + numeroTransaccionesLote + " " + perfilConfiguracion.getCurrencyCode() + " " + obtenerDecimalString(String.valueOf(totalTransaccionesLote))
                + "\n" +
                " "
                + "\n" +
                " ";

        return cadenaImpresion;
    }

    public static String obtenerFormatoCierre(String tipoTarjeta) {

        String cadenaImpresion = "";
        String listaVentas = "";
        String listaAnulaciones = "";
        String totalesVentaPorMarcaTarjeta = "";
        String totalesPorMarcaTarjeta = "";
        String totalesAnulacionPorMarcaTarjeta = "";

        int numeroLote = 0;
        if(tipoTarjeta.equals("Debito")){
            numeroLote = Integer.parseInt(perfilConfiguracion.getLoteDebito());
        }else{
            numeroLote = Integer.parseInt(perfilConfiguracion.getLoteCredito());
        }

        transaccionesLista = dbTransaccion.obtenerTransaccionesPorTipoTarjetayNumeroLoteActual(tipoTarjeta, Long.valueOf(numeroLote));

        Bundle totalesDebito = calcularTotales("Debito", transaccionesLista);

        int[] cantidadesVenta = totalesDebito.getIntArray("CANTIDADES_VENTA");
        int[] totalesVenta = totalesDebito.getIntArray("TOTALES_VENTA");
        int[] cantidadesAnulacion = totalesDebito.getIntArray("CANTIDADES_ANULACION");
        int[] totalesAnulacion = totalesDebito.getIntArray("TOTALES_ANULACION");

        int numeroVentasDebito = cantidadesVenta[TOTAL];
        int totalVentasDebito = totalesVenta[TOTAL];

        int numeroAnulacionesDebito = cantidadesAnulacion[TOTAL];
        int totalAnulacionesDebito = totalesAnulacion[TOTAL];

        int numeroTransaccionesLote = numeroVentasDebito + numeroAnulacionesDebito;
        int totalTransaccionesLote = totalVentasDebito - totalAnulacionesDebito;

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        int iReferenciaVenta = 0;
        int iMarcaTarjetaVenta = 0;
        int iReferenciaAnulacion = 0;
        int iMarcaTarjetaAnulacion = 0;
        int numeroLetrasTerminal = perfilConfiguracion.getTerminalId().length();
        String numeroLoteFormateado = String.format("%03d", numeroLote);
        String rifAdquirienteSinGuion = TextUtils.join("", perfilConfiguracion.getNumeroRIFAdquiriente().split("-"));
        String rifComercioSinGuion = TextUtils.join("", perfilConfiguracion.getNumeroRIFComercio().split("-"));

        cadenaImpresion = perfilConfiguracion.getNombreDelAdquiriente() + " " + rifAdquirienteSinGuion + "\n";

        if(tipoTarjeta.equals("Debito")){
            cadenaImpresion = cadenaImpresion + "CIERRE DEBITO" + "\n";
        }else{
            cadenaImpresion = cadenaImpresion + "CIERRE CREDITO" + "\n";
        }

        cadenaImpresion = cadenaImpresion + perfilConfiguracion.getNombreDelComercio()
                + "\n" +
                "RIF:" + rifComercioSinGuion + " AFIL:" + perfilConfiguracion.getNumeroAfiliado()
                + "\n" +
                "T:" + perfilConfiguracion.getTerminalId().substring((numeroLetrasTerminal - 4), numeroLetrasTerminal) + " L:" + numeroLoteFormateado + " F:" + fechaFormateada.format(calendar.getTime()) + " H:" + horaFormateada.format(calendar.getTime())
                + "\n" +
                " "
                + "\n" +
                "APROBADO"
                + "\n" +
                " ";

        cadenaImpresion = cadenaImpresion + " " + "\n" + "COMPRAS" + "\n";

        int iMarcaTarjeta = 0;

        for(String marcaTarjeta : marcasTarjeta){
            if (cantidadesVenta[iMarcaTarjeta] != 0) {
                totalesVentaPorMarcaTarjeta = totalesVentaPorMarcaTarjeta + marcaTarjeta + " " + cantidadesVenta[iMarcaTarjeta] + " " + perfilConfiguracion.getCurrencyCode() + " " + obtenerDecimalString(String.valueOf(totalesVenta[iMarcaTarjeta])) + "\n";
            }
            iMarcaTarjeta = iMarcaTarjeta + 1;
        }

        cadenaImpresion = cadenaImpresion + totalesVentaPorMarcaTarjeta;

        cadenaImpresion = cadenaImpresion + "TOTAL " + numeroVentasDebito + " " + perfilConfiguracion.getCurrencyCode() + " " + obtenerDecimalString(String.valueOf(totalVentasDebito)) + "\n";

        cadenaImpresion = cadenaImpresion + "\n" + "ANULACIONES" + "\n";

        iMarcaTarjeta = 0;

        for (String marcaTarjeta : marcasTarjeta) {
            if (cantidadesAnulacion[iMarcaTarjeta] != 0) {
                totalesAnulacionPorMarcaTarjeta = totalesAnulacionPorMarcaTarjeta + marcaTarjeta + " " + cantidadesAnulacion[iMarcaTarjeta] + " " + perfilConfiguracion.getCurrencyCode() + " -" + obtenerDecimalString(String.valueOf(totalesAnulacion[iMarcaTarjeta])) + "\n";
            }
            iMarcaTarjeta = iMarcaTarjeta + 1;
        }

        cadenaImpresion = cadenaImpresion + totalesAnulacionPorMarcaTarjeta;

        cadenaImpresion = cadenaImpresion + "TOTAL " + numeroAnulacionesDebito + " " + perfilConfiguracion.getCurrencyCode() + " -" + obtenerDecimalString(String.valueOf(totalAnulacionesDebito)) + "\n";

        cadenaImpresion = cadenaImpresion + "\n" + "TOTALES LOTE" + "\n";

        iMarcaTarjeta = 0;

        for(String marcaTarjeta : marcasTarjeta){
            if (cantidadesVenta[iMarcaTarjeta] != 0) {
                totalesPorMarcaTarjeta = totalesPorMarcaTarjeta + marcaTarjeta + " " + (cantidadesVenta[iMarcaTarjeta] + cantidadesAnulacion[iMarcaTarjeta]) + " " + perfilConfiguracion.getCurrencyCode() + " " + obtenerDecimalString(String.valueOf(totalesVenta[iMarcaTarjeta] - totalesAnulacion[iMarcaTarjeta])) + "\n" +
                marcaTarjeta + " LIQ " + perfilConfiguracion.getCurrencyCode() + " " + obtenerDecimalString(String.valueOf(totalesVenta[iMarcaTarjeta] - totalesAnulacion[iMarcaTarjeta])) + "\n";
            }
            iMarcaTarjeta = iMarcaTarjeta + 1;
        }

        cadenaImpresion = cadenaImpresion + totalesPorMarcaTarjeta + "\n";

        cadenaImpresion = cadenaImpresion + "TOTAL LOTE" + " " + numeroTransaccionesLote + " " + perfilConfiguracion.getCurrencyCode() + obtenerDecimalString(String.valueOf(totalTransaccionesLote))
                + "\n" +
                "TOTAL LIQ " + perfilConfiguracion.getCurrencyCode() + " " + obtenerDecimalString(String.valueOf(totalTransaccionesLote))
                + "\n" +
                " "
                + "\n" +
                " ";

        return cadenaImpresion;
    }

    public static Bundle calcularTotales(String tipoTarjeta, ArrayList<Transaccion> transaccionesListaEnviada) {

        Gson gson = new Gson();

        Bundle bundleTotales = new Bundle();

        //Obteniendo las transacciones por numero de lote.
        transaccionesLista = new ArrayList<Transaccion>();
        ventasLista = new ArrayList<Venta>();
        anulacionesLista = new ArrayList<Anulacion>();
        marcaTarjetaVentaLista = new ArrayList<String>();
        marcaTarjetaAnulacionLista = new ArrayList<String>();
        referenciaVentaLista = new ArrayList<String>();
        referenciaAnulacionLista = new ArrayList<String>();

        if(transaccionesListaEnviada != null){
            transaccionesLista = transaccionesListaEnviada;
        }else{
            String numeroLote = "";
            if(tipoTarjeta.equals("Debito")){
                numeroLote = perfilConfiguracion.getLoteDebito();
            }else{
                numeroLote = perfilConfiguracion.getLoteCredito();
            }
            transaccionesLista = dbTransaccion.obtenerTransaccionesPorTipoTarjetaNumeroLoteActualyEstado(tipoTarjeta, Long.valueOf(numeroLote),4);
        }

        int numeroObjetosErroneos = 0;
        boolean existenErroneos = false;

        for (Transaccion t : transaccionesLista) {
            if (!t.getTransaccionJSON().equals("{}")) {
                switch (t.getTipoTransaccion()) {
                    case "Venta":
                        venta = gson.fromJson(t.getTransaccionJSON(), Venta.class);
                        ventasLista.add(venta);
                        marcaTarjetaVentaLista.add(dbBines.obtenerMarcaTarjetaporNumeroTarjeta(venta.getPAN().substring(0, 6)));
                        referenciaVentaLista.add(String.valueOf(t.getReferencia()));
                        break;
                    case "Anulacion":
                        anulacion = gson.fromJson(t.getTransaccionJSON(), Anulacion.class);
                        anulacionesLista.add(anulacion);
                        marcaTarjetaAnulacionLista.add(dbBines.obtenerMarcaTarjetaporNumeroTarjeta(anulacion.getPAN().substring(0, 6)));
                        referenciaAnulacionLista.add(String.valueOf(t.getReferencia()));
                        break;
                }
            } else {
                numeroObjetosErroneos = numeroObjetosErroneos + 1;
                existenErroneos = true;
            }
        }

        if (existenErroneos) {
            //LogInterno.i(TAG, "En la tabla transacciones existen: " + numeroObjetosErroneos + " objetos erroneos.");
        }

        int[] cantidadesVenta = new int[]{0, 0, 0, 0, 0, 0};
        int[] totalesVenta = new int[]{0, 0, 0, 0, 0, 0};
        int[] cantidadesAnulacion = new int[]{0, 0, 0, 0, 0, 0};
        int[] totalesAnulacion = new int[]{0, 0, 0, 0, 0, 0};

        int iMarcaTarjeta = 0;

        for (Venta venta : ventasLista) {
            iMarcaTarjeta = 0;
            String marcaTarjetaVenta = dbBines.obtenerMarcaTarjetaporNumeroTarjeta(venta.getPAN());
            for (String marcaTarjeta : marcasTarjeta) {
                if (marcaTarjetaVenta.equals(marcaTarjeta)) {
                    cantidadesVenta[iMarcaTarjeta] = cantidadesVenta[iMarcaTarjeta] + 1;
                    totalesVenta[iMarcaTarjeta] = totalesVenta[iMarcaTarjeta] + Integer.parseInt(venta.getMonto());
                }
                iMarcaTarjeta = iMarcaTarjeta + 1;
            }
        }

        iMarcaTarjeta = 0;

        for (Anulacion anulacion : anulacionesLista) {
            iMarcaTarjeta = 0;
            String marcaTarjetaAnulacion = dbBines.obtenerMarcaTarjetaporNumeroTarjeta(anulacion.getPAN());
            for (String marcaTarjeta : marcasTarjeta) {
                if (marcaTarjetaAnulacion.equals(marcaTarjeta)) {
                    cantidadesAnulacion[iMarcaTarjeta] = cantidadesAnulacion[iMarcaTarjeta] + 1;
                    totalesAnulacion[iMarcaTarjeta] = totalesAnulacion[iMarcaTarjeta] + Integer.parseInt(anulacion.getMonto());
                }
                iMarcaTarjeta = iMarcaTarjeta + 1;
            }
        }

        iMarcaTarjeta = 0;

        cantidadesVenta[TOTAL] = cantidadesVenta[VISA] + cantidadesVenta[MASTER_CARD] + cantidadesVenta[MAESTRO] + cantidadesVenta[AMEX] + cantidadesVenta[PRIVADA];
        totalesVenta[TOTAL] = totalesVenta[VISA] + totalesVenta[MASTER_CARD] + totalesVenta[MAESTRO] + totalesVenta[AMEX] + totalesVenta[PRIVADA];
        cantidadesAnulacion[TOTAL] = cantidadesAnulacion[VISA] + cantidadesAnulacion[MASTER_CARD] + cantidadesAnulacion[MAESTRO] + cantidadesAnulacion[AMEX] + cantidadesAnulacion[PRIVADA];
        totalesAnulacion[TOTAL] = totalesAnulacion[VISA] + totalesAnulacion[MASTER_CARD] + totalesAnulacion[MAESTRO] + totalesAnulacion[AMEX] + totalesAnulacion[PRIVADA];

        bundleTotales.putIntArray("CANTIDADES_VENTA", cantidadesVenta);
        bundleTotales.putIntArray("TOTALES_VENTA", totalesVenta);
        bundleTotales.putIntArray("CANTIDADES_ANULACION", cantidadesAnulacion);
        bundleTotales.putIntArray("TOTALES_ANULACION", totalesAnulacion);

        return bundleTotales;
    }

    public static String obtenerDecimalString (String numeroEntero){
        return df.format(Double.parseDouble(numeroEntero) / 100);
    }
}

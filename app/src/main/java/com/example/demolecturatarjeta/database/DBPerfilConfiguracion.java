package com.example.demolecturatarjeta.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demolecturatarjeta.database.object.PerfilConfiguracion;

public class DBPerfilConfiguracion {

    private BaseDeDatos db = null;

    public DBPerfilConfiguracion(BaseDeDatos db) {
        this.db = db;
    }

    //Columnas de la Tabla
    public static final String TABLA_PERFIL_CONFIGURACION = "perfil_configuracion";
    public static final String COLUMNA_ID = "id";
    public static final String COLUMNA_CLAVE = "clave";
    public static final String COLUMNA_NOMBRE_DEL_ADQUIRIENTE = "nombre_del_adquiriente";
    public static final String COLUMNA_LOGO_DEL_ADQUIRIENTE = "logo_del_adquiriente";
    public static final String COLUMNA_CODIGO_DE_ADQUIRIENTE = "codigo_de_adquiriente";
    public static final String COLUMNA_TIPO_RIF_DEL_ADQUIRIENTE = "tipo_rif_adquiriente";
    public static final String COLUMNA_NUMERO_RIF_DEL_ADQUIRIENTE = "numero_rif_adquiriente";
    public static final String COLUMNA_NOMBRE_DEL_COMERCIO = "nombre_del_comercio";
    public static final String COLUMNA_DIRECCION_DEL_COMERCIO = "direccion_del_comercio";
    public static final String COLUMNA_NUMERO_AFILIADO = "numero_afiliado";
    public static final String COLUMNA_VERSION_APLICACION = "version_aplicacion";
    public static final String COLUMNA_TIPO_RIF_DEL_COMERCIO = "tipo_rif_comercio";
    public static final String COLUMNA_NUMERO_RIF_DEL_COMERCIO = "numero_rif_comercio";
    public static final String COLUMNA_DIRECCION_IP = "direccion_ip";
    public static final String COLUMNA_PUERTO = "puerto";
    public static final String COLUMNA_TERMINAL_ID = "terminal_id";
    public static final String COLUMNA_CURRENCY_CODE = "currency_code";
    public static final String COLUMNA_COUNTRY_CODE = "country_code";
    public static final String COLUMNA_POS_SERIAL_NUMBER = "pos_serial_number";
    public static final String COLUMNA_TRACE = "trace";
    public static final String COLUMNA_REFERENCIA = "referencia";
    public static final String COLUMNA_LOTE_CREDITO = "lote_credito";
    public static final String COLUMNA_LOTE_DEBITO = "lote_debito";

    //Comando SQL de creación de la tabla
    protected static final String SQL_CREAR_PERFIL_CONFIGURACION = "create table "
            + TABLA_PERFIL_CONFIGURACION + "(" + COLUMNA_ID
            + " text not null, " + COLUMNA_CLAVE
            + " text not null, " + COLUMNA_NOMBRE_DEL_ADQUIRIENTE
            + " text not null, " + COLUMNA_LOGO_DEL_ADQUIRIENTE
            + " text not null, " + COLUMNA_CODIGO_DE_ADQUIRIENTE
            + " text not null, " + COLUMNA_TIPO_RIF_DEL_ADQUIRIENTE
            + " text not null, " + COLUMNA_NUMERO_RIF_DEL_ADQUIRIENTE
            + " text not null, " + COLUMNA_NOMBRE_DEL_COMERCIO
            + " text not null, " + COLUMNA_DIRECCION_DEL_COMERCIO
            + " text not null, " + COLUMNA_NUMERO_AFILIADO
            + " text not null, " + COLUMNA_VERSION_APLICACION
            + " text not null, " + COLUMNA_TIPO_RIF_DEL_COMERCIO
            + " text not null, " + COLUMNA_NUMERO_RIF_DEL_COMERCIO
            + " text not null, " + COLUMNA_DIRECCION_IP
            + " text not null, " + COLUMNA_PUERTO
            + " text not null, " + COLUMNA_TERMINAL_ID
            + " text not null, " + COLUMNA_CURRENCY_CODE
            + " text not null, " + COLUMNA_COUNTRY_CODE
            + " text not null, " + COLUMNA_POS_SERIAL_NUMBER
            + " text not null, " + COLUMNA_TRACE
            + " text not null, " + COLUMNA_REFERENCIA
            + " text not null, " + COLUMNA_LOTE_CREDITO
            + " text not null, " + COLUMNA_LOTE_DEBITO
            + " text not null);";

    public void agregarPerfilConfiguracion(PerfilConfiguracion perfilConfiguracion){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_ID, perfilConfiguracion.getId());
        values.put(COLUMNA_CLAVE, perfilConfiguracion.getClave());
        values.put(COLUMNA_NOMBRE_DEL_ADQUIRIENTE, perfilConfiguracion.getNombreDelAdquiriente());
        values.put(COLUMNA_LOGO_DEL_ADQUIRIENTE, perfilConfiguracion.getLogoDelAdquiriente());
        values.put(COLUMNA_CODIGO_DE_ADQUIRIENTE, perfilConfiguracion.getCodigoDeAdquiriente());;
        values.put(COLUMNA_TIPO_RIF_DEL_ADQUIRIENTE, perfilConfiguracion.getTipoRIFAdquiriente());
        values.put(COLUMNA_NUMERO_RIF_DEL_ADQUIRIENTE, perfilConfiguracion.getNumeroRIFAdquiriente());
        values.put(COLUMNA_NOMBRE_DEL_COMERCIO, perfilConfiguracion.getNombreDelComercio());
        values.put(COLUMNA_DIRECCION_DEL_COMERCIO, perfilConfiguracion.getDireccionDelComercio());
        values.put(COLUMNA_NUMERO_AFILIADO, perfilConfiguracion.getNumeroAfiliado());
        values.put(COLUMNA_VERSION_APLICACION, perfilConfiguracion.getVersionAplicacion());
        values.put(COLUMNA_TIPO_RIF_DEL_COMERCIO, perfilConfiguracion.getTipoRIFComercio());
        values.put(COLUMNA_NUMERO_RIF_DEL_COMERCIO, perfilConfiguracion.getNumeroRIFComercio());
        values.put(COLUMNA_DIRECCION_IP, perfilConfiguracion.getDireccionIp());
        values.put(COLUMNA_PUERTO, perfilConfiguracion.getPuerto());
        values.put(COLUMNA_TERMINAL_ID, perfilConfiguracion.getTerminalId());
        values.put(COLUMNA_CURRENCY_CODE, perfilConfiguracion.getCurrencyCode());
        values.put(COLUMNA_COUNTRY_CODE, perfilConfiguracion.getCountryCode());
        values.put(COLUMNA_POS_SERIAL_NUMBER, perfilConfiguracion.getPosSerialNumber());
        values.put(COLUMNA_TRACE, perfilConfiguracion.getTrace());
        values.put(COLUMNA_REFERENCIA, perfilConfiguracion.getReferencia());
        values.put(COLUMNA_LOTE_CREDITO, perfilConfiguracion.getLoteCredito());
        values.put(COLUMNA_LOTE_DEBITO, perfilConfiguracion.getLoteDebito());

        db.insert(TABLA_PERFIL_CONFIGURACION, null,values);
        db.close();
    }

    public PerfilConfiguracion obtenerPerfilConfiguracionporId(String id){

        SQLiteDatabase db = this.db.getWritableDatabase();
        PerfilConfiguracion perfilConfiguracion = null;

        String[] campos = {COLUMNA_CLAVE,
                           COLUMNA_NOMBRE_DEL_ADQUIRIENTE,
                           COLUMNA_LOGO_DEL_ADQUIRIENTE,
                           COLUMNA_CODIGO_DE_ADQUIRIENTE,
                           COLUMNA_TIPO_RIF_DEL_ADQUIRIENTE,
                           COLUMNA_NUMERO_RIF_DEL_ADQUIRIENTE,
                           COLUMNA_NOMBRE_DEL_COMERCIO,
                           COLUMNA_DIRECCION_DEL_COMERCIO,
                           COLUMNA_NUMERO_AFILIADO,
                           COLUMNA_VERSION_APLICACION,
                           COLUMNA_TIPO_RIF_DEL_COMERCIO,
                           COLUMNA_NUMERO_RIF_DEL_COMERCIO,
                           COLUMNA_DIRECCION_IP,
                           COLUMNA_PUERTO,
                           COLUMNA_TERMINAL_ID,
                           COLUMNA_CURRENCY_CODE,
                           COLUMNA_COUNTRY_CODE,
                           COLUMNA_POS_SERIAL_NUMBER,
                           COLUMNA_TRACE,
                           COLUMNA_REFERENCIA,
                           COLUMNA_LOTE_CREDITO,
                           COLUMNA_LOTE_DEBITO};

        Cursor c = db.query(TABLA_PERFIL_CONFIGURACION, campos, "id = " + "'"+id+"'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                perfilConfiguracion = new PerfilConfiguracion("1",c.getString(0),
                                                                      c.getString(1),
                                                                      c.getString(2),
                                                                      c.getString(3),
                                                                      c.getString(4),
                                                                      c.getString(5),
                                                                      c.getString(6),
                                                                      c.getString(7),
                                                                      c.getString(8),
                                                                      c.getString(9),
                                                                      c.getString(10),
                                                                      c.getString(11),
                                                                      c.getString(12),
                                                                      c.getString(13),
                                                                      c.getString(14),
                                                                      c.getString(15),
                                                                      c.getString(16),
                                                                      c.getString(17),
                                                                      c.getString(18),
                                                                      c.getString(19),
                                                                      c.getString(20),
                                                                      c.getString(21));
            } while(c.moveToNext());
        }

        return perfilConfiguracion;
    }

    public void updateClaveporId(String id, String clave){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_CLAVE,clave);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateNombreDelAdquirienteporId(String id, String nombreDelAdquiriente){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_NOMBRE_DEL_ADQUIRIENTE,nombreDelAdquiriente);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateLogoDelAdquirienteporId(String id, String logoDelAdquiriente){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_LOGO_DEL_ADQUIRIENTE,logoDelAdquiriente);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateCodigoDeAdquirienteporId(String id, String codigoDeAdquiriente){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_CODIGO_DE_ADQUIRIENTE,codigoDeAdquiriente);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateTipoRIFDelAdquirienteporId(String id, String codigoDeAdquiriente){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_TIPO_RIF_DEL_ADQUIRIENTE,codigoDeAdquiriente);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateNumeroRIFDelAdquirienteporId(String id, String codigoDeAdquiriente){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_NUMERO_RIF_DEL_ADQUIRIENTE,codigoDeAdquiriente);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateNombreDelComercioporId(String id, String nombreDelComercio){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_NOMBRE_DEL_COMERCIO,nombreDelComercio);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateDireccionDelComercioporId(String id, String codigoDeAdquiriente){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_DIRECCION_DEL_COMERCIO,codigoDeAdquiriente);

        db.update(COLUMNA_DIRECCION_DEL_COMERCIO, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateNumeroAfiliadoporId(String id, String numeroAfiliado){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_NUMERO_AFILIADO,numeroAfiliado);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateVersionAplicacionporId(String id, String versionAplicacion){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_VERSION_APLICACION,versionAplicacion);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateTipoRIFDelComercioporId(String id, String codigoDeAdquiriente){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_TIPO_RIF_DEL_COMERCIO,codigoDeAdquiriente);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateNumeroRIFDelComercioporId(String id, String codigoDeAdquiriente){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_NUMERO_RIF_DEL_COMERCIO,codigoDeAdquiriente);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateDireccionIPporId(String id, String direccionIP){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_DIRECCION_IP,direccionIP);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updatePuertoporId(String id, String puerto){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_PUERTO,puerto);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateCurrencyCodeporId(String id, String puerto){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_CURRENCY_CODE,puerto);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateCountryCodeporId(String id, String puerto){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_COUNTRY_CODE,puerto);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateTerminalIdporId(String id, String terminalId){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_TERMINAL_ID,terminalId);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updatePosSerialNumberporId(String id, String terminalId){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_POS_SERIAL_NUMBER,terminalId);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateTraceporId(String id, String trace){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_TRACE,trace);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateReferenciaporId(String id, String referencia){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_REFERENCIA,referencia);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateLoteCreditoporId(String id, String loteCredito){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_LOTE_CREDITO,loteCredito);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public void updateLoteDebitoporId(String id, String loteDebito){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_LOTE_DEBITO,loteDebito);

        db.update(TABLA_PERFIL_CONFIGURACION, values, "id = " + "'"+id+"'",null);
        db.close();
    }

    public int leer(){

        int numeroRegistros = 0;

        SQLiteDatabase db = this.db.getReadableDatabase();

        String[] campos = {COLUMNA_CLAVE,
                           COLUMNA_NOMBRE_DEL_ADQUIRIENTE,
                           COLUMNA_LOGO_DEL_ADQUIRIENTE,
                           COLUMNA_CODIGO_DE_ADQUIRIENTE,
                           COLUMNA_TIPO_RIF_DEL_ADQUIRIENTE,
                           COLUMNA_NUMERO_RIF_DEL_ADQUIRIENTE,
                           COLUMNA_NOMBRE_DEL_COMERCIO,
                           COLUMNA_DIRECCION_DEL_COMERCIO,
                           COLUMNA_NUMERO_AFILIADO,
                           COLUMNA_VERSION_APLICACION,
                           COLUMNA_TIPO_RIF_DEL_COMERCIO,
                           COLUMNA_NUMERO_RIF_DEL_COMERCIO,
                           COLUMNA_DIRECCION_IP,
                           COLUMNA_PUERTO,
                           COLUMNA_TERMINAL_ID,
                           COLUMNA_CURRENCY_CODE,
                           COLUMNA_COUNTRY_CODE,
                           COLUMNA_POS_SERIAL_NUMBER,
                           COLUMNA_TRACE,
                           COLUMNA_REFERENCIA,
                           COLUMNA_LOTE_CREDITO,
                           COLUMNA_LOTE_DEBITO};

        Cursor c = db.query(TABLA_PERFIL_CONFIGURACION, campos, null, null, null, null, null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String codigo= c.getString(0);
                numeroRegistros = numeroRegistros + 1;
            } while(c.moveToNext());
        }

        return numeroRegistros;
    }
}

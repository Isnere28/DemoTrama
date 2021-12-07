package com.example.demolecturatarjeta.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demolecturatarjeta.database.object.Transaccion;

import java.util.ArrayList;

public class DBTransaccion {

    private BaseDeDatos db = null;

    //Columnas de la Tabla
    public static final String TABLA_TRANSACCIONES = "transaccion";
    public static final String COLUMNA_CODIGO = "codigo";
    public static final String COLUMNA_TRACE= "trace";
    public static final String COLUMNA_REFERENCIA = "referencia";
    public static final String COLUMNA_TIPO_TARJETA = "tipo_tarjeta";
    public static final String COLUMNA_TIPO_CUENTA = "tipo_cuenta";
    public static final String COLUMNA_LOTE = "lote";
    public static final String COLUMNA_TIPO_TRANSACCION = "tipo_transaccion";
    public static final String COLUMNA_TRANSACCION_JSON = "transaccion_json";
    public static final String COLUMNA_ESTADO = "estado";

    protected static final String SQL_CREAR_TRANSACCION = "create table "
            + TABLA_TRANSACCIONES + "(" + COLUMNA_CODIGO
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMNA_TRACE
            + " long not null," + COLUMNA_REFERENCIA
            + " long null," + COLUMNA_TIPO_TARJETA
            + " text null," + COLUMNA_TIPO_CUENTA
            + " text null," + COLUMNA_LOTE
            + " long null," + COLUMNA_TIPO_TRANSACCION
            + " text not null," + COLUMNA_TRANSACCION_JSON
            + " text not null," + COLUMNA_ESTADO
            + " integer null);";

    public DBTransaccion(BaseDeDatos db) {
        this.db = db;
    }

    public String agregarTransaccion(Transaccion transaccion) {

        String codigo = "";

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(COLUMNA_CODIGO, trace.getCodigo());
        values.put(COLUMNA_TRACE, transaccion.getTrace());
        values.put(COLUMNA_REFERENCIA, transaccion.getReferencia());
        values.put(COLUMNA_TIPO_TARJETA, transaccion.getTipoTarjeta());
        values.put(COLUMNA_TIPO_CUENTA, transaccion.getTipoCuenta());
        values.put(COLUMNA_LOTE, transaccion.getLote());
        values.put(COLUMNA_TIPO_TRANSACCION, transaccion.getTipoTransaccion());
        values.put(COLUMNA_TRANSACCION_JSON, transaccion.getTransaccionJSON());
        values.put(COLUMNA_ESTADO, transaccion.getEstado());

        db.insert(TABLA_TRANSACCIONES, null,values);

        String[] campos = {COLUMNA_CODIGO,COLUMNA_TRACE,COLUMNA_REFERENCIA,COLUMNA_TIPO_TARJETA,COLUMNA_TIPO_CUENTA,COLUMNA_LOTE,COLUMNA_TIPO_TRANSACCION,COLUMNA_TRANSACCION_JSON,COLUMNA_ESTADO};

        Cursor c = db.query(TABLA_TRANSACCIONES, campos, null, null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                codigo = c.getString(0);
            } while(c.moveToNext());
        }

        db.close();

        return codigo;
    }

    public void actualizarEstadoporNumeroReferencia(String numeroReferencia, int estado){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_ESTADO,estado);

        db.update(TABLA_TRANSACCIONES, values, COLUMNA_REFERENCIA + "= '"+numeroReferencia+"'",null);
        db.close();
    }

    public Transaccion obtenerTransaccionPorCodigo(Integer codigo){

        SQLiteDatabase db = this.db.getWritableDatabase();
        Transaccion transaccion = null;

        String[] campos = {COLUMNA_CODIGO,COLUMNA_TRACE,COLUMNA_REFERENCIA,COLUMNA_TIPO_TARJETA,COLUMNA_TIPO_CUENTA,COLUMNA_LOTE,COLUMNA_TIPO_TRANSACCION,COLUMNA_TRANSACCION_JSON,COLUMNA_ESTADO};

        Cursor c = db.query(TABLA_TRANSACCIONES, campos, COLUMNA_CODIGO + " = " + "'" + codigo + "'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                transaccion = new Transaccion(codigo,c.getLong(1),c.getLong(2),c.getString(3),c.getString(4),c.getLong(5),c.getString(6),c.getString(7),c.getInt(8));
            } while(c.moveToNext());
        }

        return transaccion;
    }

    public Transaccion obtenerTransaccionPorNumeroReferenciaActual(Long numeroReferenciaActual){

        SQLiteDatabase db = this.db.getWritableDatabase();
        Transaccion transaccion = null;

        String[] campos = {COLUMNA_CODIGO,COLUMNA_TRACE,COLUMNA_REFERENCIA,COLUMNA_TIPO_TARJETA,COLUMNA_TIPO_CUENTA,COLUMNA_LOTE,COLUMNA_TIPO_TRANSACCION,COLUMNA_TRANSACCION_JSON,COLUMNA_ESTADO};

        Cursor c = db.query(TABLA_TRANSACCIONES, campos, COLUMNA_REFERENCIA + " = " + "'" + numeroReferenciaActual + "'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                transaccion = new Transaccion(c.getInt(0),c.getLong(1),c.getLong(2),c.getString(3),c.getString(4),c.getLong(5),c.getString(6),c.getString(7),c.getInt(8));
            } while(c.moveToNext());
        }

        return transaccion;
    }

    public Transaccion obtenerTransaccionPorTipoTransaccionyNumeroReferenciaActual(String tipoTransaccion, Long numeroReferenciaActual){

        SQLiteDatabase db = this.db.getWritableDatabase();
        Transaccion transaccion = null;

        String[] campos = {COLUMNA_CODIGO,COLUMNA_TRACE,COLUMNA_REFERENCIA,COLUMNA_TIPO_TARJETA,COLUMNA_TIPO_CUENTA,COLUMNA_LOTE,COLUMNA_TIPO_TRANSACCION,COLUMNA_TRANSACCION_JSON,COLUMNA_ESTADO};

        Cursor c = db.query(TABLA_TRANSACCIONES, campos, COLUMNA_TIPO_TRANSACCION + " = " + "'" + tipoTransaccion + "' AND " + COLUMNA_REFERENCIA + " = " + "'" + numeroReferenciaActual + "'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                transaccion = new Transaccion(c.getInt(0),c.getLong(1),c.getLong(2),c.getString(3),c.getString(4),c.getLong(5),c.getString(6),c.getString(7),c.getInt(8));
            } while(c.moveToNext());
        }

        return transaccion;
    }

    public ArrayList<Transaccion> obtenerTransaccionesPorTipoTarjetayNumeroLoteActual(String tipoTarjeta, Long numeroLoteActual){

        SQLiteDatabase db = this.db.getWritableDatabase();
        ArrayList<Transaccion> transaccionesLista = new ArrayList<Transaccion>();

        String[] campos = {COLUMNA_CODIGO,COLUMNA_TRACE,COLUMNA_REFERENCIA,COLUMNA_TIPO_TARJETA,COLUMNA_TIPO_CUENTA,COLUMNA_LOTE,COLUMNA_TIPO_TRANSACCION,COLUMNA_TRANSACCION_JSON,COLUMNA_ESTADO};

        Cursor c = db.query(TABLA_TRANSACCIONES, campos, COLUMNA_REFERENCIA + " IS NOT NULL AND " + COLUMNA_TIPO_TARJETA + " = " + "'" + tipoTarjeta + "' AND " + COLUMNA_LOTE + " = " + "'" + numeroLoteActual + "'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                transaccionesLista.add(new Transaccion(c.getInt(0),c.getLong(1),c.getLong(2),c.getString(3),c.getString(4),c.getLong(5),c.getString(6),c.getString(7),c.getInt(8)));
            } while(c.moveToNext());
        }

        return transaccionesLista;
    }

    public ArrayList<Transaccion> obtenerTransaccionesPorTipoTarjetaNumeroLoteActualyEstado(String tipoTarjeta, Long numeroLoteActual, int estado){

        SQLiteDatabase db = this.db.getWritableDatabase();
        ArrayList<Transaccion> transaccionesLista = new ArrayList<Transaccion>();

        String[] campos = {COLUMNA_CODIGO,COLUMNA_TRACE,COLUMNA_REFERENCIA,COLUMNA_TIPO_TARJETA,COLUMNA_TIPO_CUENTA,COLUMNA_LOTE,COLUMNA_TIPO_TRANSACCION,COLUMNA_TRANSACCION_JSON,COLUMNA_ESTADO};

        Cursor c = db.query(TABLA_TRANSACCIONES, campos, COLUMNA_TIPO_TARJETA + " = " + "'" + tipoTarjeta + "' AND " + COLUMNA_LOTE + " = " + "'" + numeroLoteActual + "' AND " + COLUMNA_ESTADO + " = " + "'" + estado + "'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                transaccionesLista.add(new Transaccion(c.getInt(0),c.getLong(1),c.getLong(2),c.getString(3),c.getString(4),c.getLong(5),c.getString(6),c.getString(7),c.getInt(8)));
            } while(c.moveToNext());
        }

        return transaccionesLista;
    }

    public ArrayList<Transaccion> obtenerTransaccionesPorTipoTransaccionyCodigoEstado(String tipoTransaccion, int estado){

        SQLiteDatabase db = this.db.getWritableDatabase();
        ArrayList<Transaccion> transaccionesLista = new ArrayList<Transaccion>();

        String[] campos = {COLUMNA_CODIGO,COLUMNA_TRACE,COLUMNA_REFERENCIA,COLUMNA_TIPO_TARJETA,COLUMNA_TIPO_CUENTA,COLUMNA_LOTE,COLUMNA_TIPO_TRANSACCION,COLUMNA_TRANSACCION_JSON,COLUMNA_ESTADO};

        Cursor c = db.query(TABLA_TRANSACCIONES, campos, COLUMNA_TIPO_TRANSACCION + " = " + "'" + tipoTransaccion + "' AND " + COLUMNA_ESTADO + " = " + "'" + estado + "'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                transaccionesLista.add(new Transaccion(c.getInt(0),c.getLong(1),c.getLong(2),c.getString(3),c.getString(4),c.getLong(5),c.getString(6),c.getString(7),c.getInt(8)));
            } while(c.moveToNext());
        }

        return transaccionesLista;
    }

    public ArrayList<Transaccion> obtenerTransaccionesPorTipoTransaccion(String tipoTransaccion){

        SQLiteDatabase db = this.db.getWritableDatabase();
        ArrayList<Transaccion> transaccionesLista = new ArrayList<Transaccion>();

        String[] campos = {COLUMNA_CODIGO,COLUMNA_TRACE,COLUMNA_REFERENCIA,COLUMNA_TIPO_TARJETA,COLUMNA_TIPO_CUENTA,COLUMNA_LOTE,COLUMNA_TIPO_TRANSACCION,COLUMNA_TRANSACCION_JSON,COLUMNA_ESTADO};

        Cursor c = db.query(TABLA_TRANSACCIONES, campos, COLUMNA_TIPO_TRANSACCION + " = " + "'" + tipoTransaccion + "'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                transaccionesLista.add(new Transaccion(c.getInt(0),c.getLong(1),c.getLong(2),c.getString(3),c.getString(4),c.getLong(5),c.getString(6),c.getString(7),c.getInt(8)));
            } while(c.moveToNext());
        }

        return transaccionesLista;
    }

    public Transaccion obtenerTransaccionPorTrace(String trace){

        SQLiteDatabase db = this.db.getWritableDatabase();
        Transaccion transaccion = null;

        String[] campos = {COLUMNA_CODIGO,COLUMNA_TRACE,COLUMNA_REFERENCIA,COLUMNA_TIPO_TARJETA,COLUMNA_TIPO_CUENTA,COLUMNA_LOTE,COLUMNA_TIPO_TRANSACCION,COLUMNA_TRANSACCION_JSON,COLUMNA_ESTADO};

        Cursor c = db.query(TABLA_TRANSACCIONES, campos, COLUMNA_TRACE + " = " + "'" + trace + "'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                transaccion = new Transaccion(c.getInt(0),c.getLong(1),c.getLong(2),c.getString(3),c.getString(4),c.getLong(5),c.getString(6),c.getString(7),c.getInt(8));
            } while(c.moveToNext());
        }

        return transaccion;
    }

    public void actualizarObjetoJSONporNumeroTrace(String numeroTrace, String transaccionJSON){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_TRANSACCION_JSON,transaccionJSON);

        db.update(TABLA_TRANSACCIONES, values, COLUMNA_TRACE + "= '"+numeroTrace+"'",null);
        db.close();
    }

    public void actualizarEstadoporNumeroTrace(String numeroTrace, int estado){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_ESTADO,estado);

        db.update(TABLA_TRANSACCIONES, values, COLUMNA_TRACE + "= '"+numeroTrace+"'",null);
        db.close();
    }

    public void actualizarReferenciaporNumeroTrace(String numeroTrace, Long referencia){

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_REFERENCIA,referencia);

        db.update(TABLA_TRANSACCIONES, values, COLUMNA_TRACE + "= '"+numeroTrace+"'",null);
        db.close();
    }

    public void eliminarTransaccionesporNumeroLoteCredito(String tipoTarjeta, Long numeroLoteCredito){
        SQLiteDatabase db = this.db.getWritableDatabase();

        db.delete(TABLA_TRANSACCIONES,  COLUMNA_TIPO_TARJETA + "= '"+tipoTarjeta+"' AND " + COLUMNA_LOTE + "= '"+numeroLoteCredito+"'",null);
        db.close();
    }

    public void eliminarTransaccionesporNumeroLoteDebito(String tipoTarjeta, Long numeroLoteDebito){
        SQLiteDatabase db = this.db.getWritableDatabase();

        db.delete(TABLA_TRANSACCIONES,  COLUMNA_TIPO_TARJETA + "= '"+tipoTarjeta+"' AND " + COLUMNA_LOTE + "= '"+numeroLoteDebito+"'",null);
        db.close();
    }
}

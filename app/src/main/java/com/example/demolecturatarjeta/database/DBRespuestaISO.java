package com.example.demolecturatarjeta.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.demolecturatarjeta.database.object.RespuestaISO;

public class DBRespuestaISO {
    private BaseDeDatos db = null;

    //Columnas de la Tabla
    public static final String TABLA_RESPUESTAS_ISO = "respuestas_iso";
    public static final String COLUMNA_CODIGO = "codigo";
    public static final String COLUMNA_DESCRIPCION = "descripcion";

    protected static final String SQL_CREAR_RESPUESTA_ISO = "create table "
            + TABLA_RESPUESTAS_ISO + "(" + COLUMNA_CODIGO
            + " text not null, " + COLUMNA_DESCRIPCION
            + " text not null);";

    public DBRespuestaISO(BaseDeDatos db) {
        this.db = db;
    }

    public void agregarRespuestaISO(RespuestaISO respuestaISO){
        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_CODIGO, respuestaISO.getCodigo());
        values.put(COLUMNA_DESCRIPCION, respuestaISO.getDescripcion());

        db.insert(TABLA_RESPUESTAS_ISO, null,values);
        db.close();
    }

    public RespuestaISO obtenerRespuestaISOporCodigo(String codigo){

        SQLiteDatabase db = this.db.getWritableDatabase();
        RespuestaISO respuestaISO = null;

        String[] campos = {COLUMNA_CODIGO,COLUMNA_DESCRIPCION};

        try{
            Cursor c = db.query(TABLA_RESPUESTAS_ISO, campos, "codigo = " + "'"+codigo+"'", null, null, null, null);

            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    respuestaISO = new RespuestaISO(c.getString(0),c.getString(1));
                } while(c.moveToNext());
            }

        } catch(SQLException e){
            respuestaISO = null;
        }

        return respuestaISO;
    }

    public int leer(){

        int numeroRegistros = 0;

        SQLiteDatabase db = this.db.getReadableDatabase();

        String[] campos = {COLUMNA_CODIGO,COLUMNA_DESCRIPCION};

        Cursor c = db.query(TABLA_RESPUESTAS_ISO, campos, null, null, null, null, null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                //String codigo= c.getString(0);
                //LogInterno.d("Prueba","Referencia: " + c.getString(0) + " Autorización: " + c.getString(1) + " Fecha: " + c.getString(2));
                numeroRegistros = numeroRegistros + 1;
            } while(c.moveToNext());
        }

        return numeroRegistros;
    }
}

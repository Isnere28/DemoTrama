package com.example.demolecturatarjeta.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demolecturatarjeta.database.object.Estado;

public class DBEstado {

    private BaseDeDatos db = null;

    //Columnas de la Tabla
    public static final String TABLA_ESTADOS = "estados";
    public static final String COLUMNA_CODIGO = "codigo";
    public static final String COLUMNA_DESCRIPCION = "descripcion";

    protected static final String SQL_CREAR_ESTADO = "create table "
            + TABLA_ESTADOS + "(" + COLUMNA_CODIGO
            + " text not null, " + COLUMNA_DESCRIPCION
            + " text not null);";

    public DBEstado(BaseDeDatos db) {
        this.db = db;
    }

    public void agregarEstado(Estado estado){
        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_CODIGO, estado.getCodigo());
        values.put(COLUMNA_DESCRIPCION, estado.getDescripcion());

        db.insert(TABLA_ESTADOS, null,values);
        db.close();
    }

    public Estado obtenerEstadoporCodigo(String codigo){

        SQLiteDatabase db = this.db.getWritableDatabase();
        Estado estado = null;

        String[] campos = {COLUMNA_CODIGO,COLUMNA_DESCRIPCION};

        Cursor c = db.query(TABLA_ESTADOS, campos, "codigo = " + "'"+codigo+"'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                estado = new Estado(c.getInt(0),c.getString(1));
            } while(c.moveToNext());
        }

        return estado;
    }

    public int leer(){

        int numeroRegistros = 0;

        SQLiteDatabase db = this.db.getReadableDatabase();

        String[] campos = {COLUMNA_CODIGO,COLUMNA_DESCRIPCION};

        Cursor c = db.query(TABLA_ESTADOS, campos, null, null, null, null, null);

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

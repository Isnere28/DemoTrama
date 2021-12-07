package com.example.demolecturatarjeta.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demolecturatarjeta.database.object.LineaLog;

import java.util.ArrayList;

public class DBLog {
    private BaseDeDatos db = null;

    //Columnas de la Tabla
    public static final String TABLA_LOG = "log";
    public static final String COLUMNA_ID = "id";
    public static final String COLUMNA_CONTENIDO = "contenido";

    protected static final String SQL_CREAR_LOG = "create table "
            + TABLA_LOG + "(" + COLUMNA_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMNA_CONTENIDO
            + " text not null);";

    public DBLog(BaseDeDatos db) {
        this.db = db;
    }

    public void agregarLineaLog(LineaLog lineaLog){
        SQLiteDatabase db = this.db.getWritableDatabase();
int j = 0;
        ContentValues values = new ContentValues();

        values.put(COLUMNA_CONTENIDO, lineaLog.getContenido());

        db.insert(TABLA_LOG, null,values);
        db.close();
    }

    public LineaLog obtenerLineaLogpodID(String id){

        SQLiteDatabase db = this.db.getWritableDatabase();
        LineaLog lineaLog = null;

        String[] campos = {COLUMNA_ID,COLUMNA_CONTENIDO};

        Cursor c = db.query(TABLA_LOG, campos, "id = " + "'"+id+"'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                lineaLog = new LineaLog(c.getInt(0),c.getString(1));
            } while(c.moveToNext());
        }

        return lineaLog;
    }

    public ArrayList<String> leer(){

        ArrayList<String> logArrayList;

        SQLiteDatabase db = this.db.getReadableDatabase();

        String[] campos = {COLUMNA_ID,COLUMNA_CONTENIDO};

        Cursor c = db.query(TABLA_LOG, campos, null, null, null, null, null);

        logArrayList = new ArrayList<>();

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                //String codigo= c.getString(0);
                logArrayList.add(c.getString(1));
                //LogInterno.d("Prueba","Referencia: " + c.getString(0) + " Autorización: " + c.getString(1) + " Fecha: " + c.getString(2));
            } while(c.moveToNext());
        }

        return logArrayList;
    }
}

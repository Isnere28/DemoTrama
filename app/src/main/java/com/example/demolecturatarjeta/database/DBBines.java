package com.example.demolecturatarjeta.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demolecturatarjeta.database.object.Bines;

public class DBBines {

    private BaseDeDatos db = null;

    public DBBines(BaseDeDatos db) {
        this.db = db;
    }

    //Columnas de la Tabla
    public static final String TABLA_BINES = "bines";
    public static final String COLUMNA_CODIGO = "codigo";
    public static final String COLUMNA_INICIO = "inicio";
    public static final String COLUMNA_FIN = "fin";
    public static final String COLUMNA_TIPO_DE_TARJETA = "tipo_de_tarjeta";
    public static final String COLUMNA_TARJETA = "tarjeta";
    public static final String COLUMNA_BANCO = "banco";

    //Comando SQL de creación de la tabla
    protected static final String SQL_CREAR_BINES = "create table "
            + TABLA_BINES + "(" + COLUMNA_CODIGO
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMNA_INICIO
            + " text not null, " + COLUMNA_FIN
            + " text not null, " + COLUMNA_TIPO_DE_TARJETA
            + " text not null, " + COLUMNA_TARJETA
            + " text not null, " + COLUMNA_BANCO
            + " text not null);";

    public String agregarBin(Bines bines) {

        String codigo = "";

        SQLiteDatabase db = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(COLUMNA_CODIGO, bines.getCodigo());
        values.put(COLUMNA_INICIO, bines.getInicio());
        values.put(COLUMNA_FIN, bines.getFin());
        values.put(COLUMNA_TIPO_DE_TARJETA, bines.getTipoDeTarjeta());
        values.put(COLUMNA_TARJETA, bines.getTarjeta());
        values.put(COLUMNA_BANCO, bines.getBanco());

        db.insert(TABLA_BINES, null,values);

        String[] campos = {COLUMNA_CODIGO,COLUMNA_INICIO,COLUMNA_FIN,COLUMNA_TIPO_DE_TARJETA,COLUMNA_TARJETA,COLUMNA_BANCO};

        Cursor c = db.query(TABLA_BINES, campos, null, null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                codigo = c.getString(0);
                String inicio = c.getString(1);
                String fin = c.getString(2);
                String tipoDeTarjeta = c.getString(3);
                String tarjeta = c.getString(4);
                String banco = c.getString(5);
            } while(c.moveToNext());
        }

        db.close();

        return codigo;
    }

    public Bines obtenerBinporCodigo(Integer codigo){

        SQLiteDatabase db = this.db.getWritableDatabase();
        Bines bin = null;

        String[] campos = {COLUMNA_CODIGO,COLUMNA_INICIO,COLUMNA_FIN,COLUMNA_TIPO_DE_TARJETA,COLUMNA_TARJETA,COLUMNA_BANCO};

        Cursor c = db.query(TABLA_BINES, campos, "codigo = " + "'"+codigo+"'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                bin = new Bines(codigo,c.getLong(0),c.getLong(1),c.getString(2),c.getString(3),c.getString(4));
            } while(c.moveToNext());
        }

        return bin;
    }

    public String obtenerMarcaTarjetaporNumeroTarjeta(String numeroTarjeta){

        SQLiteDatabase db = this.db.getWritableDatabase();
        String marcaTarjeta = "";

        String[] campos = {COLUMNA_TARJETA};

        Cursor c = db.query(TABLA_BINES, campos, "inicio <= " + "'"+numeroTarjeta+"' AND fin >= " + "'"+numeroTarjeta+"'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                marcaTarjeta = c.getString(0);
            } while(c.moveToNext());
        }

        return marcaTarjeta;
    }

    public String obtenerTipoTarjetaporNumeroTarjeta(String numeroTarjeta){

        SQLiteDatabase db = this.db.getWritableDatabase();
        String marcaTarjeta = "";

        String[] campos = {COLUMNA_TIPO_DE_TARJETA};

        Cursor c = db.query(TABLA_BINES, campos, "inicio <= " + "'"+numeroTarjeta+"' AND fin >= " + "'"+numeroTarjeta+"'", null, null, null, null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                marcaTarjeta = c.getString(0);
            } while(c.moveToNext());
        }

        return marcaTarjeta;
    }

    public int leer(){

        int numeroRegistros = 0;

        SQLiteDatabase db = this.db.getReadableDatabase();

        String[] campos = {COLUMNA_CODIGO,COLUMNA_INICIO,COLUMNA_FIN,COLUMNA_TIPO_DE_TARJETA,COLUMNA_TARJETA,COLUMNA_BANCO};

        Cursor c = db.query(TABLA_BINES, campos, null, null, null, null, null);

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

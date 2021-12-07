package com.example.demolecturatarjeta.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "basededatosinterna.db";

    public BaseDeDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBPerfilConfiguracion.SQL_CREAR_PERFIL_CONFIGURACION);
        db.execSQL(DBRespuestaISO.SQL_CREAR_RESPUESTA_ISO);
        db.execSQL(DBBines.SQL_CREAR_BINES);
        db.execSQL(DBEstado.SQL_CREAR_ESTADO);
        db.execSQL(DBLog.SQL_CREAR_LOG);
        db.execSQL(DBTransaccion.SQL_CREAR_TRANSACCION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

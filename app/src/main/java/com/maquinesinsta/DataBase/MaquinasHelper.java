package com.maquinesinsta.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaquinasHelper extends SQLiteOpenHelper{

    private static final int database_VERSION = 1;

    // database name
    private static final String database_NAME = "MaquinasDataBase";

    public MaquinasHelper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    private String CREATE_MAQUINAS =
            "CREATE TABLE maquinas ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombreCliente TEXT NOT NULL," +
                    "direccion TEXT NOT NULL," +
                    "codigoPostal TEXT NOT NULL," +
                    "poblacion TEXT NOT NULL," +
                    "telefono TEXT," +
                    "email TEXT," +
                    "numSerie TEXT NOT NULL," +
                    "ultRevision TEXT," +
                    "tipos_id INTEGER," +
                    "zonas_id INTEGER," +
                    "FOREIGN KEY(tipos_id) REFERENCES tipos(_id) ON DELETE CASCADE," +
                    "FOREIGN KEY(zonas_id) REFERENCES zonas(_id) ON DELETE CASCADE)";

    private String CREATE_ZONAS =
            "CREATE TABLE zonas ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL)";

    private String CREATE_TIPOS =
            "CREATE TABLE tipos ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL," +
                    "color TEXT NOT NULL)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MAQUINAS);
        db.execSQL(CREATE_ZONAS);
        db.execSQL(CREATE_TIPOS);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /*if(oldVersion < 2) {
            sqLiteDatabase.execSQL(VIEW_HISTORIAL);
        }*/

    }

}

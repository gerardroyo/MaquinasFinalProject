package com.maquinesinsta.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MaquinasDatasource {

    public static final String table_MAQUINAS = "maquinas";
    public static final String MAQUINAS_ID = "_id";
    public static final String MAQUINAS_NOMBRE = "nombreCliente";
    public static final String MAQUINAS_DIRECCION = "direccion";
    public static final String MAQUINAS_CPOSTAL = "codigoPostal";
    public static final String MAQUINAS_POBLACION = "poblacion";
    public static final String MAQUINAS_TELEFONO= "telefono";
    public static final String MAQUINAS_EMAIL = "email";
    public static final String MAQUINAS_NUMSERIE = "numSerie";
    public static final String MAQUINAS_ULTREVISION = "ultRevision";
    public static final String MAQUINAS_ID_TIPOS = "tipos_id";
    public static final String MAQUINAS_ID_ZONAS = "zonas_id";

    public static final String table_ZONAS = "zonas";
    public static final String ZONAS_ID = "_id";
    public static final String ZONAS_NOMBRE = "nombre";

    public static final String table_TIPOS = "tipos";
    public static final String TIPOS_ID = "_id";
    public static final String TIPOS_NOMBRE = "nombre";
    public static final String TIPOS_COLOR = "color";

    private MaquinasHelper dbHelper;
    private static SQLiteDatabase dbW, dbR;

    // CONSTRUCTOR
    public MaquinasDatasource(Context ctx) {
        // En el constructor directament obro la comunicació amb la base de dades
        dbHelper = new MaquinasHelper(ctx);

        // amés també construeixo dos databases un per llegir i l'altre per alterar
        open();
    }

    // DESTRUCTOR
    protected void finalize () {
        // Cerramos los databases
        dbW.close();
        dbR.close();
    }

    private void open() {
        dbW = dbHelper.getWritableDatabase();
        dbR = dbHelper.getReadableDatabase();
    }

    // Funcions que retornen cursors de articulos

//-----------------------------------------> SHOW <-----------------------------------------\\

    // Taula Tipos
    public Cursor returnAllTipos() {
        // Retorem tots el tipus de maquines
        return dbR.query(table_TIPOS, new String[]{TIPOS_ID,TIPOS_NOMBRE,TIPOS_COLOR},
                null, null,
                null, null, TIPOS_ID);
    }

    // Taula Zonas
    public Cursor returnAllZonas() {
        // Retorem totes les zones
        return dbR.query(table_ZONAS, new String[]{ZONAS_ID,ZONAS_NOMBRE},
                null, null,
                null, null, ZONAS_ID);
    }

//------------------------------------------------------------------------------------------\\
//-----------------------------------------> ADD <-----------------------------------------\\

    // Taula Tipos
    public long addTipo(String nombre, String color) {
        // Creem un nou tipos i retornem el id crear per si el necessiten
        ContentValues values = new ContentValues();
        values.put(TIPOS_NOMBRE, nombre);
        values.put(TIPOS_COLOR, color);

        return dbW.insert(table_TIPOS,null,values);
    }

    // Taula Zonas
    public long addZona(String nombre) {
        // Creem un nou tipos i retornem el id crear per si el necessiten
        ContentValues values = new ContentValues();
        values.put(ZONAS_NOMBRE, nombre);

        return dbW.insert(table_ZONAS,null,values);
    }

//-----------------------------------------------------------------------------------------\\
//-----------------------------------------> UPDATE <-----------------------------------------\\

    // Taula Tipos
    public void updateTipo(long id, String nombre, String color) {
        // Modifiquem els valors de tipos amb clau primària "id"
        ContentValues values = new ContentValues();
        values.put(TIPOS_NOMBRE, nombre);
        values.put(TIPOS_COLOR, color);

        dbW.update(table_TIPOS,values, TIPOS_ID + " = ?", new String[] { String.valueOf(id) });
    }

    // Taula Zonas
    public void updateZona(long id, String nombre) {
        // Modifiquem els valors de zonas amb clau primària "id"
        ContentValues values = new ContentValues();
        values.put(ZONAS_NOMBRE, nombre);

        dbW.update(table_ZONAS,values, ZONAS_ID + " = ?", new String[] { String.valueOf(id) });
    }

//---------------------------------------------------------------------------------------------\\
//-----------------------------------------> DELETE <-----------------------------------------\\
////////////////////////////////////falta controlar que no borri si existeix algun maquina amb aquests////////////////////////////////////
    // Taula Tipos
    public void deleteTipo(long id) {
        // Eliminem la task amb clau primària "id"
        dbW.delete(table_TIPOS,TIPOS_ID + " = ?", new String[] { String.valueOf(id) });
    }

    // Taula Zonas
    public void deleteZona(long id) {
        // Eliminem la task amb clau primària "id"
        dbW.delete(table_ZONAS,ZONAS_ID + " = ?", new String[] { String.valueOf(id) });
    }

//---------------------------------------------------------------------------------------------\\
//-----------------------------------------> LOAD A ROW <-----------------------------------------\\

// Taula Tipos
    public static Cursor oneRowTipo(long id) {
        // Retorna un cursor només amb el id indicat
        return dbR.query(table_TIPOS, new String[]{TIPOS_ID,TIPOS_NOMBRE,TIPOS_COLOR},
                TIPOS_ID+ "=?", new String[]{String.valueOf(id)},
                null, null, null);

    }

    // Taula Zonas
    public static Cursor oneRowZona(long id) {
        // Retorna un cursor només amb el id indicat
        return dbR.query(table_ZONAS, new String[]{ZONAS_ID,ZONAS_NOMBRE},
                ZONAS_ID+ "=?", new String[]{String.valueOf(id)},
                null, null, null);

    }

//------------------------------------------------------------------------------------------------\\

    /*public Cursor gMovimientos(long id) {
        // Retorem totes les zones
        return dbR.query(table_MOVIMIENTOS, new String[]{MOVIMIENTOS_ID,MOVIMIENTOS_CODE,MOVIMIENTOS_FECHA,MOVIMIENTOS_CANTIDAD,MOVIMIENTOS_TIPO, MOVIMIENTOS_ID_ARTICULOS},
                MOVIMIENTOS_ID_ARTICULOS + "=?", new String[]{String.valueOf(id)},
                null, null, MOVIMIENTOS_ID);
    }*/
//cerca per data
    /*public Cursor gMovimientosDate(long id, String initDate, String finDate) {
        // Filtrem per data
        return dbR.rawQuery("select * from " + table_MOVIMIENTOS +
                " where fecha BETWEEN '" + initDate + "' AND '" + finDate + "' AND articulo_ID = " + id + " " +
                "ORDER BY fecha DESC ", null);
    }*/
//cerca per data (today)
    /*public Cursor gMovimientosToday(String today) {
        // Filtrem nomes les d'avui
        String ConstToday = "SELECT * FROM " + table_MOVIMIENTOS + " INNER JOIN " + table_ARTICULOS +
                " ON " + table_MOVIMIENTOS + "." + MOVIMIENTOS_ID_ARTICULOS + "=" + table_ARTICULOS + "." + ARTICULOS_ID +
                " WHERE " + table_MOVIMIENTOS + "." + MOVIMIENTOS_FECHA + "=?" +
                " ORDER BY " + table_MOVIMIENTOS + "." + MOVIMIENTOS_CODE + " ASC";

        Cursor CursorToday = dbR.rawQuery(ConstToday, new String[]{String.valueOf(today)});

        return CursorToday;
    }*/
//retorna pending/completed
    /*public Cursor gArticulosPending() {
        // Retornem les tasques que el camp ESTOC <= 0
        return dbR.query(table_ARTICULOS, new String[]{ARTICULOS_ID,ARTICULOS_CODE,ARTICULOS_DESCRIPCION,ARTICULOS_PVP,ARTICULOS_ESTOC},
                ARTICULOS_ESTOC + "<=" + 0, null,
                null, null, ARTICULOS_ID);
    }

    public Cursor gArticulosCompleted() {
        // Retornem les tasques que el camp ESTOC > 0
        return dbR.query(table_ARTICULOS, new String[]{ARTICULOS_ID,ARTICULOS_CODE,ARTICULOS_DESCRIPCION,ARTICULOS_PVP,ARTICULOS_ESTOC},
                ARTICULOS_ESTOC + ">" + 0, null,
                null, null, ARTICULOS_ID);
    }*/
// retorna 1 sola tasca
    /*public static Cursor task(long id) {
        // Retorna un cursor només amb el id indicat
        // Retornem les tasques que el camp ESTOC = 1
        return dbR.query(table_ARTICULOS, new String[]{ARTICULOS_ID,ARTICULOS_CODE,ARTICULOS_DESCRIPCION,ARTICULOS_PVP,ARTICULOS_ESTOC},
                ARTICULOS_ID+ "=?", new String[]{String.valueOf(id)},
                null, null, null);

    }*/
// retorns un sol itme
    /*public Cursor itemCode(String itemCode) {
        return dbR.query(table_ARTICULOS, new String[]{ARTICULOS_ID,ARTICULOS_CODE,ARTICULOS_DESCRIPCION,ARTICULOS_PVP,ARTICULOS_ESTOC},
                ARTICULOS_CODE+ "=?", new String[]{String.valueOf(itemCode)},
                null, null, null);

    }*/

    // ******************
    // Funciones de manipualación de datos
    // ******************
//add
    /*public long taskAdd(String code, String description, float pvp, int estoc) {
        // Creem una nova tasca i retornem el id crear per si el necessiten
        ContentValues values = new ContentValues();
        values.put(ARTICULOS_CODE, code);
        values.put(ARTICULOS_DESCRIPCION, description);
        values.put(ARTICULOS_PVP,pvp);
        values.put(ARTICULOS_ESTOC,estoc);

        return dbW.insert(table_ARTICULOS,null,values);
    }

    public long taskAddMov(String code, String fecha, int cantidad, String tipo, long id_articulo) {
        // Creem una nova tasca i retornem el id crear per si el necessiten
        ContentValues values = new ContentValues();
        values.put(MOVIMIENTOS_CODE, code);
        values.put(MOVIMIENTOS_FECHA, fecha);
        values.put(MOVIMIENTOS_CANTIDAD,cantidad);
        values.put(MOVIMIENTOS_TIPO,tipo);
        values.put(MOVIMIENTOS_ID_ARTICULOS,id_articulo);

        return dbW.insert(table_MOVIMIENTOS,null,values);
    }*/
//Update
    /*public void taskUpdate(long id, String code, String description, float pvp, int estoc) {
        // Modifiquem els valors de las tasca amb clau primària "id"
        ContentValues values = new ContentValues();
        values.put(ARTICULOS_CODE, code);
        values.put(ARTICULOS_DESCRIPCION, description);
        values.put(ARTICULOS_PVP,pvp);
        values.put(ARTICULOS_ESTOC,estoc);

        dbW.update(table_ARTICULOS,values, ARTICULOS_ID + " = ?", new String[] { String.valueOf(id) });
    }*/
//delete
    /*public void taskDelete(long id) {
        // Eliminem la task amb clau primària "id"
        dbW.delete(table_ARTICULOS,ARTICULOS_ID + " = ?", new String[] { String.valueOf(id) });
    }*/
//gestio de filtes pendent/completat
    /*public void taskPending(long id, int estoc) {
        // Modifiquem al estat de pendent la task indicada
        ContentValues values = new ContentValues();
        values.put(ARTICULOS_ESTOC,estoc);

        dbW.update(table_ARTICULOS,values, ARTICULOS_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void taskCompleted(long id, int estoc) {
        // Modifiquem al estat de pendent la task indicada
        ContentValues values = new ContentValues();
        values.put(ARTICULOS_ESTOC,estoc);

        dbW.update(table_ARTICULOS,values, ARTICULOS_ID + " = ?", new String[] { String.valueOf(id) });
    }*/

}

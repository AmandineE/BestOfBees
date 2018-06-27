package com.example.android.bestofbees;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by Amandine on 24/06/2018.
 */

public class DataBaseData extends SQLiteOpenHelper {
    private static DataBaseData sInstance;

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "data.db";

    private static final String TABLE_DATA = "table_data";
    private static final String COL_DATE = "DATE";
    private static final String COL_HEURE = "HEURE";
    private static final String COL_HUMIDITY = "Humidity";
    private static final String COL_TEMPERATURE = "Temperature";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_DATA + " ("
            +COL_DATE +" DATE, " + COL_HEURE + " HEURE, " + COL_HUMIDITY + " HUMIDITY, " + COL_TEMPERATURE
            + " TEMPERATURE);";

    public static synchronized DataBaseData getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DataBaseData(context.getApplicationContext(),NOM_BDD,null,VERSION_BDD);
        }
        return sInstance;
    }

    public DataBaseData(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public List<Data> getAllData() {
        List<Data> dataEntryList = new ArrayList<Data>();
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Data dataEntry = new Data();
                dataEntry.setDate(cursor.getString(0));
                dataEntry.setHeure(cursor.getString(1));
                dataEntry.setHumidity(cursor.getString(2));
                dataEntry.setTemperature(cursor.getString(3));
                dataEntryList.add(dataEntry);
            }
            while (cursor.moveToNext());
        }
        return dataEntryList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_DATA + ";");
        onCreate(db);
    }
}

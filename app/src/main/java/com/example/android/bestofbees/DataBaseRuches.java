package com.example.android.bestofbees;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amandine on 19/06/2018.
 */

public class DataBaseRuches extends SQLiteOpenHelper {

    private static DataBaseRuches sInstance;

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "ruches.db";

    private static final String TABLE_RUCHES = "table_ruches";
    private static final String COL_ID = "ID";
    private static final String COL_UID = "UID";
    private static final String COL_NOM = "NOM";
    private static final String COL_LOC = "Localisation";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_RUCHES + " ("
            +COL_ID +" ID, " + COL_UID + " UID KEY, " + COL_NOM + " TEXT NOT NULL, " + COL_LOC
           + " TEXT NOT NULL);";

    public static synchronized DataBaseRuches getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DataBaseRuches(context.getApplicationContext(),NOM_BDD,null,VERSION_BDD);
        }
        return sInstance;
    }

    public DataBaseRuches(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public List<Ruche> getAllRuches() {
        List<Ruche> rucheEntryList = new ArrayList<Ruche>();
        String selectQuery = "SELECT  * FROM " + TABLE_RUCHES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Ruche rucheEntry = new Ruche();
                //rucheEntry.setId(Integer.parseInt(cursor.getString(0)));
                rucheEntry.setId(cursor.getString(0));
                rucheEntry.setUid(cursor.getString(1));
                rucheEntry.setNom(cursor.getString(2));
                rucheEntry.setLocalisation(cursor.getString(3));
                rucheEntryList.add(rucheEntry);
            }
            while (cursor.moveToNext());
        }
        return rucheEntryList;
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
        db.execSQL("DROP TABLE " + TABLE_RUCHES + ";");
        onCreate(db);
    }
}

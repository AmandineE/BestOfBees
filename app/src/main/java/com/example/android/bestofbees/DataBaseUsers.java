package com.example.android.bestofbees;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amandine on 20/06/2018.
 */

public class DataBaseUsers extends SQLiteOpenHelper {

    private static DataBaseUsers sInstance;

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "users.db";

    private static final String TABLE_USERS = "table_users";
    private static final String COL_ID = "ID";
    private static final String COL_PRENOM = "PRENOM";
    private static final String COL_NOM = "NOM";
    private static final String COL_MAIL = "Mail";
    private static final String COL_USERNAME = "USERNAME";
    private static final String COL_PASSWORD = "PASSWORD";
    private static final String COL_NBRUCHES = "Nb Ruches";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_USERS + " ("
            +COL_ID +" ID, " + COL_NOM + " UID KEY, " + COL_PRENOM + " TEXT NOT NULL, " + COL_MAIL + " TEXT NOT NULL, " + COL_USERNAME + " TEXT NOT NULL, " +
            COL_PASSWORD + " TEXT NOT NULL, " + COL_NBRUCHES
            + " TEXT NOT NULL);";

    public static synchronized DataBaseUsers getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DataBaseUsers(context.getApplicationContext(),NOM_BDD,null,VERSION_BDD);
        }
        return sInstance;
    }

    public DataBaseUsers(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public List<User> getAllUsers() {
        List<User> userEntryList = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User userEntry = new User();
                userEntry.setIdUser(Integer.parseInt(cursor.getString(0)));
                userEntry.setNomUser(cursor.getString(1));
                userEntry.setPrenom(cursor.getString(2));
                userEntry.setMail(cursor.getString(3));
                userEntry.setUsername(cursor.getString(4));
                userEntry.setPassword(cursor.getString(5));
                userEntry.setNbRuches(cursor.getString(6));
                userEntryList.add(userEntry);
            }
            while (cursor.moveToNext());
        }
        return userEntryList;
    }

    public int getSize(){
        List<User> userEntryList = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int n = 0;
        if (cursor.moveToFirst()) {
            do {
                n++;
            }
            while (cursor.moveToNext());
        }
        return n;
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
        db.execSQL("DROP TABLE " + TABLE_USERS + ";");
        onCreate(db);
    }
}

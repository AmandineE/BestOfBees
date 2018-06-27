package com.example.android.bestofbees;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Amandine on 24/06/2018.
 */

public class DataBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "data.db";

    private static final String TABLE_DATA = "table_data";
    private static final String COL_DATE = "DATE";
    private static final int NUM_COL_DATE = 0;
    private static final String COL_HEURE = "HEURE";
    private static final int NUM_COL_HEURE = 1;
    private static final String COL_HUMIDITY = "HUMIDITY";
    private static final int NUM_COL_HUMIDITY = 2;
    private static final String COL_TEMPERATURE = "TEMPERATURE";
    private static final int NUM_COL_TEMPERATURE = 3;

    private SQLiteDatabase bdd;

    private DataBaseData maBaseSQLite;

    public DataBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new DataBaseData(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public DataBaseData getMaBaseSQLite() {return maBaseSQLite;}

    public long insertData(Data data){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_DATE, data.getDate());
        values.put(COL_HEURE, data.getHeure());
        values.put(COL_HUMIDITY, data.getHumidity());
        values.put(COL_TEMPERATURE,data.getTemperature());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_DATA, null, values);
    }

    public Data getDataWithNom(String heure){
        //Récupère dans un Cursor les valeurs correspondant à une donnée contenue dans la BDD (ici on sélectionne la donnée grâce à son heure)
        Cursor c = bdd.query(TABLE_DATA, new String[] {COL_DATE, COL_HEURE, COL_HUMIDITY, COL_TEMPERATURE}, COL_HEURE + " LIKE \"" + heure +"\"", null, null, null, null);
        return cursorToData(c);
    }

    //Cette méthode permet de convertir un cursor en une ruche
    private Data cursorToData(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé une donnée
       Data data = new Data();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        data.setDate(c.getString(NUM_COL_DATE));
        data.setHeure(c.getString(NUM_COL_HEURE));
        data.setHumidity(c.getString(NUM_COL_HUMIDITY));
        data.setTemperature(c.getString(NUM_COL_TEMPERATURE));
        //On ferme le cursor
        c.close();

        //On retourne la ruche
        return data;
    }
}

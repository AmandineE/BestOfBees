package com.example.android.bestofbees;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Amandine on 19/06/2018.
 */

public class RuchesBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "ruches.db";

    private static final String TABLE_RUCHES = "table_ruches";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_UID = "UID";
    private static final int NUM_COL_UID = 1;
    private static final String COL_NOM = "NOM";
    private static final int NUM_COL_NOM = 2;
    private static final String COL_LOC = "Localisation";
    private static final int NUM_COL_LOC = 3;

    private SQLiteDatabase bdd;

    private DataBaseRuches maBaseSQLite;

    public RuchesBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new DataBaseRuches(context, NOM_BDD, null, VERSION_BDD);
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

    public DataBaseRuches getMaBaseSQLite() {return maBaseSQLite;}

    public long insertRuche(Ruche ruche){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_ID, ruche.getId());
        values.put(COL_UID, ruche.getUid());
        values.put(COL_NOM, ruche.getNom());
        values.put(COL_LOC,ruche.getLocalisation());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_RUCHES, null, values);
    }

    public int updateRuche(int id, Ruche ruche){
        //La mise à jour d'une ruche dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quelle ruche on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_ID, ruche.getId());
        values.put(COL_UID, ruche.getUid());
        values.put(COL_NOM, ruche.getNom());
        values.put(COL_LOC,ruche.getLocalisation());
        return bdd.update(TABLE_RUCHES, values, COL_ID + " = " +id, null);
    }

    public int removeRucheWithID(int id){
        //Suppression d'une ruche de la BDD grâce à l'ID
        return bdd.delete(TABLE_RUCHES, COL_ID + " = " +id, null);
    }

    public Ruche getRucheWithNom(String nom){
        //Récupère dans un Cursor les valeurs correspondant à une ruche contenue dans la BDD (ici on sélectionne la ruche grâce à son nom)
        Cursor c = bdd.query(TABLE_RUCHES, new String[] {COL_ID, COL_UID, COL_NOM, COL_LOC}, COL_NOM + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToRuche(c);
    }

    //Cette méthode permet de convertir un cursor en une ruche
    private Ruche cursorToRuche(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé une ruche
        Ruche ruche = new Ruche();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        ruche.setId(c.getString(NUM_COL_ID));
        ruche.setUid(c.getString(NUM_COL_UID));
        ruche.setNom(c.getString(NUM_COL_NOM));
        ruche.setLocalisation(c.getString(NUM_COL_LOC));
        //On ferme le cursor
        c.close();

        //On retourne la ruche
        return ruche;
    }
}

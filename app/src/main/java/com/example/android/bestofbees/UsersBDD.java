package com.example.android.bestofbees;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Amandine on 20/06/2018.
 */

public class UsersBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "users.db";

    private static final String TABLE_USERS = "table_users";
    private static final String COL_ID = "ID";
    private static final String COL_PRENOM = "PRENOM";
    private static final String COL_NOM = "NOM";
    private static final String COL_MAIL = "Mail";
    private static final String COL_USERNAME = "USERNAME";
    private static final String COL_PASSWORD = "PASSWORD";
    private static final String COL_NBRUCHES = "Nb Ruches";;

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_NOM = 1;
    private static final int NUM_COL_PRENOM = 2;
    private static final int NUM_COL_MAIL = 3;
    private static final int NUM_COL_USERNAME = 4;
    private static final int NUM_COL_PASSWORD = 5;
    private static final int NUM_COL_NBRUCHES = 6;

    private SQLiteDatabase bdd;

    private DataBaseUsers maBaseSQLite;

    public UsersBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new DataBaseUsers(context, NOM_BDD, null, VERSION_BDD);
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

    public DataBaseUsers getMaBaseSQLite() {return maBaseSQLite;}

    public long insertUser(User user){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_ID, user.getIdUser());
        values.put(COL_PRENOM, user.getPrenom());
        values.put(COL_NOM, user.getNomUser());
        values.put(COL_MAIL,user.getMail());
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_NBRUCHES,user.getNbRuches());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_USERS, null, values);
    }

    public User getUserWithUsername(String nomUtilisateur){
        //Récupère dans un Cursor les valeurs correspondant à un user contenu dans la BDD (ici on sélectionne user grâce à son nom d'utilisateur)
        Cursor c = bdd.query(TABLE_USERS, new String[] {COL_ID, COL_NOM, COL_PRENOM, COL_MAIL, COL_USERNAME, COL_PASSWORD, COL_NBRUCHES},
                COL_USERNAME + " LIKE \"" + nomUtilisateur +"\"" , null, null, null, null);
        return cursorToUser(c);
    }

    public User getUserWithMPD(String mdp){
        //Récupère dans un Cursor les valeurs correspondant à un user contenu dans la BDD (ici on sélectionne user grâce à son mot de passe)
        Cursor c = bdd.query(TABLE_USERS, new String[] {COL_ID, COL_NOM, COL_PRENOM, COL_MAIL, COL_USERNAME, COL_PASSWORD, COL_NBRUCHES},
                 COL_PASSWORD + " LIKE \"" + mdp + "\"", null, null, null, null);
        return cursorToUser(c);
    }



    //Cette méthode permet de convertir un cursor en un user
    private User cursorToUser(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un user
       User user = new User();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        user.setIdUser(c.getInt(NUM_COL_ID));
        user.setNomUser(c.getString(NUM_COL_NOM));
        user.setPrenom(c.getString(NUM_COL_PRENOM));
        user.setMail(c.getString(NUM_COL_MAIL));
        user.setPassword(c.getString(NUM_COL_PASSWORD));
        user.setUsername(c.getString(NUM_COL_USERNAME));
        user.setNbRuches(c.getString(NUM_COL_NBRUCHES));
        //On ferme le cursor
        c.close();

        //On retourne le user
        return user;
    }

}

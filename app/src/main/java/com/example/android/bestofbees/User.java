package com.example.android.bestofbees;

import static com.example.android.bestofbees.R.id.localisation;
import static com.example.android.bestofbees.R.id.uid;

/**
 * Created by Amandine on 20/06/2018.
 */

public class User {
    private String nom;
    private String prenom;
    private String mail;
    private int id;
    private String username;
    private String password;
    private String nbRuches;

    public User(){};

    public User(int id, String nom, String prenom, String mail, String username, String password, String nbRuches) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.username = username;
        this.password = password;
        this.nbRuches = nbRuches;
    }

    public String getNomUser() {
        return nom;
    }

    public void setNomUser(String text) {
        this.nom = text;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getIdUser() {
        return id;
    }

    public void setIdUser(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNbRuches() {
        return nbRuches;
    }

    public void setNbRuches(String nbRuches) {
        this.nbRuches = nbRuches;
    }

    public Boolean isEqual(User u1){
        Boolean equal = false;
        if (id == u1.getIdUser() && nom.equals(u1.getNomUser()) && prenom.equals(u1.getPrenom()) && mail.equals(u1.getMail()) && username.equals(u1.getUsername())
                && password.equals(u1.getPassword()) && nbRuches.equals(u1.getNbRuches())) {
            equal = true;
        }
        return equal;
    }
}

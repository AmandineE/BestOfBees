package com.example.android.bestofbees;

import static android.R.attr.id;

/**
 * Created by Amandine on 18/06/2018.
 */

public class Ruche {
    private String text;
    private String localisation;
    private String uid;
    private String id;

    public Ruche(){};

    public Ruche(String text, String localisation, String uid, String id) {
        this.id = id;
        this.uid = uid;
        this.localisation = localisation;
        this.text = text;
    }

    public String getNom() {
        return text;
    }

    public void setNom(String text) {
        this.text = text;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


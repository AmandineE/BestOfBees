package com.example.android.bestofbees;

import static android.R.attr.id;
import static com.example.android.bestofbees.R.id.localisation;
import static com.example.android.bestofbees.R.id.uid;

/**
 * Created by Amandine on 24/06/2018.
 */

public class Data {
    private String date;
    private String heure;
    private String humidity;
    private String temperature;

    public Data(){};

    public Data(String date, String heure, String humidity, String temperature) {
        this.date = date;
        this.heure = heure;
        this.humidity = humidity;
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}

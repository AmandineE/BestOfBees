package com.example.android.bestofbees;


import android.content.Intent;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import static android.R.id.list;


public class Main3Activity extends AppCompatActivity {
    /*List<String> temp;
    List<String> humidite;
    List<String> jour;
    List<String> heure;*/
    PointsGraphSeries<DataPoint> seriesHum;
    PointsGraphSeries<DataPoint> seriesTemp;
    DataPoint[] dataPointsHum;
    DataPoint[] dataPointsTemp;
    int n=0;
    ArrayList<String> list_data;
    String uid;
    String string;
    int debut;
    String[] s1;
    String[] s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ImageButton menuButton = (ImageButton) findViewById(R.id.button_menu);
        menuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (n==0) {
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_menu);
                    linearLayout.setVisibility(View.VISIBLE);
                    n++;
                }
                else {
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_menu);
                    linearLayout.setVisibility(View.INVISIBLE);
                    n=0;
                }
            }
        });

        Button deconnectButton = (Button) findViewById(R.id.button_deconnect);
        deconnectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button ruchesButton = (Button) findViewById(R.id.button_ruches);
        ruchesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_menu);
                linearLayout.setVisibility(View.INVISIBLE);
                n=0;
                startActivity(intent);

            }
        });

        Button addRucheButton = (Button) findViewById(R.id.button_addRuche);
        addRucheButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, AddRucheActivity.class);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_menu);
                linearLayout.setVisibility(View.INVISIBLE);
                n=0;
                startActivity(intent);
            }
        });

        /*ImageButton userButton = (ImageButton) findViewById(R.id.button_user);
        userButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(Main3Activity.this, "User", Toast.LENGTH_SHORT).show();
            }
        });*/

        Button getDataButton = (Button) findViewById(R.id.button_getData);
        getDataButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, GetDataActivity.class);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_menu);
                linearLayout.setVisibility(View.INVISIBLE);
                n=0;
                startActivity(intent);
            }
        });

        //on récupère les données du bluetooth
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            //uid = extras.getString("uid");
            list_data = (ArrayList<String>) getIntent().getSerializableExtra("list_data");

            /*for (int i=0; i<list_data.size(); i++){
            if (list_data.get(i).equals("\n;;;")){
                debut = i + 1;
                break;
            }
        }*/

            debut = 6; //début de la récupération des données
            string = "";
            for (int i=debut; i<list_data.size();i++){
                string = string + list_data.get(i);
            }

            String[] separated1 = string.split(";");

            DataBDD dataBDD = new DataBDD(Main3Activity.this);
            dataBDD.open();

            int fin = separated1.length - 3;

            // on récupère les données sous la forme Date ; Heure ; Humidité ; Température \n Date ; Heure ; Humidité ; Température \n...
            // donc il faut recouper entre température et date de la ligne suivante

            for (int i=0; i<fin;i+=3) {
                if (i==0) {
                    s2 = separated1[i + 3].split("\n");
                    Data data = new Data(separated1[i], separated1[i + 1], separated1[i + 2], s2[0]);
                    dataBDD.insertData(data);
                }
                else {
                    Data data = new Data(s2[1], separated1[i+1], separated1[i + 2], separated1[i + 3].split("\n")[0]);
                    dataBDD.insertData(data);
                    //Toast.makeText(Main3Activity.this, "" + s2[1] + " " + separated1[i+1] + " " + separated1[i + 2] + " " + separated1[i + 3].split("\n")[0], Toast.LENGTH_SHORT).show();
                    s2 = separated1[i + 3].split("\n");
                }
            }

            dataBDD.close();
        }


        DataBaseData db = DataBaseData.getInstance(Main3Activity.this);
        List<Data> data = db.getAllData();

        GraphView graphHum = (GraphView) findViewById(R.id.graphHum);
        GraphView graphTemp = (GraphView) findViewById(R.id.graphTemp);

        dataPointsHum = new DataPoint[data.size()];
        dataPointsTemp = new DataPoint[data.size()];

        for (int i=0; i<data.size();i++){
            Float fhumidite = Float.parseFloat(data.get(i).getHumidity());
            Float ftemp = Float.parseFloat(data.get(i).getTemperature());

            String[] s = data.get(i).getHeure().split(":");
            Float fheure = Float.parseFloat(s[0]);
            Float fminute = Float.parseFloat(s[1]);
            Float fseconde = Float.parseFloat(s[2]);

            Float ftime = fheure + fminute/60 + fseconde/3600; //temps en heure

            dataPointsHum[i] = new DataPoint(ftime, fhumidite);  //DataPoint(abscisse, ordonnée)
            dataPointsTemp[i] = new DataPoint(ftime,ftemp);
        }

        // we get graph view instance
        //Cas où on récupère les données d'un fichier csv présent dans assets (ancienne version de l'appli)

/*        GraphView graphHum = (GraphView) findViewById(R.id.graphHum);
        GraphView graphTemp = (GraphView) findViewById(R.id.graphTemp);

        List<String[]> rows = new ArrayList<>();
        CSVReader csvReader = new CSVReader(Main3Activity.this, "data.csv");
        try {
            rows = csvReader.readCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

        temp = new ArrayList<>();
        humidite = new ArrayList<>();
        jour = new ArrayList<>();
        heure = new ArrayList<>();

        dataPointsHum = new DataPoint[rows.size()];
        dataPointsTemp = new DataPoint[rows.size()];

        for (int i = 0; i < rows.size(); i++) {
            String string = rows.get(i)[0];

            String[] separated = string.split(";");
            jour.add(separated[0]);
            heure.add(separated[1]);
            humidite.add(separated[2]);
            temp.add(separated[3]);

            Float fhumidite= Float.parseFloat(humidite.get(i));
            Float ftemp= Float.parseFloat(temp.get(i));

            String[] separatedHeure = heure.get(i).split(":");
            Float fheure = Float.parseFloat(separatedHeure[0]);
            Float fminute = Float.parseFloat(separatedHeure[1]);
            Float fseconde = Float.parseFloat(separatedHeure[2]);

            Float ftime = fheure + fminute/60 + fseconde/3600; //temps en heure

            dataPointsHum[i] = new DataPoint(ftime, fhumidite);  //DataPoint(abscisse, ordonnée)
            dataPointsTemp[i] = new DataPoint(ftime,ftemp);


        }*/

        seriesHum = new PointsGraphSeries<>(dataPointsHum);
        graphHum.addSeries(seriesHum);
        seriesHum.setShape(PointsGraphSeries.Shape.POINT); //forme des points
        seriesHum.setSize(5); //taille des points
        seriesHum.setColor(Color.RED);
        graphHum.getGridLabelRenderer().setHorizontalAxisTitle("Temps (heure)");
        graphHum.getGridLabelRenderer().setVerticalAxisTitle("Humidité (%)");

        seriesTemp = new PointsGraphSeries<>(dataPointsTemp);
        graphTemp.addSeries(seriesTemp);
        seriesTemp.setShape(PointsGraphSeries.Shape.POINT); //forme des points
        seriesTemp.setSize(5); //taille des points
        seriesTemp.setColor(Color.BLUE);
        graphTemp.getGridLabelRenderer().setHorizontalAxisTitle("Temps (heure)");
        graphTemp.getGridLabelRenderer().setVerticalAxisTitle("Température (°C)");


    }
}

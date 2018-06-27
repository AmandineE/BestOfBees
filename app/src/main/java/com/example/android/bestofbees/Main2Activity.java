package com.example.android.bestofbees;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.color.transparent;
import static android.os.Build.VERSION_CODES.M;

public class Main2Activity extends AppCompatActivity {
    ListView mListView;
    List<Ruche> ruches;
    int n=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button ruchesButton = (Button) findViewById(R.id.button_ruches);
        ruchesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Main2Activity.class);
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
                Intent intent = new Intent(Main2Activity.this, AddRucheActivity.class);
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
                Toast.makeText(Main2Activity.this, "User", Toast.LENGTH_SHORT).show();
            }
        });*/

        Button getDataButton = (Button) findViewById(R.id.button_getData);
        getDataButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, GetDataActivity.class);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_menu);
                linearLayout.setVisibility(View.INVISIBLE);
                n=0;
                startActivity(intent);
            }
        });

        mListView = (ListView) findViewById(R.id.list_view);

        afficherListeRuches();


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Ruche ruche = ruches.get(position);
                String uid = ruche.getUid();

                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                //intent.putExtra("uid", uid);
                startActivity(intent);


                // à faire : aouter les icônes "modifier" et "supprimer" pour chaque ruche

                /*LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_ruche);
                linearLayout.setVisibility(View.VISIBLE);

                ListView listView = (ListView) findViewById(R.id.list_view);
                listView.setVisibility(View.INVISIBLE);

                Button infosButton = (Button) findViewById(R.id.button_infos);
                infosButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Main2Activity.this, InfosRuchesActivity.class);
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_ruche);
                        linearLayout.setVisibility(View.INVISIBLE);

                        ListView listView = (ListView) findViewById(R.id.list_view);
                        listView.setVisibility(View.VISIBLE);
                        startActivity(intent);
                    }
                });

                Button dataButton = (Button) findViewById(R.id.button_data);
                dataButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                        startActivity(intent);

                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_ruche);
                        linearLayout.setVisibility(View.INVISIBLE);

                        ListView listView = (ListView) findViewById(R.id.list_view);
                        listView.setVisibility(View.VISIBLE);
                    }
                });*/

            }
        });

    }

    private List<Ruche> genererRuches(){

        DataBaseRuches db1 = DataBaseRuches.getInstance(Main2Activity.this);

        List<Ruche> ruche = db1.getAllRuches();

        return ruche;
    }

    private void afficherListeRuches(){
        ruches = genererRuches();

        ListAdapter adapter = new ListAdapter(Main2Activity.this, ruches);
        mListView.setAdapter(adapter);
    }
}

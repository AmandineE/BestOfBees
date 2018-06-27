package com.example.android.bestofbees;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import static android.R.attr.path;

public class AddRucheActivity extends AppCompatActivity {
    EditText nom;
    EditText localisation;
    EditText uid;
    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ruche);

        //pour modifier une ruche
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            String nom1 = extras.getString("nom");
            String loc1 = extras.getString("localisation");
            String uid1 = extras.getString("uid");
            String id1 = extras.getString("id");

            nom = (EditText) findViewById(R.id.nom_ruche);
            nom.setText(nom1);

            localisation = (EditText) findViewById(R.id.localisation);
            localisation.setText(loc1);

            uid = (EditText) findViewById(R.id.uid);
            uid.setText(uid1);

            id = (EditText) findViewById(R.id.id_ruche);
            id.setText(id1);
        }

        Button enterButton = (Button) findViewById(R.id.button_enregistrer);
        enterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                id = (EditText) findViewById(R.id.id_ruche);
                //int idRuche = Integer.parseInt(id.getText().toString());
                String idRuche = id.getText().toString();

                nom = (EditText) findViewById(R.id.nom_ruche);
                String nomRuche = nom.getText().toString();

                localisation = (EditText) findViewById(R.id.localisation);
                String locRuche = localisation.getText().toString();

                uid = (EditText) findViewById(R.id.uid);
                String uidRuche = uid.getText().toString();

                RuchesBDD ruchesBDD = new RuchesBDD(AddRucheActivity.this);


                Ruche ruche = new Ruche(nomRuche, locRuche, uidRuche, idRuche);

                ruchesBDD.open();
                ruchesBDD.insertRuche(ruche);

                //ruchesBDD.getMaBaseSQLite().onUpgrade(ruchesBDD.getBDD(),1,2);

                Intent intent = new Intent(AddRucheActivity.this, Main2Activity.class);
                startActivity(intent);

            }
        });
    }
}

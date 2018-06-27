package com.example.android.bestofbees;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import static android.R.attr.id;
import static com.example.android.bestofbees.R.id.localisation;
import static com.example.android.bestofbees.R.id.uid;

public class CompteUserActivity extends AppCompatActivity {
    EditText nom;
    EditText prenom;
    EditText mail;
    EditText user;
    EditText mdp;
    EditText nbRuches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_user);


        Button enterButton = (Button) findViewById(R.id.button_enregistrer);
        enterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nom = (EditText) findViewById(R.id.text_nom);
                String nomUser = nom.getText().toString();

                prenom = (EditText) findViewById(R.id.text_prenom);
                String prenomUser = prenom.getText().toString();

                mail = (EditText) findViewById(R.id.text_mail);
                String mailUser = mail.getText().toString();

                user = (EditText) findViewById(R.id.text_user);
                String userUser = user.getText().toString();

                mdp = (EditText) findViewById(R.id.text_mdp);
                String mdpUser = mdp.getText().toString();

                nbRuches = (EditText) findViewById(R.id.text_nb_ruche);
                String nbRucheUser = nbRuches.getText().toString();


                UsersBDD usersBDD = new UsersBDD(CompteUserActivity.this);

                //DataBaseUsers db1 = DataBaseUsers.getInstance(CompteUserActivity.this);
                int id = usersBDD.getMaBaseSQLite().getSize();

                //int id = db1.getSize() + 1;

                User user = new User(id, nomUser, prenomUser, mailUser, userUser, mdpUser, nbRucheUser);

                usersBDD.open();
                usersBDD.insertUser(user);

                Toast.makeText(CompteUserActivity.this, "user id: " + id, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CompteUserActivity.this, Main2Activity.class);
                startActivity(intent);

            }
        });
    }
}

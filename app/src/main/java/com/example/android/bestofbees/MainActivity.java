package com.example.android.bestofbees;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText user;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password = (EditText) findViewById(R.id.password);
        password.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        //pour remettre à 0 la base de donnée des ruches

        /*RuchesBDD ruchesBDD = new RuchesBDD(MainActivity.this);


        Ruche ruche = new Ruche("beehive", "paris", "ff", "1");

        ruchesBDD.open();
        ruchesBDD.insertRuche(ruche);

        ruchesBDD.getMaBaseSQLite().onUpgrade(ruchesBDD.getBDD(),1,2);*/

        //pour remettre à 0 la base de donnée des données envoyées

        /*DataBDD dataBDD = new DataBDD(MainActivity.this);
        dataBDD.open();
        Data data = new Data("date", "heure","humidite","temperature");
        dataBDD.insertData(data);
        dataBDD.getMaBaseSQLite().onUpgrade(dataBDD.getBDD(),1,2);*/

        Button enterButton = (Button) findViewById(R.id.button_enter);
        enterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String pas = password.getText().toString();

                user = (EditText) findViewById(R.id.user);
                String us = user.getText().toString();

                // à faire : mettre dans la base de donnée des Users les noms utilisateurs et mots de passe et vérifier si ce que rentre l'utilisateur est bien dans la base de donnée

                /*UsersBDD usersBDD = new UsersBDD(MainActivity.this);
                User user1 = usersBDD.getUserWithUsername(us);
                User user2 = usersBDD.getUserWithMPD(pas);

                if (user1.isEqual(user2)){
                    password.setText(null);
                    user.setText(null);
                    password.setHint("Mot de passe");
                    user.setHint("Nom d'utilisateur");
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                }
                else{
                    password.setText(null);
                    password.setHint("Password)");
                    user.setText(null);
                    user.setHint("Password)");
                    Toast.makeText(MainActivity.this, "Wrong password or user", Toast.LENGTH_SHORT).show();
                }*/

                if (pas.equals("1234") && (us.equals("rp"))) {
                    password.setText(null);
                    user.setText(null);
                    password.setHint("Mot de passe");
                    user.setHint("Nom d'utilisateur");
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);

                }
                else{
                    password.setText(null);
                    password.setHint("Mot de passe");
                    user.setText(null);
                    user.setHint("Nom d'utilisateur");
                    Toast.makeText(MainActivity.this, "Wrong password or user", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*Button userButton = (Button) findViewById(R.id.button_new_user);
        userButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompteUserActivity.class);
                startActivity(intent);
            }
        });*/
    }

    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }
            public char charAt(int index) {
                return '*'; // This is the important part
            }
            public int length() {
                return mSource.length(); // Return default
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    };
}

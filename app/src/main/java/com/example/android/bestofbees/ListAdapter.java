package com.example.android.bestofbees;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

import static android.R.attr.button;
import static android.R.attr.manageSpaceActivity;
import static android.R.attr.x;

/**
 * Created by Amandine on 18/06/2018.
 */

public class ListAdapter extends ArrayAdapter<Ruche> {
    private Context mcontext;
    RucheViewHolder viewHolder;

    public ListAdapter(Context context, List<Ruche> ruches) {
        super(context, 0, ruches);
        this.mcontext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_ruches,parent, false);
        }

        viewHolder = (RucheViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new RucheViewHolder();

            viewHolder.linearLayout = (LinearLayout)convertView.findViewById(R.id.linear_forme);

            //viewHolder.modify = (ImageButton) convertView.findViewById(R.id.modify);
            //viewHolder.delete = (ImageButton) convertView.findViewById(R.id.delete);

            viewHolder.button = (TextView) convertView.findViewById(R.id.button_nom);
            viewHolder.button2 = (TextView) convertView.findViewById(R.id.button_loc);
            viewHolder.button3 = (TextView) convertView.findViewById(R.id.button_uid);
            viewHolder.button4 = (TextView) convertView.findViewById(R.id.button_id);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Ruche> ruche
        Ruche ruche = getItem(position);

        /*viewHolder.modify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nom = viewHolder.button.getText().toString();
                String localisation = viewHolder.button2.getText().toString();
                String uid = viewHolder.button3.getText().toString();
                String id = viewHolder.button4.getText().toString();

                Intent myIntent = new Intent(mcontext, AddRucheActivity.class);
                myIntent.putExtra("nom", nom);
                myIntent.putExtra("localisation",localisation);
                myIntent.putExtra("uid", uid);
                myIntent.putExtra("id", id);
                mcontext.startActivity(myIntent);
            }
        });*/

        int color;
        Random rd = new Random();
        int x=rd.nextInt(8);
        switch (x){
            case 0: color = Color.parseColor("#FF80AB"); break;
            case 1: color = Color.parseColor("#90CAF9"); break;
            case 2: color = Color.parseColor("#CE93D8"); break;
            case 3: color = Color.parseColor("#A5D6A7"); break;
            case 4: color = Color.parseColor("#FFB74D"); break;
            case 5 : color = Color.parseColor("#BCAAA4"); break;
            case 6: color = Color.parseColor("#26C6DA"); break;
            case 7 : color = Color.parseColor("#EF9A9A"); break;
            default: color = Color.parseColor("#9FA8DA"); break;
        }

        viewHolder.linearLayout.setBackgroundColor(color);

        viewHolder.button.setText(ruche.getNom());

        viewHolder.button2.setText(ruche.getLocalisation());

        viewHolder.button3.setText(ruche.getUid());

        viewHolder.button4.setText(ruche.getId());

        return convertView;
    }

    private class RucheViewHolder{
        public LinearLayout linearLayout;
        //public ImageButton modify;
        //public ImageButton delete;
        public TextView button;
        public TextView button2;
        public TextView button3;
        public TextView button4;
    }
}

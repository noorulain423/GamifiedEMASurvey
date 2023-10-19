package com.example.gamifiedsurvey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

    class MyEmpscrAdapter extends ArrayAdapter<String> {
        ArrayList<String> listempaddress;
        ArrayList<String> listempstes;
        ArrayList<String> listempusage;

        Context c;

        //    public MyEmpscrAdapter(Context context, ArrayList<String> allproductlist, ArrayList<String> list,ArrayList<String> listdesignation){
        public MyEmpscrAdapter(Context context, ArrayList<String> allproductlist, ArrayList<String> liststeps, ArrayList<String> listusage) {
            super(context, R.layout.usersimple_layout, allproductlist);
            this.listempaddress = allproductlist;
            this.listempstes = liststeps;
            this.listempusage = listusage;
            this.c = context;
            notifyDataSetChanged();





        }


        @Override
        public View getView(final int position,@Nullable View convertView,@NonNull ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View v = inflater.inflate(R.layout.usersimple_layout, parent, false);
            TextView tvforuseraddress = v.findViewById(R.id.txt_loc);
            TextView tvforusesteps = v.findViewById(R.id.txt_steps);
            TextView tvforuserusage = v.findViewById(R.id.txt_usage);
            String valueaddress = listempaddress.get(position);

            String valuesteps = listempstes.get(position);
            String valueusage = listempusage.get(position);

            tvforuseraddress.setText(valueaddress + "");

            tvforuserusage.setText( valueusage);
            tvforusesteps.setText(valuesteps + "");
            return v;

        }
    }



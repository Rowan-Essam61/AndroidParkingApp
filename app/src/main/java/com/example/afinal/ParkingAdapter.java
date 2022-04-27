package com.example.afinal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
public class ParkingAdapter<P> extends ArrayAdapter<parkingspace> {


        public ParkingAdapter(Context context, ArrayList<parkingspace> news) {
            super(context, 0, news);
        }




        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View listItem = convertView;
            if (listItem == null) {
                listItem = LayoutInflater.from(getContext()).inflate(R.layout.parkingitem, parent, false);
            }
            parkingspace prod = getItem(position);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.parkingitem,null);

            TextView secdata = (TextView) convertView.findViewById(R.id.name);
            String seci = prod.getName();
            secdata.setText(seci);



            TextView img = (TextView) convertView.findViewById(R.id.img);
            String imgi = prod.getImg();
            Resources res = getContext().getResources();
            int resID = res.getIdentifier(imgi , "drawable", getContext().getPackageName());
            img.setBackgroundResource(resID);



            TextView price = (TextView) convertView.findViewById(R.id.price);
            String datei = prod.getPrice();
            price.setText(datei+" EGP/H");

            return convertView;




        }

    }







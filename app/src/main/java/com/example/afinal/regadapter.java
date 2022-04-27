package com.example.afinal;

import android.content.Context;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;

import android.widget.ArrayAdapter;


import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class regadapter<P> extends ArrayAdapter<registeration> {


    public regadapter(Context context, ArrayList<registeration> news) {
        super(context, 0, news);
    }


    ImageView qrCodeIV;
    ImageView hide;
    ImageView show ;
    TableLayout textView;
    LinearLayout header, headertop;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.regestration, parent, false);
        }
        registeration prod = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.regestration, null);

        TextView secdata = (TextView) convertView.findViewById(R.id.name);
        String seci = prod.getName();
        secdata.setText(seci);

        TextView address = (TextView) convertView.findViewById(R.id.slot);
        String quan = prod.getSlot();
        address.setText(quan);


        TextView price = (TextView) convertView.findViewById(R.id.date);
        String datei = prod.getDate();
        price.setText(datei);

        TextView address1 = (TextView) convertView.findViewById(R.id.checkin);
        String quan1 = prod.getCheckin();
        address1.setText(quan1);

        headertop = convertView.findViewById(R.id.headertop);
        header = convertView.findViewById(R.id.header);
        TextView price1 = (TextView) convertView.findViewById(R.id.checkout);
        String datei1 = prod.getCheckout();
        price1.setText(datei1);
        TextView secdata1 = (TextView) convertView.findViewById(R.id.status);
        String seci1 = prod.getStatus();
        secdata1.setText(seci1);
        Log.i("adapteeerrr",prod.getStatus());
        TextView statusss = (TextView) convertView.findViewById(R.id.status1);
        textView = (TableLayout) convertView.findViewById(R.id.tabledata);
        hide = (ImageView) convertView.findViewById(R.id.imageView1);
        show = (ImageView) convertView.findViewById(R.id.imageView2);

        qrCodeIV = convertView.findViewById(R.id.overlay);

        if (seci1.equals("completed")) {

            Drawable image = (Drawable) getContext().getResources().getDrawable(R.drawable.ic_baseline_where_to_vote_24);
            statusss.setBackground(image);
        } else if (seci1.equals("cancelled")) {

            Drawable image = (Drawable) getContext().getResources().getDrawable(R.drawable.ic_baseline_wrong_location_24);
            statusss.setBackground(image);




        } else if (seci1.equals("checkin")) {

            Drawable image = (Drawable) getContext().getResources().getDrawable(R.drawable.ic_baseline_pending_24);
            statusss.setBackground(image);
        } else if (seci1.equals("pending")) {

            Drawable image = (Drawable) getContext().getResources().getDrawable(R.drawable.ic_baseline_qr_code_24);
            statusss.setBackground(image);

            pending(prod.getUser_id(),prod.getSlot());

        }

        return convertView;

    }

    public void pending(String id, String slot) {
        Bitmap bitmap;

        QRGEncoder qrgEncoder;
        String dataqr;
        dataqr = slot + id;
        Log.i("idddddinregadapteer", id);
        Log.i("idddddinregadaptesloottter", slot);



            Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);


            int width = point.x;
            int height = point.y;

            int dimen = width < height ? width : height;
            dimen = dimen * 3 / 4;

            qrgEncoder = new QRGEncoder(dataqr, null, QRGContents.Type.TEXT, dimen);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrCodeIV.setImageBitmap(bitmap);

            } catch (WriterException e) {
                Log.e("Tag", e.toString());
            }

        }


    }




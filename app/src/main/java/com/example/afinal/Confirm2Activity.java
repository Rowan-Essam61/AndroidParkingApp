package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.WriterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Confirm2Activity extends AppCompatActivity {
    private ImageView qrCodeIV;
    String URL = "http://192.168.220.207:8000/api/registration/user/";
    String URL1 = "http://192.168.220.207:8000/api/registration/";
    int parkingid, checkin, checkout, checkinm, checkoutm;
    String dateday, day, slott, iduser, regid, checkintt;

    String dataqr, carnumber;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    TextView textView;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm2);
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        iduser = sharedPreferences.getString("id", "");
        parkingid = (int) getIntent().getSerializableExtra("parkingspaceid");
        checkin = (int) getIntent().getSerializableExtra("checkinhour");
        checkout = (int) getIntent().getSerializableExtra("checkouthour");
        checkinm = (int) getIntent().getSerializableExtra("checkinmin");
        checkoutm = (int) getIntent().getSerializableExtra("checkoutmin");
        slott = (String) getIntent().getSerializableExtra("id");
        dateday = (String) getIntent().getSerializableExtra("daydate");
        day = (String) getIntent().getSerializableExtra("day");
        carnumber = (String) getIntent().getSerializableExtra("carnumber");
        dataqr = slott + iduser;
        Log.i("dataaa", slott + "   " + dateday + "" + day + checkin + "  " + checkout + "  " + parkingid);


        //QR Codee
        qrCodeIV = findViewById(R.id.idIVQrcode);

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();

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

        //counteer

        Calendar calendar = Calendar.getInstance();
        Date td0 = calendar.getTime();
        int now = td0.getHours();
        int min = td0.getMinutes();


        Log.i("nowcheck", String.valueOf(checkin));
        Log.i("mincheck", String.valueOf(checkinm));
        Log.i("now", String.valueOf(now));
        Log.i("min", String.valueOf(min));
        textView = findViewById(R.id.countdown);
        int couth = checkin - now;
        int couth2 = couth * 60;
        int countmin = checkinm - min;
        int counter = couth2 + countmin;
        int sec = counter * 60;
        sec = sec * 1000;
        // Time is in millisecond so 50sec = 50000 I have used
        // countdown Interveal is 1sec = 1000 I have used
        new CountDownTimer(sec, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                textView.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));

//                SharedPreferences.Editor myEdit = sharedPreferences.edit();
//
//                myEdit.putString("counter", String.valueOf(textView.getText().toString()));
              Log.i("xxxxxxxxxxxx",String.valueOf(textView.getText().toString()));
//            myEdit.commit();
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                textView.setText("00:00:00");
                parseApiData(regid);

            }
        }.start();


        postUserData();

    }


    public void parseApiData(String idd) {
//        Log.i("regidd", idd);


        StringRequest s = new StringRequest(Request.Method.GET, URL1 + idd, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("resultregsss", response);
                try {
                    JSONArray o = new JSONArray(response);
                    if (o.length() > 0) {
                        for (int i = 0; i < o.length(); i++) {
                            JSONObject reg = o.getJSONObject(i);
                            String status = reg.getString("status");
                            if (status == "pending") {
                                postregData();
                                postslotData("available");
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("nelaa", String.valueOf(error));

            }
        });
        RequestQueue r = Volley.newRequestQueue(this);
        r.add(s);

    }


    private void postUserData() {
        // url to post our data
        String url ="http://192.168.220.207:8000/api/registration/insert";

        RequestQueue queue = Volley.newRequestQueue(Confirm2Activity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject respObj = new JSONObject(response);

                    JSONObject respObj2 = respObj.getJSONObject("registration");
                    regid = String.valueOf(respObj2.getString("id"));
                    Log.i("postUserDatavvvvvvv", regid);

                    myEdit.putString("regid", regid);
                    myEdit.commit();
                    ////////////////////////////////////////////////////
                    postslotData("unavailable");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("eroororr", String.valueOf(error));
            }
        }) {
            @Override
            protected Map getParams() {

                Map<String, Object> params = new HashMap<>();
                params.put("user_id", iduser.toString());
                params.put("car_id", carnumber.toString());
                params.put("date", dateday.toString());
                params.put("status", "pending");
                params.put("slot_name", slott.toString());
                params.put("leave_time", checkout + ":" + checkoutm);
                params.put("checkin_time", checkin + ":" + checkinm);
                params.put("parking_id", String.valueOf(getIntent().getSerializableExtra("parkingspaceid")));
                params.put("day", day);
                Log.i("param", String.valueOf(params));

                return params;
            }
        };


        queue.add(request);
    }


    private void postregData() {
        // url to post our data
        String url = "http://192.168.220.207:8000/api/registration/update/";

        RequestQueue queue = Volley.newRequestQueue(Confirm2Activity.this);
        Log.i("Sloottt", slott);
        StringRequest request = new StringRequest(Request.Method.POST, url + regid, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respObj = new JSONObject(response);
                    Log.i("response", response);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("eroororr", String.valueOf(error));
            }
        }) {
            @Override
            protected Map getParams() {

                Map<String, Object> params = new HashMap<>();
                params.put("user_id", iduser.toString());
                params.put("car_id", carnumber.toString());
                params.put("date", dateday.toString());
                params.put("status", "canceled");
                params.put("slot_name", slott.toString());
                params.put("leave_time", checkout + ":" + checkoutm);
                params.put("checkin_time", checkin + ":" + checkinm);
                params.put("parking_id", String.valueOf(getIntent().getSerializableExtra("parkingspaceid")));
                params.put("day", day);

                Log.i("param", String.valueOf(params));

                return params;
            }
        };

        queue.add(request);
    }

    private void postslotData(String status) {
        // url to post our data
        String url = "http://192.168.220.207:8000/api/parkingslot/updatestatus/";

        RequestQueue queue = Volley.newRequestQueue(Confirm2Activity.this);
        Log.i("Sloottt", slott);
        StringRequest request = new StringRequest(Request.Method.POST, url + slott, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respObj = new JSONObject(response);
                    Log.i("response", response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("eroororr", String.valueOf(error));
            }
        }) {
            @Override
            protected Map getParams() {

                Map<String, Object> params = new HashMap<>();

                params.put("status", status);

                params.put("parking_id", String.valueOf(getIntent().getSerializableExtra("parkingspaceid")));
                Log.i("param", String.valueOf(params));

                return params;
            }
        };

        queue.add(request);
    }

    public void gohome(View V) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);

    }

    public void viewreg(View V) {
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);

    }

}


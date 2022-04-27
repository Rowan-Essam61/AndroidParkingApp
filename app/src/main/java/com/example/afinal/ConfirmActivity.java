package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ConfirmActivity extends AppCompatActivity {
    String id;
    TextView car;
    TextView user;
    TextView slot;
    TextView time;
    TextView date;
    TextView position;

    String URL1 = "http://192.168.220.207:8000/api/parkingspace/";
    String URL = "http://192.168.220.207:8000/api/user/car/";
    int parkingid, checkin, checkout, checkinm, checkoutm;
    String dateday, day, slott;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Intent intent = getIntent();
        id = intent.getStringExtra("slot");
        parkingid = (int) getIntent().getSerializableExtra("parkingspaceid");
        checkin = (int) getIntent().getSerializableExtra("checkinhour");
        checkout = (int) getIntent().getSerializableExtra("checkouthour");
        checkinm = (int) getIntent().getSerializableExtra("checkinmin");
        checkoutm = (int) getIntent().getSerializableExtra("checkoutmin");
        slott = (String) getIntent().getSerializableExtra("slot");
        dateday = (String) getIntent().getSerializableExtra("daydate");
        day = (String) getIntent().getSerializableExtra("day");
        Log.i("confirmmm", String.valueOf(parkingid));
        user = (TextView) findViewById(R.id.name);
        position = (TextView) findViewById(R.id.position);
        time = (TextView) findViewById(R.id.time);
        time.setText(checkin + ":" + checkinm);

        slot = (TextView) findViewById(R.id.slot);
        slot.setText(slott);
        date = (TextView) findViewById(R.id.date);
        date.setText(dateday);
        car = (TextView) findViewById(R.id.carnum);

        parseApicar();
        parseApiparking();
    }


    public void parseApicar() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String idd = sh.getString("id", "");
        Log.i("iddd", idd);

        StringRequest s = new StringRequest(Request.Method.GET, URL + idd, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {
                    JSONObject o = new JSONObject(response);
                    JSONArray userr = o.getJSONArray("user");
                    JSONArray car2 = o.getJSONArray("car");
                    if (userr.length() > 0) {
                        for (int i = 0; i < userr.length(); i++) {
                            JSONObject user2 = userr.getJSONObject(i);
                            JSONObject car3 = car2.getJSONObject(i);
                            String carnum1 = car3.getString("id");
                            car.setText(carnum1);
                            String username = user2.getString("username");
                            user.setText(username);

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

    public void parseApiparking() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String idd = sh.getString("id", "");
        Log.i("iddd", idd);

        StringRequest s = new StringRequest(Request.Method.GET, URL1 + parkingid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {

                    JSONArray x = new JSONArray(response);
                    if (x.length() > 0) {
                        for (int i = 0; i < x.length(); i++) {
                            JSONObject parkingspace = x.getJSONObject(i);
                            Log.i("parkingspace", String.valueOf(parkingspace.getString("name")));
                            position.setText(parkingspace.getString("name"));


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

    public void confirm(View V) {
        Log.i("slot", id);
        Intent intent = new Intent(getApplicationContext(), Confirm2Activity.class);

        intent.putExtra("parkingspaceid", (int) getIntent().getSerializableExtra("parkingspaceid"));
        intent.putExtra("checkinhour", checkin);
        intent.putExtra("checkouthour", checkout);
        intent.putExtra("checkinmin", checkinm);
        intent.putExtra("checkoutmin", checkoutm);
        intent.putExtra("daydate", dateday);
        intent.putExtra("day", day);
        intent.putExtra("id", id);
        intent.putExtra("parkingid", parkingid);
        intent.putExtra("carnumber", car.getText());


        startActivity(intent);
    }
}
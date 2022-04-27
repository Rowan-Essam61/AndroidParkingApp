package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    String URL = "http://192.168.220.207:8000/api/user/";
    String URL1 = "http://192.168.220.207:8000/api/usercar/";
    EditText name;
    EditText email;
    EditText phone;
    EditText gender;
    EditText nid;
    EditText address;
    EditText dob;
    TextView back;
    EditText carnum;
    EditText category;
    EditText type;
    EditText color;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        name = (EditText) findViewById(R.id.name);
//        email = (EditText) findViewById(R.id.email);
//        phone = (EditText) findViewById(R.id.phone);
//        address = (EditText) findViewById(R.id.address);
//        nid = (EditText) findViewById(R.id.nid);
//        dob = (EditText) findViewById(R.id.dob);
//        gender = (EditText) findViewById(R.id.gender);
//
//        carnum = (EditText) findViewById(R.id.carnum);
//        category = (EditText) findViewById(R.id.category);
//        type = (EditText) findViewById(R.id.type);
//        color = (EditText) findViewById(R.id.color);
        back = (TextView) findViewById(R.id.back);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Personal Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Car Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Registrations"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.view.setAlpha((float) 0.5);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tab.view.setAlpha((float) 0.5);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setAlpha((float) 1);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });


//
//    parseApiData();
//        parseApicar();
    }


    public void parseApiData() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String id = sh.getString("id", "");
        Log.i("iddd", id);

        StringRequest s = new StringRequest(Request.Method.GET, URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {
                    JSONArray x = new JSONArray(response);

                    if (x.length() > 0) {
                        for (int i = 0; i < x.length(); i++) {
                            JSONObject user = x.getJSONObject(i);
                            int id1 = user.getInt("id");
                            String name1 = user.getString("username");
                            String email1 = user.getString("email");
                            String address1 = user.getString("address");
                            String phone1 = user.getString("phone");
                            String gender1 = user.getString("gender");
                            String dob1 = user.getString("dob");
                            dob.setText(dob1);
                            gender.setText(gender1);
                            name.setText(name1);
                            email.setText(email1);
                            address.setText(address1);
                            phone.setText(phone1);
                            nid.setText(id);
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

    public void parseApicar() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String id = sh.getString("id", "");
        Log.i("iddd", id);

        StringRequest s = new StringRequest(Request.Method.GET, URL1 + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {
                    JSONArray x = new JSONArray(response);

                    if (x.length() > 0) {
                        for (int i = 0; i < x.length(); i++) {
                            JSONObject car = x.getJSONObject(i);
                            String carnum1 = car.getString("id");
                            carnum.setText(carnum1);
                            String category1 = car.getString("category");
                            category.setText(category1);
                            String type1 = car.getString("category");
                            type.setText(type1);
                            String color1 = car.getString("color");
                            color.setText(color1);


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

}
package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Signup3Activity extends AppCompatActivity {
    user user;
    user car;
    private TextView username;
    private TextView email;
    private TextView phone;
    private TextView address;
    private TextView dob;
    private TextView nid;
    private TextView gender;
    private TextView carnum;
    private TextView category;
    private TextView type;
    private TextView color;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);

        username = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        dob = (TextView) findViewById(R.id.dob);
        nid = (TextView) findViewById(R.id.nid);
        gender = (TextView) findViewById(R.id.gender);
        address = (TextView) findViewById(R.id.address);
        carnum = (TextView) findViewById(R.id.carnum);
        category = (TextView) findViewById(R.id.category);
        type = (TextView) findViewById(R.id.type);
        color = (TextView) findViewById(R.id.color);
        user = (user) getIntent().getSerializableExtra("user");
        car = (user) getIntent().getSerializableExtra("car");
        Log.i("user", user.username);
     //   Log.i("car", car.carnumber);
        username.setText(user.username);
        email.setText(user.email);
        phone.setText(user.phone);
        dob.setText(user.dob);
        nid.setText(user.nid);
        gender.setText(user.gender);
        address.setText(user.address);
        carnum.setText(car.carnumber);
        category.setText(car.category);
        type.setText(car.type);
        color.setText(car.colors);
        mAuth = FirebaseAuth.getInstance();

    }

    public void gohome(View V) {

        postUserData();
       // sendVerificationEmail();
        Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
        startActivity(intent);
    }


    private void postUserData() {

        mAuth
                .createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {


                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // email sent
                                                // after email is sent just logout the user and finish this activity
                                                FirebaseAuth.getInstance().signOut();

                                                finish();




                                            }
                                            else
                                            {
                                                // email not sent, so display message and restart the activity or do whatever you wish to do

                                                //restart this activity
                                                overridePendingTransition(0, 0);
                                                finish();
                                                overridePendingTransition(0, 0);
                                                startActivity(getIntent());

                                            }
                                        }
                                    });



                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });


        // url to post our data
        String url = "http://192.168.220.207:8000/api/user/insert";

        RequestQueue queue = Volley.newRequestQueue(Signup3Activity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject respObj = new JSONObject(response);
                    Log.i("response", response);
                    postCarData();

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


                params.put("id", user.nid);
                params.put("username", user.username);
                params.put("address", user.address);
                params.put("email", user.email);
                params.put("gender", user.gender);
                params.put("phone", user.phone);
                params.put("role", "carowner");
                params.put("dob", user.dob);
                params.put("password", user.password);

                Log.i("param", String.valueOf(params));
                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void postCarData() {
        // url to post our data
        String url = "http://192.168.220.207:8000/api/car/insert";

        RequestQueue queue = Volley.newRequestQueue(Signup3Activity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
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

                Map<String, String> param = new HashMap<>();


                param.put("id", car.carnumber);
                param.put("category", car.category);
                param.put("type", car.type);
                param.put("color", car.colors);
                param.put("user_id", user.nid);


                Log.i("param", String.valueOf(param));
                // at last we are
                // returning our params.
                return param;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}


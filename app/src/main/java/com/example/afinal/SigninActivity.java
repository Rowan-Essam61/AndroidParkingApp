package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SigninActivity extends AppCompatActivity {
    String URL = "http://192.168.220.207:8000/api/useremail/";
    EditText email;
    EditText password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        ImageView showpass = (ImageView) findViewById(R.id.imageView2);
        ImageView hidepass = (ImageView) findViewById(R.id.imageView1);


        //show and hide password
        showpass.setOnClickListener(v -> {
            showpass.setVisibility(View.GONE);
            hidepass.setVisibility(View.VISIBLE);
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        });
        hidepass.setOnClickListener(v -> {
            hidepass.setVisibility(View.GONE);
            showpass.setVisibility(View.VISIBLE);
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        });



    }

    public void signup(View v) {
        Intent i = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(i);
    }

    public void forget(View v) {
        Intent i = new Intent(getApplicationContext(), ForgetActivity.class);
        startActivity(i);
    }

    public void home(View v) {
        parseApiData();

    }


    public void parseApiData() {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });
        StringRequest s = new StringRequest(Request.Method.GET, URL + email.getText(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {
                    JSONObject o = new JSONObject(response);
                    JSONArray x = o.getJSONArray("user");
                    if (x.length() > 0) {
                        for (int i = 0; i < x.length(); i++) {
                            JSONObject user = x.getJSONObject(i);

                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("id",user.getString("id"));
                               Log.i("iddddddddddd",user.getString("id"));
                                myEdit.commit();
                                Intent z = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(z);
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
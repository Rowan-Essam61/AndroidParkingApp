package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ChangepassActivity extends AppCompatActivity {
    LinearLayout pattern, total;
    LottieAnimationView animation;
    EditText currentpass, newpass, confirmpass;

    String oldpass;
    TextView atoz, AtoZ, num, charcount;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        animation = (LottieAnimationView) findViewById(R.id.animationView);
        pattern = (LinearLayout) findViewById(R.id.pattern);
        total = (LinearLayout) findViewById(R.id.total);
        atoz = (TextView) findViewById(R.id.atoz);
        AtoZ = (TextView) findViewById(R.id.AtoZ);
        num = (TextView) findViewById(R.id.num);
        charcount = (TextView) findViewById(R.id.charcount);

        currentpass = (EditText) findViewById(R.id.currentpass);
        newpass = (EditText) findViewById(R.id.newpass);
        confirmpass = (EditText) findViewById(R.id.confirmpass);
        newpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // get the password when we start typing
                String passwordx = newpass.getText().toString();
                validatepass(passwordx);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void change(View V) {
        String URL = "http://192.168.220.207:8000/api/user/";
        SharedPreferences sh = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String id = sh.getString("id", "");
        Log.i("iddd", id);

        StringRequest s = new StringRequest(Request.Method.GET, URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {
                    JSONArray x = new JSONArray(response);
                    String email = "";
                    if (x.length() > 0) {
                        for (int i = 0; i < x.length(); i++) {
                            JSONObject user = x.getJSONObject(i);
                            int id1 = user.getInt("id");
                            email = user.getString("email");
                            oldpass = user.getString("password");
                        }
                        updatee(email);


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

    public void updatee(String email) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, oldpass.toString());
Log.i("user",user.toString());
// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Password updated", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(i);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password not updated", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "auth error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }


    public Boolean validatepass(String password) {
        Log.i("hererree", password);
        // check for pattern
        Boolean x = true;
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");

        // if lowercase character is not present
        if (!lowercase.matcher(password).find()) {
            atoz.setTextColor(Color.RED);
            x = false;
        } else {
            // if lowercase character is  present
            atoz.setTextColor(Color.rgb(34, 139, 34));

        }

        // if uppercase character is not present
        if (!uppercase.matcher(password).find()) {
            AtoZ.setTextColor(Color.RED);
            x = false;
        } else {
            // if uppercase character is  present
            AtoZ.setTextColor(Color.rgb(34, 139, 34));
        }
        // if digit is not present
        if (!digit.matcher(password).find()) {
            num.setTextColor(Color.RED);
            x = false;
        } else {
            // if digit is present
            num.setTextColor(Color.rgb(34, 139, 34));
        }
        // if password length is less than 8
        if (password.length() < 8) {
            charcount.setTextColor(Color.RED);
            x = false;
        } else {
            charcount.setTextColor(Color.rgb(34, 139, 34));
        }
        return x;
    }
}
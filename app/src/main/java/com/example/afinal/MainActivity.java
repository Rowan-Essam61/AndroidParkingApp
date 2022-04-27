package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

public class MainActivity extends AppCompatActivity {
    ImageView logo;
    private int mCounter = 10;
    private HTextView hTextView;

    //prediction
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = (ImageView) findViewById(R.id.profile_image);

        //image animation
        ObjectAnimator moveAnim = ObjectAnimator.ofFloat(logo, "Y", 600);
        moveAnim.setDuration(2000);
        moveAnim.setInterpolator(new BounceInterpolator());
        moveAnim.start();

        //text animation
        hTextView = (HTextView) findViewById(R.id.text2);
        hTextView.setTextColor(Color.WHITE);
        hTextView.setAnimateType(HTextViewType.TYPER);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String id = sh.getString("id", "");
                Log.i("iddd", id);
                if (id != "") {
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), SigninActivity.class);
                    startActivity(i);
                }
            }
        }, 5000);
    }
}
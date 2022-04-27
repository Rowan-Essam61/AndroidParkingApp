package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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

public class ParkinginfoActivity extends AppCompatActivity {
    String URL = "http://192.168.220.207:8000/api/parkingspace/";
    TextView name;
    TextView location;
    TextView capacity;
    TextView category;
    TextView description;
    TextView fees;
    String capacity1;
ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkinginfo);
        name = (TextView) findViewById(R.id.name);
        location = (TextView) findViewById(R.id.location);
        capacity = (TextView) findViewById(R.id.capacity);
        category = (TextView) findViewById(R.id.category);
        description = (TextView) findViewById(R.id.description);
        fees = (TextView) findViewById(R.id.fees);
     img = (ImageView) findViewById(R.id.img);


        int id = (int) getIntent().getSerializableExtra("parkingspace");
        parseApiData(id);
    }

    public void parseApiData(int id) {
        StringRequest s = new StringRequest(Request.Method.GET, URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {
                    JSONArray o = new JSONArray(response);
                    Log.i("location", String.valueOf(o));


                    if (o.length() > 0) {
                        for (int i = 0; i < o.length(); i++) {
                            JSONObject parking = o.getJSONObject(i);
                            int id = parking.getInt("id");
                            String location1 = parking.getString("location");
                            location.setText(location1);
                            String fees1 = parking.getString("fees");
                            fees.setText(fees1);
                            String description1 = parking.getString("description");
                            description.setText(description1);
                            String name1 = parking.getString("name");
                            name.setText(name1);
                            String category1 = parking.getString("category");
                            category.setText(category1);
                            String levels1 = parking.getString("levels");
                             capacity1 = parking.getString("capacity");
                            capacity.setText(capacity1);
                            String imgg = parking.getString("img");

                            Resources res = getResources();
                            int resID = res.getIdentifier(imgg , "drawable", getPackageName());
                            img.setImageResource(resID);


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

    public void gopredict(View V) {
        Intent intent = new Intent(getApplicationContext(), PredictionActivity.class);
        int id = (int) getIntent().getSerializableExtra("parkingspace");
        intent.putExtra("parkingspaceidd",id);
        int capacity=Integer.parseInt(capacity1);
        intent.putExtra("capacity",capacity);
        startActivity(intent);

    }
}




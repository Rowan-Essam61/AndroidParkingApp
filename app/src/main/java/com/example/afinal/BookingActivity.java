package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity {
    final ArrayList<String> n = new ArrayList<>();
    String URL = "http://192.168.220.207:8000/api/parkingslot/parking/";
    int id, checkin, checkout, checkinm, checkoutm;
    String dateday, day;
    LottieAnimationView animation;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        animation = (LottieAnimationView) findViewById(R.id.animationView);
        id = (int) getIntent().getSerializableExtra("parkingspaceid");
        Log.i("parkingspaceeee", String.valueOf(id));
        checkin = (int) getIntent().getSerializableExtra("checkinhour");
        checkout = (int) getIntent().getSerializableExtra("checkouthour");
        checkinm = (int) getIntent().getSerializableExtra("checkinmin");
        checkoutm = (int) getIntent().getSerializableExtra("checkoutmin");

        dateday = (String) getIntent().getSerializableExtra("daydate");
        day = (String) getIntent().getSerializableExtra("day");

        parseApiDataparking();
        Log.i("id", String.valueOf(id));
        Log.i("checkin", String.valueOf(checkin));
        Log.i("checkout", String.valueOf(checkout));
        Log.i("checkinmmm", String.valueOf(checkinm));
        Log.i("checkoutmm", String.valueOf(checkoutm));
        Log.i("dayyyyyyyyyyyyy", String.valueOf(dateday));
        Log.i("day", String.valueOf(day));

        parseApiDataa();
        parseApiDataslotlevel();
    }


    public void parseApiDataa() {
        String iduser;

        String URL = "http://192.168.220.207:8000/api/registration/user/";
        SharedPreferences sh = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        iduser = sh.getString("id", "");

        StringRequest s = new StringRequest(Request.Method.GET, URL + iduser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String date, slotname, checkin, checkout, status, parkingid, day;

                Log.i("result", response);
                try {
                    JSONObject o = new JSONObject(response);
                    JSONArray x = o.getJSONArray("registration");
                    if (x.length() > 0) {
                        for (int i = 0; i < x.length(); i++) {

                            JSONObject registrations = x.getJSONObject(i);
                            parkingid = registrations.getString("parking_id");
                            slotname = registrations.getString("slot_name");
                            checkin = registrations.getString("checkin_time");
                            status = registrations.getString("status");
                            date = registrations.getString("date");
                            checkout = registrations.getString("leave_time");
                            day = registrations.getString("day");
                            Log.i("status", status);
                            if (status.equals("pending")) {
/////////////////////////////////////////////////////////////////
                                Log.i("11111", "pendiing");
                                flag += 1;
                            }
                        }

                        if (flag > 0) {
                            GridView list = (GridView) findViewById(R.id.list);
                            list.setVisibility(View.GONE);
                            animation.setVisibility(View.VISIBLE);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(i);

                                }
                            }, 5000);


                        } else {

                            parseApiData();
                        }

                    } else {
                        parseApiData();

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

    public void parseApiDataslotlevel() {
        final ArrayList<String> category = new ArrayList<>();
        String URL = "http://192.168.220.207:8000/api/parkingslot/level/";
        StringRequest s = new StringRequest(Request.Method.GET, URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {
                    JSONArray o = new JSONArray(response);
                    if (o.length() > 0) {
                        for (int i = 0; i < o.length(); i++) {
                            JSONObject level = o.getJSONObject(i);
                            Log.i("categorry", String.valueOf(level));
                            category.add(level.getString("level"));
                        }
                        category.add("level3");
                        category.add("level4");


                        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.level, category);

                        GridView listView = (GridView) findViewById(R.id.listt);
                        listView.setAdapter(adapter);
//                        ViewGroup.LayoutParams lp = listView.getLayoutParams();
//                        lp.height = (category.size() * 120);
//                        listView.setLayoutParams(lp);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Log.i("position", String.valueOf(category.get(position)));

                                for (int i=0;i<category.size();i++) {
                                    parent.getChildAt(i).setBackgroundColor(Color.rgb(152,193,217));
                                }


                                view.setBackgroundColor(Color.RED);
                                parseApileveldata(category.get(position));



                            }
                        });


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


    public void parseApileveldata(String level) {

        final ArrayList<String> levels = new ArrayList<>();
        String URL = "http://192.168.220.207:8000/api/parkingslot/parking/level/";
        StringRequest s = new StringRequest(Request.Method.GET, URL + id + "/" + level, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {
                    JSONArray x = new JSONArray(response);
                    if (x.length() > 0) {
                        for (int i = 0; i < x.length(); i++) {
                            JSONObject slot = x.getJSONObject(i);
                            Log.i("parkingslot/parking/level/", String.valueOf(slot));
                            levels.add(slot.getString("name"));
Log.i("sssssssssssssssssssssss",slot.getString("name"));

                        }




                    }
                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.slot,levels);

                    GridView listView = (GridView) findViewById(R.id.list);
                    listView.setAdapter(adapter);
                    ViewGroup.LayoutParams lp = listView.getLayoutParams();
                    lp.height = 80 * (n.size());
                    listView.setLayoutParams(lp);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.i("position", String.valueOf(n.get(position)));
                            Log.i("paringgid", String.valueOf(id));
                            Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
                            intent.putExtra("slot", n.get(position));
                            intent.putExtra("parkingspaceid", (int) getIntent().getSerializableExtra("parkingspaceid"));
                            intent.putExtra("checkinhour", checkin);
                            intent.putExtra("checkouthour", checkout);
                            intent.putExtra("checkinmin", checkinm);
                            intent.putExtra("checkoutmin", checkoutm);
                            intent.putExtra("daydate", dateday);
                            intent.putExtra("day", day);

                            startActivity(intent);
                        }
                    });

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


    public void parseApiData() {
        StringRequest s = new StringRequest(Request.Method.GET, URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {
                    JSONArray x = new JSONArray(response);
                    if (x.length() > 0) {
                        for (int i = 0; i < x.length(); i++) {
                            JSONObject slot = x.getJSONObject(i);
                            Log.i("Slottttttttttt", String.valueOf(slot));


                            if (slot.getString("status").equals("available")) {
                                n.add(slot.getString("name"));
                            }

                        }


                        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.slot, n);

                        GridView listView = (GridView) findViewById(R.id.list);
                        listView.setAdapter(adapter);
                        ViewGroup.LayoutParams lp = listView.getLayoutParams();
                        lp.height = 80 * (n.size());
                        listView.setLayoutParams(lp);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Log.i("position", String.valueOf(n.get(position)));
                                Log.i("paringgid", String.valueOf(id));
                                Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
                                intent.putExtra("slot", n.get(position));
                                intent.putExtra("parkingspaceid", (int) getIntent().getSerializableExtra("parkingspaceid"));
                                intent.putExtra("checkinhour", checkin);
                                intent.putExtra("checkouthour", checkout);
                                intent.putExtra("checkinmin", checkinm);
                                intent.putExtra("checkoutmin", checkoutm);
                                intent.putExtra("daydate", dateday);
                                intent.putExtra("day", day);

                                startActivity(intent);
                            }
                        });


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


    public void parseApiDataparking() {
        String URL = "http://192.168.220.207:8000/api/parkingspace/";
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

                            String imgg = parking.getString("img");
                            ImageView img = (ImageView) findViewById(R.id.img);
                            Resources res = getResources();
                            int resID = res.getIdentifier(imgg, "drawable", getPackageName());
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


}
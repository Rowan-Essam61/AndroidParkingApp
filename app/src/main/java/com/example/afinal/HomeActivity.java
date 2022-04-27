package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    final ArrayList<parkingspace> n = new ArrayList<>();
    String URL = "http://192.168.220.207:8000/api/parkingspace";
    String URL1 = "http://192.168.220.207:8000/api/parkingspace/category/";
    String URL2 = "http://192.168.220.207:8000/api/registration/";
    GridView listView;
    TextView textView;
    ImageView sidebar;
    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        parseApiData();
        listView = (GridView) findViewById(R.id.list);
        textView = findViewById(R.id.countdown);
        sidebar = (ImageView) findViewById(R.id.sidebar);
        search = (SearchView) findViewById(R.id.search);

        sidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);


            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int flag = 0;

                for (int i = 0; i < n.size(); i++) {
                    Log.i("n", n.get(i).name);

                    if (n.get(i).name.toLowerCase().contains(query)) {
                        Log.i("turee", "trrrrrrrrrrrrrrrrrrrrrr" + query);
                        Intent intent = new Intent(getApplicationContext(), ParkinginfoActivity.class);
                        intent.putExtra("parkingspace", n.get(i).id);
                        startActivity(intent);
                        flag++;

                    }

                }
                if (flag == 0) {

                    Toast.makeText(getApplicationContext(), "sorry no parking space with that name ", Toast.LENGTH_SHORT).show();


                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);

//                getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, new RegistrationFragment ()).commit();

            }
        });

        check();

    }

    public void check() {

        SharedPreferences sh = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String id = sh.getString("regid", "");

        Log.i("reggggggg", id);
        StringRequest s = new StringRequest(Request.Method.GET, URL2 + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("resultregsss", response);
                try {
                    JSONArray o = new JSONArray(response);
                    if (o.length() > 0) {
                        for (int i = 0; i < o.length(); i++) {
                            JSONObject reg = o.getJSONObject(i);
                            Log.i("iiiiiiiiiiiiii", String.valueOf(reg));
                            String status = reg.getString("status");
                            String checkin = reg.getString("checkin_time");
                            String idd = reg.getString("user_id");
                            String slot_name = reg.getString("slot_name");
                            String leave_time = reg.getString("leave_time");
                            String parking_id = reg.getString("parking_id");
                            String day = reg.getString("day");
                            String date = reg.getString("date");
                            String carnum = reg.getString("car_id");


                            if (status.equals("pending")) {
                                Calendar calendar = Calendar.getInstance();
                                Date td0 = calendar.getTime();


                                Date datee = Calendar.getInstance().getTime();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String strDate = dateFormat.format(datee);


                                SimpleDateFormat formatt = new SimpleDateFormat("yyyy-MM-dd");


                                Date datechecking = formatt.parse(date);
                                Date today = formatt.parse(strDate);
                                Boolean x = datechecking.before(today);
                                Log.i("datttttttttttttttttttttttttttttttt1", String.valueOf(strDate));
                                Log.i("datttttttttttttttttttttttttttttttt2", date);

                                if (x) {

                                    Log.i("xxx", String.valueOf(x));

                                    textView.setVisibility(View.GONE);
                                    SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                                    String id = sh.getString("regid", "");
                                    postregData(id, status);
                                    // url to post our data;
                                    postslotData("available", slot_name, parking_id);

                                } else {
                                    int now = td0.getHours();
                                    int min = td0.getMinutes();
                                    String checkinarr[] = checkin.split(":");
                                    String checkhour = checkinarr[0];
                                    String checkmin = checkinarr[1];

                                    int chour = Integer.parseInt(checkhour);
                                    int cmin = Integer.parseInt(checkmin);
                                    Log.i("ccchour", String.valueOf(chour));

                                    int couth = chour - now;
                                    int couth2 = couth * 60;
                                    int countmin = cmin - min;
                                    int counter = couth2 + countmin;
                                    Log.i("ssssssss", "s" + counter);

                                    if (counter == 0) {
                                        postregData(id, status);
                                        // url to post our data;
                                        postslotData("available", slot_name, parking_id);
                                    } else {
                                        counter(checkin);
                                    }
                                }
                            }
                        }
                    }
                } catch (JSONException | ParseException e) {
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

    public void counter(String checkin) {

        Log.i("checkinn timee", checkin);

        //counteer

        Calendar calendar = Calendar.getInstance();
        Date td0 = calendar.getTime();
        int now = td0.getHours();
        int min = td0.getMinutes();
        String checkinarr[] = checkin.split(":");
        String checkhour = checkinarr[0];
        String checkmin = checkinarr[1];

        int chour = Integer.parseInt(checkhour);
        int cmin = Integer.parseInt(checkmin);
        Log.i("ccchour", String.valueOf(chour));


        textView.setVisibility(View.VISIBLE);
        int couth = chour - now;
        int couth2 = couth * 60;
        int countmin = cmin - min;
        int counter = couth2 + countmin;
        int sec = counter * 60;
        sec = sec * 1000;

        new CountDownTimer(sec, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                textView.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                Log.i("couterhome", String.valueOf(textView.getText()));
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                textView.setText("00:00:00");
                textView.setVisibility(View.GONE);
                SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                String id = sh.getString("regid", "");

                check();


            }
        }.start();


    }


    private void postregData(String regid, String status) {
        // url to post our data
        String url = "http://192.168.220.207:8000/api/registration/updatestatus/";

        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);

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

                params.put("status", "cancelled");
                Log.i("param", String.valueOf(params));

                return params;
            }
        };

        queue.add(request);
    }

    private void postslotData(String status, String slott, String parking_id) {
        // url to post our data
        String url = "http://192.168.220.207:8000/api/parkingslot/updatestatus/";

        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
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

                params.put("parking_id", parking_id);
                Log.i("param", String.valueOf(params));

                return params;
            }
        };

        queue.add(request);
    }


    public void parseApiData() {
        StringRequest s = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {
                    JSONObject o = new JSONObject(response);
                    JSONArray x = o.getJSONArray("parkingspace");
                    if (x.length() > 0) {
                        for (int i = 0; i < x.length(); i++) {
                            JSONObject parking = x.getJSONObject(i);
                            int id = parking.getInt("id");
                            String location = parking.getString("location");
                            String fees = parking.getString("fees");
                            String description = parking.getString("description");
                            String name = parking.getString("name");
                            String category = parking.getString("category");
                            String levels = parking.getString("levels");
                            String capacity = parking.getString("capacity");
                            String img = parking.getString("img");

                            n.add(new parkingspace(name, capacity, location, fees, id, img));

                        }


                        ViewGroup.LayoutParams lp = listView.getLayoutParams();
                        lp.height = 400 * x.length();
                        listView.setLayoutParams(lp);

                        ParkingAdapter<parkingspace> adapter = new ParkingAdapter(getApplicationContext(), n);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                int y = (int) id;

                                parkingspace ww = n.get(position);
                                Log.i("selected", String.valueOf(ww.id));
                                Intent intent = new Intent(getApplicationContext(), ParkinginfoActivity.class);
                                intent.putExtra("parkingspace", ww.id);
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

    public void parseApicategory(View V) {
        String x = String.valueOf(V.getTag());
        n.clear();

        StringRequest s = new StringRequest(Request.Method.GET, URL1 + x, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response);
                try {
                    JSONArray o = new JSONArray(response);
                    if (o.length() > 0) {
                        for (int i = 0; i < o.length(); i++) {
                            JSONObject parking = o.getJSONObject(i);
                            int id = parking.getInt("id");
                            String location = parking.getString("location");
                            String fees = parking.getString("fees");
                            String description = parking.getString("description");
                            String name = parking.getString("name");
                            String category = parking.getString("category");
                            String levels = parking.getString("levels");
                            String capacity = parking.getString("capacity");
                            String img = parking.getString("img");
                            n.add(new parkingspace(name, capacity, location, fees, id, img));

                        }

                        ViewGroup.LayoutParams lp = listView.getLayoutParams();
                        lp.height = 400 * o.length();
                        listView.setLayoutParams(lp);
                        ParkingAdapter<parkingspace> adapter = new ParkingAdapter(getApplicationContext(), n);


                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                int y = (int) id;

                                parkingspace ww = n.get(position);
                                Log.i("selected", String.valueOf(ww.id));
                                Intent intent = new Intent(getApplicationContext(), ParkinginfoActivity.class);
                                intent.putExtra("parkingspace", ww.id);
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
}
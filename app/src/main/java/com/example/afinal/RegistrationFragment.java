package com.example.afinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageView hide;
    ImageView show;
    String checkindatee, checkoutdatee;
    final ArrayList<registeration> n = new ArrayList<>();
    //
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        parseApiData();

    }


    public void parseApiData() {
        String iduser;
        String URL = "http://192.168.220.207:8000/api/registration/user/";
        SharedPreferences sh = this.getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);


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


                            String type = "AM";
                            Log.i("parkriirstatussss", status);

                            String checkinarr[] = checkin.split(":");
                            String hour = checkinarr[0];
                            String min = checkinarr[1];
                            int checkintime, checkouttime;
                            checkintime = Integer.valueOf(hour);
                            if (Integer.parseInt(hour) > 12) {

                                checkintime = Integer.parseInt(hour) - 12;
                                type = "PM";
                            }
                            checkindatee = String.valueOf(checkintime) + ":" + min + " " + type;
                            Log.i("checkinn", checkindatee);

                            String checkoutarr[] = checkout.split(":");
                            String hourout = checkoutarr[0];
                            String minout = checkoutarr[1];
                            checkouttime = Integer.valueOf(hourout);
                            if (Integer.parseInt(hourout) > 12) {

                                checkouttime = Integer.parseInt(hourout) - 12;
                                type = "PM";
                            }
                            Log.i("Statussss", status);
                            Log.i("parkingid", parkingid);
                            Log.i("slot", slotname);
                            checkoutdatee = String.valueOf(checkouttime) + ":" + minout + " " + type;

                            String URL1 = "http://192.168.220.207:8000/api/parkingspace/";
                            ////////////////////////////////////////////////////
                            String finalSlotname = slotname;
                            String finalStatus = status;
                            String finalDate = date;
                            String finalDay = day;
                            StringRequest s = new StringRequest(Request.Method.GET, URL1 + parkingid, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String parkingname;

                                    Log.i("result", response);
                                    try {
                                        JSONArray x = new JSONArray(response);
                                        if (x.length() > 0) {
                                            for (int i = 0; i < x.length(); i++) {

                                                JSONObject registrations = x.getJSONObject(i);
                                                parkingname = registrations.getString("name");
                                                Log.i("parkingspacename", parkingname);

                                                registeration reg = new registeration(parkingname, finalSlotname, finalStatus,
                                                        checkindatee, checkoutdatee, finalDate, finalDay, iduser);

                                                Log.i("regobject", reg.getName());

                                                Log.i("regobject2", reg.getStatus());

                                                n.add(reg);

                                            }
                                            regadapter<registeration> adapter = new regadapter(getContext(), n);
                                            ListView listView = (ListView) getView().findViewById(R.id.list);
                                            listView.setAdapter(adapter);

                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                                    TableLayout textView = (TableLayout) view.findViewById(R.id.tabledata);
                                                    hide = (ImageView) view.findViewById(R.id.imageView1);
                                                    show = (ImageView) view.findViewById(R.id.imageView2);
                                                    TextView status = (TextView) view.findViewById(R.id.status);
                                                    ImageView overlay = (ImageView) view.findViewById(R.id.overlay);
                                                    TextView statusss = (TextView) view.findViewById(R.id.status1);
                                                    if (textView.getVisibility() == View.GONE) {
                                                        hide.setVisibility(View.VISIBLE);
                                                        show.setVisibility(View.GONE);


                                                        textView.setVisibility(View.VISIBLE);
                                                    } else {
                                                        hide.setVisibility(View.GONE);
                                                        show.setVisibility(View.VISIBLE);
                                                        textView.setVisibility(View.GONE);
                                                    }

                                                    if (status.getText().equals("pending")) {
                                                        statusss.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                if (overlay.getVisibility() == View.VISIBLE) {

                                                                    overlay.setVisibility(View.GONE);

                                                                } else {
                                                                    textView.setVisibility(View.GONE);
                                                                    overlay.setVisibility(View.VISIBLE);
                                                                }


                                                            }
                                                        });

                                                    }


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
                            RequestQueue r = Volley.newRequestQueue(getContext());
                            r.add(s);

                        }

                    }else {

//
//                        LottieAnimationView animation;
//                            animation = (LottieAnimationView) getView().findViewById(R.id.animationView);
//                            animation.setVisibility(View.VISIBLE);




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
        RequestQueue r = Volley.newRequestQueue(getContext());
        r.add(s);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }
}
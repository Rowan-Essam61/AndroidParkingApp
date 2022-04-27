package com.example.afinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String URL1 = "http://192.168.220.207:8000/api/usercar/";
TextView update;
    String type1, category1, carnum1, color1;
    EditText carnum;
    EditText category;
    EditText type;

    LinearLayout total;
    LottieAnimationView animation;
    EditText color;
    TabLayout tabLayout;
    ViewPager viewPager;

    public CarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarFragment newInstance(String param1, String param2) {
        CarFragment fragment = new CarFragment();
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

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_car, container, false);
        animation=(LottieAnimationView) rootView.findViewById(R.id.animationView);

        total=(LinearLayout) rootView.findViewById(R.id.total);

        carnum = (EditText) rootView.findViewById(R.id.carnum);
        category = (EditText) rootView.findViewById(R.id.category);
        type = (EditText) rootView.findViewById(R.id.type);
        color = (EditText) rootView.findViewById(R.id.color);
update=(TextView) rootView.findViewById(R.id.update);
        parseApicar();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatee();
            }
        });



        return rootView;
    }

    public void parseApicar() {
        SharedPreferences sh = this.getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
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

                            carnum1 = car.getString("id");
                            carnum.setText(carnum1);
                            category1 = car.getString("category");
                            category.setText(category1);
                            type1 = car.getString("category");
                            type.setText(type1);
                            color1 = car.getString("color");
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
        RequestQueue r = Volley.newRequestQueue(getContext());
        r.add(s);

    }

    public void updatee() {

        String url = "http://192.168.220.207:8000/api/car/update/";


        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, url + carnum1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respObj = new JSONObject(response);
                    Log.i("response", response);

                    total.setVisibility(View.GONE);
                    animation.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i = new Intent(getContext(), HomeActivity.class);
                            startActivity(i);

                        }
                    }, 2000);

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
                params.put("category", category.getText().toString());
                params.put("type", type.getText().toString());
                params.put("color", color.getText().toString());
                Log.i("param", String.valueOf(params));

                return params;
            }
        };

        queue.add(request);


    }

}
package com.example.afinal;

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
 * Use the {@link PersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout total;
    LottieAnimationView animation;
    String URL = "http://192.168.220.207:8000/api/user/";
    String URL1 = "http://192.168.220.207:8000/api/usercar/";
    EditText name;
    EditText email;
    EditText phone;
    EditText gender;
    EditText nid;
    EditText address;
    EditText dob;
TextView update,change,logout;
    EditText carnum;
    EditText category;
    EditText type;
    EditText color;
    TabLayout tabLayout;
    ViewPager viewPager;

    private View rootView;
    public PersonalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalFragment newInstance(String param1, String param2) {
        PersonalFragment fragment = new PersonalFragment();
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
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_personal, container, false);
update=(TextView) rootView.findViewById(R.id.update);
        change=(TextView) rootView.findViewById(R.id.change);
        animation=(LottieAnimationView) rootView.findViewById(R.id.animationView);
        logout=(TextView) rootView.findViewById(R.id.logout);

        total=(LinearLayout) rootView.findViewById(R.id.total);
        name = (EditText) rootView.findViewById(R.id.name);
        email = (EditText) rootView.findViewById(R.id.email);
        phone = (EditText) rootView.findViewById(R.id.phone);
        address = (EditText) rootView.findViewById(R.id.address);
        nid = (EditText) rootView.findViewById(R.id.nid);
        dob = (EditText) rootView.findViewById(R.id.dob);
        gender = (EditText) rootView.findViewById(R.id.gender);
        parseApiData();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       updatee();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangepassActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                preferences.edit().remove("id").commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });
        return rootView;


    }


public void updatee(){

    String url = "http://192.168.220.207:8000/api/user/update/";
    SharedPreferences sh = this.getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
    String id = sh.getString("id", "");
    Log.i("iddd", id);

    RequestQueue queue = Volley.newRequestQueue(getContext());

    StringRequest request = new StringRequest(Request.Method.POST, url+id, new com.android.volley.Response.Listener<String>() {
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


            params.put("username",name.getText().toString() );
            params.put("address", address.getText().toString());
            params.put("email", email.getText().toString());
            params.put("gender", gender.getText().toString());
            params.put("phone", phone.getText().toString());
            params.put("role", "carowner");
            params.put("dob", dob.getText().toString());


                     Log.i("param", String.valueOf(params));

            return params;
        }
    };

    queue.add(request);


}



    public void parseApiData() {
        SharedPreferences sh = this.getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
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
        RequestQueue r = Volley.newRequestQueue(getContext());
        r.add(s);

    }



}
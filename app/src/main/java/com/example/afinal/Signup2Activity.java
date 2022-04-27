package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.ByteArrayOutputStream;

public class Signup2Activity extends AppCompatActivity {
    private EditText carnumber;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String ImageString = "";
    LinearLayout ocr;
    Button photoButton;
    ProgressBar simpleProgressBar;

    String[] colors = new String[]{"Select color", "RED", "BLACK", "SILVER", "BLUE", "GREEN", "MAROON"};
    String[] items = new String[]{"select category", "BMW", "FIAT", "Kia", "JEEP", "OPEL", "TOYOTA", "MINI", "RENAULT"};
    String[][] cars = {{"select type"},
            {"select type", "X6", "X1", "X3", "I8", "Z4", "IX3", "1 SERIES", "2SERIES", "3SERIES"},
            {"select type", "TIPO", "BUNTO", "500", "PANDA", "SPIDER"},
            {"select type", "EV6", "CARENZ", "APORTAGE", "SOUL", "RIO"},
            {"select type", "CHEROKEE", "COMPASS", "RENEGADE"},
            {"select type", "YARIS", "COROLA", "RUSH"},
            {"select type", "ASTRA", "CORSA", "CROSSLAND"},
            {"select type", "COOPER", "CLUBMAN", "COUNTRYMAN"},
            {"select type", "MEGAN", "LOGAN", "CLIO", "CAPTUR"}};
    user x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        //car category
        Spinner dropdown = findViewById(R.id.spinner1);

        simpleProgressBar = (ProgressBar) findViewById(R.id.prg);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Log.i("ss", selectedItem);
                selected(position, selectedItem);

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        } else {
            Log.i("nelsaaa", "nelaa");

        }
        carnumber = (EditText) findViewById(R.id.carnum);
        ocr = (LinearLayout) findViewById(R.id.ocr);
        this.imageView = (ImageView) this.findViewById(R.id.imageView1);
        photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });


    }

    //car type
    public void selected(int positionc, String category) {

        Spinner dropdown2 = findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cars[positionc]);

        dropdown2.setAdapter(adapter2);
        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Log.i("ss", selectedItem);

                color(selectedItem, category, positionc, position);

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void color(String type, String category, int pc, int pt) {
        //color
        Spinner dropdown3 = findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colors);
        dropdown3.setAdapter(adapter3);
        dropdown3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Log.i("ss", selectedItem);
                if (position != 0 && pc != 0 && pt != 0) {
                    x = finish(type, category, selectedItem);
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public user finish(String type, String category, String color) {
        Log.i("type", type);
        Log.i("category", category);
        Log.i("color", color);
        carnumber = (EditText) findViewById(R.id.carnum);
        Log.i("carnumber", carnumber.getText().toString());
        user xc = new user(carnumber.getText().toString(), category, type, color);
        return xc;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photoButton.setVisibility(View.GONE);
            ocr.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(photo);
            ImageString = getStringImage(photo);
            simpleProgressBar.setVisibility(View.VISIBLE);

            AsyncTask<Void,Void,Boolean> task = new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    simpleProgressBar.setVisibility(View.VISIBLE);
                }

                @Override
                protected Boolean doInBackground(Void... params) {
                    // 2. Obtain the python instance
                    Python py = Python.getInstance();
                    PyObject sys = py.getModule("scan");

                    PyObject res = sys.callAttr("main", ImageString);
                    String carplate = String.valueOf(res);

                    //String ssss=sss.replace(",", " ");
                    //String sssss=ssss.replace("'", " ");

                    String SS = carplate.replace("[", " ");
                    String sss = SS.replace("]", " ");
                    String str = sss.replaceAll("[,']", " ");
                    Log.i("outputtttttttttttttttt", String.valueOf(str));
                    carnumber.setText(str);



                  return true;
                }

                @Override
                protected void onPostExecute(Boolean aVoid) {
                    super.onPostExecute(aVoid);
                    simpleProgressBar.setVisibility(View.GONE);
                }
            };
            task.execute();
        }



        }


    public String getStringImage(Bitmap photo) {
        Log.i("imag string", "awl elfun ");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, output);
        Log.i("imag string", "nos elfun ");
        byte[] imageByte = output.toByteArray();
        String encodeImage = android.util.Base64.encodeToString(imageByte, Base64.DEFAULT);
        return encodeImage;
    }


    public void next(View V) {
        user z = (user) getIntent().getSerializableExtra("user");
        Intent intent = new Intent(getApplicationContext(), Signup3Activity.class);
        intent.putExtra("car", x);
        intent.putExtra("user", z);
        startActivity(intent);

    }


}
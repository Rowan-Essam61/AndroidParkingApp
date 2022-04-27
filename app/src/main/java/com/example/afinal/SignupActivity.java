package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText address;
    private EditText nid;
    private EditText cpassword;
    private EditText password;
    LinearLayout pattern;
    private RadioGroup radioSexGroup;
    int selectedId;
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
        setContentView(R.layout.activity_signup);

        pattern = (LinearLayout) findViewById(R.id.pattern);
        atoz = findViewById(R.id.atoz);
        AtoZ = findViewById(R.id.AtoZ);
        num = findViewById(R.id.num);
        charcount = findViewById(R.id.charcount);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        nid = (EditText) findViewById(R.id.nid);
        cpassword = (EditText) findViewById(R.id.cpassword);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        selectedId = radioSexGroup.getCheckedRadioButtonId();
        password = (EditText) findViewById(R.id.password);



        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pattern.setVisibility(View.VISIBLE);
                Log.i("in beforeTextChanged","beforeTextChanged");
                }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // get the password when we start typing
                pattern.setVisibility(View.VISIBLE);
                Log.i("outt beforeTextChanged","beforeTextChanged");
                String passwordx = password.getText().toString();
                validatepass(passwordx);
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


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


        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.i("yy2", String.valueOf(dateView.getText()));
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        Log.i("yy", String.valueOf(dateView.length()));
        dateView.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
        Log.i("day", String.valueOf(dateView.length()));
        dateView.setVisibility(View.VISIBLE);
    }

    public void check(View V) {

        Log.i("datteeeeeeeeee", String.valueOf(dateView.getText()));
        String gender;
        RadioButton male = (RadioButton) findViewById(R.id.radioMale);
        RadioButton female = (RadioButton) findViewById(R.id.radioFemale);
        if (male.isChecked()) {
            gender = "male";

        } else {
            gender = "female";
        }
        Log.i("before", String.valueOf(selectedId));
        Log.i("before2", String.valueOf(cpassword.getText()));
        if (username.length() != 0 && emailValidator(email) && phone.length() == 11 && nid.length() == 11
                && password.length() > 8 && dateView.getText() != "" && address.length() != 0 &&
                password.getText().toString().equals(cpassword.getText().toString())
        ) {
            Log.i("after", String.valueOf(password.getText()));

            user x = new user(username.getText().toString(), email.getText().toString(), phone.getText().toString(),
                    nid.getText().toString(), address.getText().toString(),
                    password.getText().toString(), gender, dateView.getText().toString());

            Log.i("user", x.username);
            Intent intent = new Intent(getApplicationContext(), Signup2Activity.class);
            intent.putExtra("user", x);
            startActivity(intent);


        } else {
            if (username.length() == 0) {
                username.setError("This field is required");

            }
            if (!emailValidator(email)) {
                email.setError("enter valid email");

            }
            if (phone.length() == 0) {
                phone.setError("This field is required");

            } else if (phone.length() < 11 || phone.length() > 11) {
                phone.setError("Phone must be 11 number");
            }
            if (nid.length() == 0) {
                nid.setError("This field is required");

            } else if (nid.length() < 11 || nid.length() > 11) {
                nid.setError("National ID must be 11 number");
            }
            if (dateView.length() == 0) {
                dateView.setVisibility(View.VISIBLE);
                dateView.setError("This field is required");

            }

            if (address.length() == 0) {
                address.setError("This field is required");

            }
            if (password.length() == 0) {
                password.setError("This field is required");

            }
            if (!password.getText().toString().equals(cpassword.getText().toString())) {

                cpassword.setError("password isnot equal confirm password");

            }

        }
    }

    public boolean emailValidator(EditText etMail) {

        // extract the entered data from the EditText
        String emailToText = etMail.getText().toString();

        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public void validatepass(String password) {

        // check for pattern
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");

        // if lowercase character is not present
        if (!lowercase.matcher(password).find()) {
            atoz.setTextColor(Color.RED);
        } else {
            // if lowercase character is  present
            atoz.setTextColor(Color.rgb(34,139,34));
        }

        // if uppercase character is not present
        if (!uppercase.matcher(password).find()) {
            AtoZ.setTextColor(Color.RED);
        } else {
            // if uppercase character is  present
            AtoZ.setTextColor(Color.rgb(34,139,34));
        }
        // if digit is not present
        if (!digit.matcher(password).find()) {
            num.setTextColor(Color.RED);
        } else {
            // if digit is present
            num.setTextColor(Color.rgb(34,139,34));
        }
        // if password length is less than 8
        if (password.length() < 8) {
            charcount.setTextColor(Color.RED);
        } else {
            charcount.setTextColor(Color.rgb(34,139,34));
        }
    }
}





package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DirectAction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.jsoup.parser.Parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class PredictionActivity extends AppCompatActivity {
    TextView timeButton2;
    TextView timeButton;
    TextView book;
    TextView predict;
    TextView checkouttext;
    TextView today;
    TextView d1;
    TextView d2;
    TextView d3;
    TextView d4;
    TextView d5;
    TextView d6;
    String select;
    String fDate;
    Date td0;
    Date td1;
    Date td2;
    Date td3;
    Date td4;
    Date td5;
    Date td6;
    Date now;
    String[] items;
    int selectedday;
    String dayyy;
    int hour, minute, hour1, hour3;
    int hour2, minute2;
    int flag = 0;
    PieChart pieChart;
    PieData pieData;
    String checkin = "AM", checkout = "AM";
    List<PieEntry> pieEntryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        //piechart
        pieChart = findViewById(R.id.pieChart);


        book = (TextView) findViewById(R.id.book);
        checkouttext = (TextView) findViewById(R.id.checkouttext);
        predict = (TextView) findViewById(R.id.predicted);
        predict.setVisibility(View.GONE);
        pieChart.setVisibility(View.GONE);
        checkouttext.setVisibility(View.INVISIBLE);
        book.setVisibility((View.INVISIBLE));
        //date
        Calendar calendar = Calendar.getInstance();
        td0 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        td1 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        td2 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        td3 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        td4 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        td5 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        td6 = calendar.getTime();


        today = (TextView) findViewById(R.id.today);
        d1 = (TextView) findViewById(R.id.d1);
        d2 = (TextView) findViewById(R.id.d2);
        d3 = (TextView) findViewById(R.id.d3);
        d4 = (TextView) findViewById(R.id.d4);
        d5 = (TextView) findViewById(R.id.d5);
        d6 = (TextView) findViewById(R.id.d6);
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(td0);
        String fDate1 = new SimpleDateFormat("yyyy-MM-dd").format(td1);
        String fDate2 = new SimpleDateFormat("yyyy-MM-dd").format(td2);
        String fDate3 = new SimpleDateFormat("yyyy-MM-dd").format(td3);
        String fDate4 = new SimpleDateFormat("yyyy-MM-dd").format(td4);
        String fDate5 = new SimpleDateFormat("yyyy-MM-dd").format(td5);
        String fDate6 = new SimpleDateFormat("yyyy-MM-dd").format(td6);
        items = new String[]{fDate, fDate1, fDate2, fDate3, fDate4, fDate5, fDate6};


        today.setText(new SimpleDateFormat("EE", Locale.ENGLISH).format(td0.getTime()));
        d1.setText(new SimpleDateFormat("EE", Locale.ENGLISH).format(td1.getTime()));
        d2.setText(new SimpleDateFormat("EE", Locale.ENGLISH).format(td2.getTime()));
        d3.setText(new SimpleDateFormat("EE", Locale.ENGLISH).format(td3.getTime()));
        d4.setText(new SimpleDateFormat("EE", Locale.ENGLISH).format(td4.getTime()));
        d5.setText(new SimpleDateFormat("EE", Locale.ENGLISH).format(td5.getTime()));
        d6.setText(new SimpleDateFormat("EE", Locale.ENGLISH).format(td6.getTime()));
//1 select day 1 change color  2 time now bra7tooo 3 3 button hide 4444444444444444444 predictionnnnnnnnnnnnnn

        fDate = today.getText().toString();
        Calendar calendar2 = Calendar.getInstance();
        now = calendar2.getTime();
        hour = now.getHours();
        minute = now.getMinutes();
        timeButton = findViewById(R.id.timeButton);
        timeButton2 = findViewById(R.id.checkoutButton);
        timeButton2.setVisibility(View.INVISIBLE);
        select = items[0];
        dayyy = today.getText().toString();


    }

    public void getday(View V) {
        Log.i("dateeeeed1", String.valueOf(V.getTag()));
        int myColor = Color.rgb(196, 196, 196);
        int myColr = Color.YELLOW;
        today.setBackgroundTintList(ColorStateList.valueOf(myColor));
        d1.setBackgroundTintList(ColorStateList.valueOf(myColor));
        d2.setBackgroundTintList(ColorStateList.valueOf(myColor));
        d3.setBackgroundTintList(ColorStateList.valueOf(myColor));
        d4.setBackgroundTintList(ColorStateList.valueOf(myColor));
        d5.setBackgroundTintList(ColorStateList.valueOf(myColor));
        d6.setBackgroundTintList(ColorStateList.valueOf(myColor));
        V.setBackgroundTintList(ColorStateList.valueOf(myColr));
        String x = (String) V.getTag();
        selectedday = Integer.valueOf(x);
        select = items[selectedday];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(select);
            fDate = (new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime()));
            Log.i("datttttttttttttttttttttttttttttttt", fDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;

                if (hour == 0) {
                    Log.i("midddddddddddddddd", "ddddddddddddddd");
                    select = items[1];
                }
                Log.i("bbbbbbbbbbhour", String.valueOf(hour));

                Calendar calendar2 = Calendar.getInstance();
                calendar2.add(Calendar.HOUR_OF_DAY, 2);
                Date next = calendar2.getTime();
                int nextdate = next.getHours();
                if (next.getHours() == 0) {
                    nextdate = 24;

                }

                Log.i("nowwwwwww", String.valueOf(Calendar.getInstance().getTime().getHours()));
                hour1 = hour;
                if (items[selectedday] == items[0]) {
                    if (hour < next.getHours()) {
                        book.setVisibility(View.VISIBLE);
                    }

                    if (hour > Calendar.getInstance().getTime().getHours() && hour < next.getHours()) {
                        checkin = "AM";
                        if (hour > 12) {
                            hour1 = hour - 12;
                            checkin = "pm";
                        }
                        timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour1, minute) + " " + checkin);
                        Log.i("checkinintimebuttonnnn", checkin);
                        Log.i("hour", String.valueOf(hour));
                        flag += 1;
                        timeButton2.setVisibility(View.VISIBLE);
                        checkouttext.setVisibility(View.VISIBLE);
                    } else if (Calendar.getInstance().getTime().getHours() > next.getHours()) {
                        Log.i("hehee", String.valueOf(nextdate));
                        if (hour <= nextdate) {

                            checkin = "AM";
                            if (hour > 12) {
                                hour1 = hour - 12;
                                checkin = "pm";
                            }
                            timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour1, minute) + " " + checkin);
                            Log.i("hour", String.valueOf(hour));
                            flag += 1;
                            timeButton2.setVisibility(View.VISIBLE);
                            checkouttext.setVisibility(View.VISIBLE);
                            book.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getApplicationContext(), "checkin time notvalid", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "checkin time notvalid", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    book.setVisibility(View.GONE);
                    String x = "AM";
                    if (hour > 12) {
                        hour1 = hour - 12;
                        x = "pm";
                    }
                    timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour1, minute) + " " + x);
                    Log.i("hour", String.valueOf(hour));
                    flag += 1;
                    timeButton2.setVisibility(View.VISIBLE);
                    checkouttext.setVisibility(View.VISIBLE);


                }


            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.cus, onTimeSetListener, hour, minute, false);

        timePickerDialog.setTitle("Select Time");

        timePickerDialog.show();


    }


    public void popTimePicker2(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour2 = selectedHour;
                minute2 = selectedMinute;
                checkout = "AM";

                Calendar calendar2 = Calendar.getInstance();
                calendar2.add(Calendar.HOUR_OF_DAY, 2);
                Date next = calendar2.getTime();
                Log.i("hour211111111111111111", String.valueOf(hour2));
                Log.i("h1111111", String.valueOf(hour));
                hour3 = hour2;
                if (hour2 > 12) {
                    hour3 = hour2 - 12;
                    checkout = "pm";
                }
                Log.i("checkinnnnnnnnnnnnnn", checkin);
                Log.i("checkoutnnnnnnnnnnnnnnnnnnnnnnn", checkout);

                if (hour2 > hour) {
                    Log.i("hour222iffffffff2222222222", String.valueOf(hour2));


                    timeButton2.setText(String.format(Locale.getDefault(), "%02d:%02d", hour3, minute2) + " " + checkout);
                    flag += 1;
                    chart();
                    predict.setVisibility(View.VISIBLE);
                    pieChart.setVisibility(View.VISIBLE);


                } else {

                    timeButton2.setText(String.format(Locale.getDefault(), "%02d:%02d", hour3, minute2) + " " + checkout);
                    flag += 1;
                    chart();
                    predict.setVisibility(View.VISIBLE);
                    pieChart.setVisibility(View.VISIBLE);
                }

            }
        };
        //int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.cus, onTimeSetListener, hour2, minute2, false);

        timePickerDialog.setTitle("Select Time");

        timePickerDialog.show();


    }

    public void chart() {

        int id = (int) getIntent().getSerializableExtra("parkingspaceidd");
        int capacity= (int) getIntent().getSerializableExtra("capacity");
        Log.i("bbbbbbbbbbhourinoncreatee", String.valueOf(hour));
        Log.i("bbbbbbbbbbhourinoncreatee", String.valueOf(capacity));
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        } else {
            Log.i("nelsaaa", "nelaa");

        }
if (fDate==null){
    fDate=dayyy;

}


        // 2. Obtain the python instance
        Python py = Python.getInstance();
        PyObject sys = py.getModule("pred");

        PyObject res = sys.callAttr("main", id, fDate, hour);
        String predictionn = String.valueOf(res);
        Log.i("outputtttttttttttttttt", String.valueOf(predictionn));
int x=Integer.parseInt(predictionn.toString());
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieEntryList.add(new PieEntry(capacity-x, "Avaliable"));
        pieEntryList.add(new PieEntry(x, "Unavaliable"));
        int[] productColors = {Color.rgb(222, 94, 118), Color.rgb(5, 10, 48)};
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "Parking slots");
        pieDataSet.setColors(ColorTemplate.createColors(productColors));
        pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(15);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.setDrawHoleEnabled(false);

    }

    public void booking(View V) {
        Intent intent = new Intent(getApplicationContext(), BookingActivity.class);
        int id = (int) getIntent().getSerializableExtra("parkingspaceidd");
        Log.i("afhmmmmm", String.valueOf(id));
        intent.putExtra("parkingspaceid", id);
        intent.putExtra("checkinhour", hour);
        intent.putExtra("checkouthour", hour2);
        intent.putExtra("checkinmin", minute);
        intent.putExtra("checkoutmin", minute2);
        intent.putExtra("daydate", select);
        intent.putExtra("day", dayyy);
        startActivity(intent);
    }
}
package com.example.roombooker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateSelector extends AppCompatActivity {

    private long startTime;

    DatePicker dp;
    TimePicker tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selector);

        Intent thisIntent = getIntent();

        startTime = thisIntent.getLongExtra("startTime", -1);
        if (startTime == -1) throw new IllegalArgumentException("did not get a proper start time for date picker");

        dp = (DatePicker)findViewById(R.id.datePickerElement);
        tp = (TimePicker)findViewById(R.id.timePickerElement);
        tp.setIs24HourView(true);

        Date defaultDate = new Date((startTime*1000)); // note to self: epoch is in milliseconds and not seconds......................................................


        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(defaultDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        //int second = cal.get(Calendar.SECOND);

        dp.updateDate(year, month, day);
        tp.setHour(hour);
        tp.setMinute(minute);
    }

    public void backMethod(View view) {
        setResult(400, null);
        finish();
    }

    public void submitDate(View view) {
        //TODO deprecated use of date needs to be fixed
        Date resultDate = new Date(dp.getYear()-1900, dp.getMonth(), dp.getDayOfMonth(), tp.getHour(), tp.getMinute()); // jeg aner ikke hvorfor den viser 1900 år for meget????

        Toast.makeText(this, resultDate.toString(), Toast.LENGTH_SHORT).show();

        long time = resultDate.getTime()/1000L;

        Intent suitcase = new Intent();
        suitcase.putExtra("time", time);

        setResult(200, suitcase);
        finish();
    }

    public void showDatePicker(View view) {
        view.setVisibility(View.GONE);
        dp.setVisibility(View.VISIBLE);
    }
}

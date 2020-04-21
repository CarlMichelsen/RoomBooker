package com.example.roombooker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateSelector extends AppCompatActivity {

    private long startTime;

    DatePicker dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selector);

        Intent thisIntent = getIntent();

        startTime = thisIntent.getLongExtra("startTime", -1);
        if (startTime == -1) throw new IllegalArgumentException("did not get a proper start time for date picker");

        dp = (DatePicker)findViewById(R.id.datePickerElement);

        Date defaultDate = new Date(startTime);


        Toast.makeText(this, defaultDate.toString()+"\n"+startTime, Toast.LENGTH_LONG).show(); // for testing

        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(defaultDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        dp.updateDate(year, month, day); //TODO this is deprecated! find a better way!



    }

    public void backMethod(View view) {
        setResult(400, null);
        finish();
    }
}

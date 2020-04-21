package com.example.roombooker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roombooker.REST.ApiUtils;
import com.example.roombooker.REST.Reservation;
import com.example.roombooker.REST.ReservationService;
import com.example.roombooker.data.BookerDebug;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationOverview extends AppCompatActivity {
    Reservation mainReservation;

    TextView reservationTitle, reservationOwnerId, reservationId, startTime, endTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_overview);

        reservationTitle = findViewById(R.id.reservationTitleElement);
        reservationOwnerId = findViewById(R.id.reservationOwnerId);
        reservationId = findViewById(R.id.reservationId);
        startTime = findViewById(R.id.startTimeText);
        endTime = findViewById(R.id.endTimeText);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Intent thisIntent = getIntent();
            mainReservation = (Reservation) thisIntent.getSerializableExtra("reservation_value");
        } catch (Exception e) {
            Log.d(BookerDebug.getInstance().TAG, "failed to transfer object");
            finish();
        }

        if (mainReservation != null) {

            reservationTitle.setText("Reservation For Room " + mainReservation.getRoomId());
            reservationOwnerId.setText(mainReservation.getUserId());
            reservationId.setText(mainReservation.getId().toString());
            startTime.setText(dateToText(mainReservation.getFromTime()));
            endTime.setText(dateToText(mainReservation.getToTime()));
        }
    }

    public String dateToText(int time) {
        Date date = new Date(time);

        return date.toString();
    }

    public void deleteReservationMethod(View view) {
        ReservationService service = ApiUtils.getReservationService();
        Call<String> deleteCall = service.deleteReservation(mainReservation.getId());


        /*
        deleteCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d(BookerDebug.getInstance().TAG, "deleted reservation");
                } else {
                    Log.d(BookerDebug.getInstance().TAG, "failed to delete reservation");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(BookerDebug.getInstance().TAG, "failed to even send a proper delete message reservation "+t.toString());
            }
        });*/

        /*
        deleteCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Log.d(BookerDebug.getInstance().TAG, "deleted reservation");
                } else {
                    Log.d(BookerDebug.getInstance().TAG, "failed to delete reservation");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(BookerDebug.getInstance().TAG, "failed to even send a proper delete message reservation "+t.toString());
            }
        });
        */
    }

    public void backMethod(View view) {
        finish();
    }
}

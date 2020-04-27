package com.example.roombooker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roombooker.REST.ApiUtils;
import com.example.roombooker.REST.Reservation;
import com.example.roombooker.REST.ReservationService;
import com.example.roombooker.REST.Room;
import com.example.roombooker.REST.RoomService;
import com.example.roombooker.data.BookerDebug;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewReservation extends AppCompatActivity {
    // reservation information
    private int id;
    private int fromTime;
    private int toTime;
    private String userId;
    private String purpose;
    private int roomId;
    // reservation information end

    FirebaseAuth mAuth;

    private int currentTime;

    boolean fromTimeIsSet, toTimeIsSet;

    Room sourceRoom;


    Button createReservationButton;
    TextView fromTimeElement, toTimeElement, roomInformationTextView;
    EditText descriptionBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);

        fromTimeIsSet = false;
        toTimeIsSet = false;

        roomId = -1; // set to -1 so i can validate it without worrying about having roomid 0 selected
        id = -1;

        Date now = new Date();
        currentTime = (int) getCurrentTime();


        sourceRoom = (Room) getIntent().getSerializableExtra("room");
        if (sourceRoom == null)
            throw new IllegalArgumentException("Could not find source room for new reservation");

        roomId = sourceRoom.getId();

        descriptionBox = findViewById(R.id.descriptionBox);
        createReservationButton = findViewById(R.id.createReservationButton);
        fromTimeElement = findViewById(R.id.selectedFromTime);
        toTimeElement = findViewById(R.id.selectedToTime);
        roomInformationTextView = findViewById(R.id.roomInformationTextView);

        roomInformationTextView.setText("For room number " + sourceRoom.getId());


        try {
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            userId = currentUser.getUid();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to get login information\n"+e.toString(), Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void backMethod(View view) {
        finish();
    }

    public long getCurrentTime() {
        Date now = new Date();
        return now.getTime() / 1000L;
    }

    public ArrayList<String> performInputChecks() {
        ArrayList<String> errors = new ArrayList<String>();

        if ((int)fromTime <= (currentTime-60*10)) errors.add("no fromTime for the reservation");
        if ((int)toTime < currentTime) errors.add("invalid toTime for the reservation");
        if (userId == "") errors.add("no userId for the reservation");
        if ((int)roomId == -1) errors.add("no roomId for the reservation");
        if (!fromTimeIsSet) errors.add("fromTime is not set");
        if (!toTimeIsSet) errors.add("toTime is not set");

        return errors;
    }


    public void createReservationMethod(View view) {
        id = 34;
        purpose = descriptionBox.getText().toString();


        Log.e(BookerDebug.getInstance().TAG, "button");
        Reservation res = new Reservation(id, fromTime, toTime, userId, purpose, roomId);

        Log.e(BookerDebug.getInstance().TAG, "trying to do it");
        ReservationService reservationService = ApiUtils.getReservationService();

        Log.e(BookerDebug.getInstance().TAG, "created service");
        Call<Integer> responsePost = reservationService.addReservation(res);

        Log.e(BookerDebug.getInstance().TAG, "sent the thing");


        responsePost.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NewReservation.this, "Created a reservation! ["+response.body().toString()+"]", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NewReservation.this, "Failed to create a reservation :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e(BookerDebug.getInstance().TAG, "you did not do it homie!");
                Toast.makeText(NewReservation.this, "MEGA failed to create a reservation :(", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void errorCheckAndDisplay() {
        ArrayList<String> errors = performInputChecks();
        boolean informationIsValid = errors.size() == 0;

        if (!informationIsValid) {
            String str = "Errors";

            for (String s:errors) {
                str += "\n"+s;
            }

            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }

        createReservationButton.setEnabled(informationIsValid);
    }

    public void editFromTimeMethod(View view) {
        Intent fromTimeIntent = new Intent(this, DateSelector.class);
        fromTimeIntent.putExtra("startTime", getCurrentTime());

        startActivityForResult(fromTimeIntent, 11);
    }

    public void editToTimeMethod(View view) {
        Intent toTimeIntent = new Intent(this, DateSelector.class);
        toTimeIntent.putExtra("startTime", getCurrentTime());

        startActivityForResult(toTimeIntent, 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            if (data == null)
                throw new IllegalArgumentException("could not find a return intent from date picker");


            if (requestCode == 11) { // starttime response
                Date resultDate = new Date(data.getLongExtra("time", 0)*1000L);

                fromTime = (int)(resultDate.getTime()/1000L);

                fromTimeElement.setText(resultDate.toString());
                fromTimeIsSet = true;

            } else if (requestCode == 12) { // toTime response
                Date resultDate = new Date(data.getLongExtra("time", 0)*1000L);

                toTime = (int)(resultDate.getTime()/1000L);

                toTimeElement.setText(resultDate.toString());
                toTimeIsSet = true;


            }


            ArrayList<String> errors = performInputChecks();
            boolean informationIsValid = errors.size() == 0;
            createReservationButton.setEnabled(informationIsValid);


        } else if (resultCode == 400) {

            //TODO maybe add failure toast??? :]

            if (requestCode == 11) { // starttime response
                fromTimeElement.setText("i tried to change this");

            } else if (requestCode == 12) { // toTime response
                toTimeElement.setText("i tried to change this");
            }
        }



    }

}

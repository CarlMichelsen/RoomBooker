package com.example.roombooker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.roombooker.REST.Room;

import java.util.ArrayList;
import java.util.Date;

public class NewReservation extends AppCompatActivity {
    // reservation information
    private Integer id;
    private Integer fromTime;
    private Integer toTime;
    private String userId;
    private String purpose;
    private Integer roomId;
    // reservation information end

    private Integer currentTime;


    Room sourceRoom;


    Button createReservationButton;
    TextView fromTimeElement, toTimeElement, roomInformationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);

        roomId = new Integer(-1); // set to -1 so i can validate it without worrying about having roomid 0 selected
        id = new Integer(-1); // set to -1 so i can validate it without worrying about having reservationid 0 selected

        Date now = new Date();
        currentTime = new Integer((int)getCurrentTime());


        sourceRoom = (Room)getIntent().getSerializableExtra("room");
        if (sourceRoom == null) throw new IllegalArgumentException("Could not find source room for new reservation");

        roomId = new Integer(sourceRoom.getId());


        createReservationButton = findViewById(R.id.createReservationButton);
        fromTimeElement = findViewById(R.id.selectedFromTime);
        toTimeElement = findViewById(R.id.selectedToTime);
        roomInformationTextView = findViewById(R.id.roomInformationTextView);

        roomInformationTextView.setText("For room number " + sourceRoom.getId());
    }

    public void backMethod(View view) {
        finish();
    }

    public long getCurrentTime() {
        Date now = new Date();
        return now.getTime() / 1000L;
    }

    public ArrayList<String> performInputChecks() {
        Date now = new Date();
        currentTime = new Integer((int)getCurrentTime());
        ArrayList<String> errors = new ArrayList<>();

        if (id == -1) errors.add("no id for the reservation");
        if (fromTime >= currentTime) errors.add("no fromTime for the reservation");
        if (toTime >= currentTime && toTime >= fromTime) errors.add("invalid toTime for the reservation");
        if (userId.isEmpty()) errors.add("no userId for the reservation");
        if (roomId == -1) errors.add("no roomId for the reservation");

        return errors;
    }

    public boolean validateInformation() {
        return performInputChecks().size() == 0;
    }

    public void validateAndEnableButton() { // Create reservation button*
        createReservationButton.setEnabled(validateInformation());
    }

    public void createReservationMethod(View view) {
        //TODO HEHEHEHEHEH
    }

    public void testMethod(View view) {
        createReservationButton.setEnabled(validateInformation());
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
                fromTimeElement.setText("i tried to change this");

            } else if (requestCode == 12) { // toTime response
                toTimeElement.setText("i tried to change this");

            }

            validateAndEnableButton();

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

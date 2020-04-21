package com.example.roombooker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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


    Button createReservationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);

        roomId = new Integer(-1); // set to -1 so i can validate it without worrying about having roomid 0 selected
        id = new Integer(-1); // set to -1 so i can validate it without worrying about having reservationid 0 selected

        Date now = new Date();
        currentTime = new Integer((int)(now.getTime() / 1000L));

        createReservationButton = findViewById(R.id.createReservationButton);
    }

    public void backMethod(View view) {
        finish();
    }

    public ArrayList<String> performInputChecks() {
        Date now = new Date();
        currentTime = new Integer((int)(now.getTime() / 1000L));
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
    }

    public void editToTimeMethod(View view) {
    }
}

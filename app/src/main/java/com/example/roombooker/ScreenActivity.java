package com.example.roombooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.roombooker.REST.ApiUtils;
import com.example.roombooker.REST.Reservation;
import com.example.roombooker.REST.ReservationService;
import com.example.roombooker.REST.Room;
import com.example.roombooker.REST.RoomService;
import com.example.roombooker.RecyclerView.RecyclerViewReservation;
import com.example.roombooker.data.BookerDebug;
import com.google.gson.JsonSerializer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenActivity extends AppCompatActivity implements RecyclerViewReservation.ItemClickListener {
    private String TAG;

    Spinner roomSpinner;
    Room selectedRoom;

    private RecyclerView output;
    RecyclerViewReservation adapter;
    ArrayAdapter<String> spinnerAdapter;

    ArrayList<Reservation> reservations;
    ArrayList<Room> roomObjects;
    ArrayList<String> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        TAG = BookerDebug.getInstance().TAG;

        /*
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);*/


        /*
        reservations = new ArrayList<>();
        //reservations.add(new Reservation(Integer id, Integer fromTime, Integer toTime, String userId, String purpose, Integer roomId));
        reservations.add(new Reservation(6, 10, 100, ")DSA)J(D(J", "none", 33));
        reservations.add(new Reservation(2, 120, 200, "9sffjjsj", "ass", 34));
        reservations.add(new Reservation(3, 120, 300, "f09sdkfs0", "none", 43));
        reservations.add(new Reservation(4, 210, 1000, "f9k0s0k9fs", "licking", 35));
        reservations.add(new Reservation(5, 130, 300, "0fksfk90sd", "none", 36));
        */


        reservations = new ArrayList<>();
        roomObjects = new ArrayList<Room>();
        roomList = new ArrayList<>();

        output = findViewById(R.id.reservationRecycleView);
        roomSpinner = findViewById(R.id.roomSpinner);


        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,roomList);
        roomSpinner.setAdapter(spinnerAdapter);

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView mainTitle = findViewById(R.id.titleElement);

                if (roomList.size()>0) {
                    selectedRoom = roomObjects.get(position);
                    mainTitle.setText("Reservations for room " + roomList.get(position));
                    getAndShowRoomReservations(selectedRoom.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLockTask();


        getAndShowAllRooms();
        getAndShowAllReservations();
    }

    @Override
    public void onItemClick(View view, int position) {
        Reservation res = adapter.getItem(position);
        Intent intent = new Intent(this, ReservationOverview.class);

        intent.putExtra("reservation_value", res);

        startActivity(intent);
    }

    public void getAndShowAllReservations() {
        ReservationService service = ApiUtils.getReservationService();
        Call<List<Reservation>> allReservations = service.getAllReservations();

        allReservations.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    List<Reservation> resultList = response.body();
                    reservations = new ArrayList<>(resultList);

                    Log.d(TAG, reservations.size()+"");
                    updateRecycleView();
                } else {
                    String message = "Problem " + response.code() + " " + response.message();
                    Log.d(TAG, message);
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void getAndShowRoomReservations(int roomId) {
        Log.d(TAG, "trying by room");

        ReservationService service = ApiUtils.getReservationService();
        Call<List<Reservation>> reservationsByRoom = service.getReservationsByRoomId(roomId);


        reservationsByRoom.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    List<Reservation> resultList = response.body();
                    reservations = new ArrayList<>(resultList);

                    Log.d(TAG, "by room:"+reservations.size()+"");
                    updateRecycleView();
                } else {
                    String message = "Problem " + response.code() + " " + response.message();
                    Log.d(TAG, message);
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }


    public void getAndShowAllRooms() {
        RoomService roomService = ApiUtils.getRoomService();
        Call<List<Room>> allComments = roomService.getAllRooms();

        allComments.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> resultList = response.body();
                    roomObjects = new ArrayList<>(resultList);

                    updateRoomSpinner();
                } else {
                    String message = "Problem " + response.code() + " " + response.message();
                    Log.d(TAG, message);
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }



    public void updateRecycleView() {
        output.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewReservation(this, reservations);
        adapter.setClickListener(this);

        output.setAdapter(adapter);


        Log.d(TAG, reservations.toString());
    }

    public void updateRoomSpinner() {
        roomList.clear();
        for (int i = 0; i<roomObjects.size(); i++) {
            Room r = roomObjects.get(i);
            String str = "["+r.getId()+"] - "+r.getName();
            roomList.add(str);
            Log.e(TAG, str);
        }


        if (roomSpinner.getSelectedItemId() == Long.MIN_VALUE) {
            roomSpinner.setId(0);
            Log.e(TAG, "selected spinner zero");
        }


        roomSpinner.setAdapter(spinnerAdapter);
    }

    public void addReservationMethod(View view) {
        Intent intent = new Intent(this, NewReservation.class);
        intent.putExtra("room", selectedRoom);

        startActivityForResult(intent, 22);
    }
}

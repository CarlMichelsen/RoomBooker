package com.example.roombooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.roombooker.REST.ApiUtils;
import com.example.roombooker.REST.Reservation;
import com.example.roombooker.REST.ReservationService;
import com.example.roombooker.RecyclerView.RecyclerViewReservation;
import com.example.roombooker.RecyclerView.RecyclerViewSimpleAdapter;
import com.example.roombooker.data.Debug;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenActivity extends AppCompatActivity implements RecyclerViewReservation.ItemClickListener {
    private String TAG;

    TextView out;

    private RecyclerView output;
    RecyclerViewReservation adapter;

    ArrayList<Reservation> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        TAG = Debug.getInstance().TAG;

        /*
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);*/

        reservations = new ArrayList<>();
        //reservations.add(new Reservation(Integer id, Integer fromTime, Integer toTime, String userId, String purpose, Integer roomId));
        reservations.add(new Reservation(6, 10, 100, ")DSA)J(D(J", "none", 33));
        reservations.add(new Reservation(2, 120, 200, "9sffjjsj", "ass", 34));
        reservations.add(new Reservation(3, 120, 300, "f09sdkfs0", "none", 43));
        reservations.add(new Reservation(4, 210, 1000, "f9k0s0k9fs", "licking", 35));
        reservations.add(new Reservation(5, 130, 300, "0fksfk90sd", "none", 36));



        out = findViewById(R.id.outputElement);
        output = findViewById(R.id.reservationRecycleView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startLockTask();

        getAndShowAllReservations();
    }

    @Override
    public void onItemClick(View view, int position) {
        //Comment comment = adapter.getItem(position);
        // Toast.makeText(this, "You clicked " + animal + " on row number " + position, Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(this, CommentDetails.class);
        //intent.putExtra(CommentDetails.COMMENT, comment);
        //startActivity(intent);
    }

    public void getAndShowAllReservations() {

        ReservationService service = ApiUtils.getReservationService();
        Call<List<Reservation>> allComments = service.getAllReservations();

        allComments.enqueue(new Callback<List<Reservation>>() {
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

    public void updateRecycleView() {
        output.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewReservation(this, reservations);
        adapter.setClickListener(this);

        output.setAdapter(adapter);


        Log.d(TAG, reservations.toString());
    }

    public void testMethod(View view) {
        stopLockTask();
        finish();
    }

    public void refreshMethod(View view) {
        getAndShowAllReservations();
    }
}

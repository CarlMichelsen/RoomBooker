package com.example.roombooker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roombooker.data.BookerDebug;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password;
    private TextView loginStatus;
    private Spinner building, organization;

    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TAG = BookerDebug.getInstance().TAG;

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        building = findViewById(R.id.buildingSpinner);
        organization = findViewById(R.id.orgSpinner);
        loginStatus = findViewById(R.id.loginStatus);

        email.setText("");
        password.setText("");


        mAuth = FirebaseAuth.getInstance();



        ArrayList<String> listBuilding = new ArrayList<>();
        listBuilding.add("Roskilde");
        listBuilding.add("Næstved");
        ArrayAdapter<String> arrayAdapterBuilding = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listBuilding);
        building.setAdapter(arrayAdapterBuilding);
        building.setId(0);


        ArrayList<String> listOrg = new ArrayList<>();
        listOrg.add("Zealand");
        ArrayAdapter<String> arrayAdapterOrg = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOrg);
        organization.setAdapter(arrayAdapterOrg);
        organization.setId(0);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        loginStatus.setText("none");
        loginStatus.setAlpha(0);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Log.d(TAG, "user: "+currentUser.getEmail());
        } else {
            Log.d(TAG, "no user logged in");
        }
    }

    public void login(View view) {


        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        if (emailText == null) emailText = "";
        if (passwordText == null) passwordText = "";

        loginStatus.setText("loading...");
        loginStatus.setAlpha(1);

        if (emailText.length()<4 || passwordText.length()<4) {
            loginStatus.setText("invalid input");
            loginStatus.setAlpha(1);
            return;
        }

        mAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            loginStatus.setText("logged in");
                            loginStatus.setAlpha(1);

                            changeView();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);

                            loginStatus.setText("login failed");
                            loginStatus.setAlpha(1);
                        }
                    }
                });

        email.setText("");
        password.setText("");
    }

    public void changeView() {
        Intent intent = new Intent(this, ScreenActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
    }

    public void gotoHelpActivity(View view) {
        Intent intent = new Intent(this, HelpPage.class);
        startActivity(intent);
    }
}

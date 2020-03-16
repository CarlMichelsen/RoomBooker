package com.example.roombooker.data;

public class Debug {
    public String TAG;


    private Debug() {
        TAG = "Booker";

    }






    private static Debug instance = null;
    public static Debug getInstance() {
        if (instance == null) instance = new Debug();
        return instance;
    }
}
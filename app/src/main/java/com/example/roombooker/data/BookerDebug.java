package com.example.roombooker.data;

public class BookerDebug {
    public String TAG;


    private BookerDebug() {
        TAG = "Booker";

    }






    private static BookerDebug instance = null;
    public static BookerDebug getInstance() {
        if (instance == null) instance = new BookerDebug();
        return instance;
    }
}
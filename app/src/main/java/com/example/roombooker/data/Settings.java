package com.example.roombooker.data;

public class Settings {
    public boolean isRoom;


    private Settings() {
        isRoom = false;

    }






    private static Settings instance = null; //TODO save deserialized instance and load when needed instead of creating a new one
    public static Settings getInstance() {
        if (instance == null) instance = new Settings();
        return instance;
    }
}

package com.example.roombooker.REST;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RoomService {
    @GET("Rooms")
    Call<List<Room>> getAllRooms();

    @GET("Rooms/free/{time}")
    Call<List<Room>> getRoomsByTime(int time);

    @GET("Rooms/{id}")
    Call<Room> getRoomById(int id);

    @GET("Rooms/name/{roomName}")
    Call<Room> getRoomByRoomName(int roomName);
}

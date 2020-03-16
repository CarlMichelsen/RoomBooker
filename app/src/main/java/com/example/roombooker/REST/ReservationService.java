package com.example.roombooker.REST;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ReservationService {
    @GET("Reservations")
    Call<List<Reservation>> getAllReservations();

    @GET("Reservations/{id}")
    Call<Reservation> getReservationById(int id);

    @GET("Reservations/room/{roomId}")
    Call<List<Reservation>> getReservationsByRoomId(int roomId);

    @GET("Reservations/user/{userId}/{roomId}")
    Call<List<Reservation>> getReservationsByUserIdAndRoomId(int userId, int roomId);

    @GET("Reservations/room/{roomId}/{fromTime}/{toTime}")
    Call<List<Reservation>> getReservationsByRoomIdAndTime(int roomId, int fromTime, int toTime);

    @GET("Reservations/room/{roomId}/{fromTime}")
    Call<List<Reservation>> getReservationsByRoomIdAndFromTime(int roomId, int fromTime);

    @GET("Reservations/user/{userId}")
    Call<List<Reservation>> getReservationsByUser(int userId);

    @GET("Reservations/user/{userId}/{fromTime}")
    Call<List<Reservation>> getReservationsByUserFromTime(int userId, int fromTime);

    @DELETE("Reservations/{id}")
    Call<List<Reservation>> deleteReservation(int id);

    @POST("Reservations")
    Call<List<Reservation>> addReservation(Reservation input);
}
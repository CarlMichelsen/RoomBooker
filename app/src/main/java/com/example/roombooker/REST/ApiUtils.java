package com.example.roombooker.REST;

public class ApiUtils {
    private ApiUtils() {
    }

    private static final String BASE_URL = "http://anbo-roomreservationv3.azurewebsites.net/api/";

    public static ReservationService getReservationService() {
        return RetrofitClient.getClient(BASE_URL).create(ReservationService.class);
    }

    public static ReservationService getRoomService() {
        return RetrofitClient.getClient2(BASE_URL).create(ReservationService.class);
    }
}

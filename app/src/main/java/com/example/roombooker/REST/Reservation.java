package com.example.roombooker.REST;

import java.io.Serializable;

public class Reservation implements Serializable
{

    private Integer id;
    private Integer fromTime;
    private Integer toTime;
    private String userId;
    private String purpose;
    private Integer roomId;
    private final static long serialVersionUID = 3461550986750271020L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Reservation() {
    }

    /**
     *
     * @param purpose
     * @param fromTime
     * @param id
     * @param userId
     * @param toTime
     * @param roomId
     */
    public Reservation(Integer id, Integer fromTime, Integer toTime, String userId, String purpose, Integer roomId) {
        super();
        this.id = id;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.userId = userId;
        this.purpose = purpose;
        this.roomId = roomId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromTime() {
        return fromTime;
    }

    public void setFromTime(Integer fromTime) {
        this.fromTime = fromTime;
    }

    public Integer getToTime() {
        return toTime;
    }

    public void setToTime(Integer toTime) {
        this.toTime = toTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "id:"+id+" fromTime:"+fromTime+" toTime:"+toTime+" userId:"+userId+" purpose:"+purpose+" roomId"+roomId;
    }

}
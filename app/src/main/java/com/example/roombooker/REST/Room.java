package com.example.roombooker.REST;

import java.io.Serializable;

public class Room implements Serializable
{

    private Integer id;
    private String name;
    private String description;
    private Integer capacity;
    private String remarks;
    private final static long serialVersionUID = 4113285932282277968L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Room() {
    }

    /**
     *
     * @param name
     * @param description
     * @param id
     * @param remarks
     * @param capacity
     */
    public Room(Integer id, String name, String description, Integer capacity, String remarks) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.remarks = remarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "id:"+id+" name:"+name+" description:"+description+" capacity:"+capacity+" remarks:"+remarks;
    }

}
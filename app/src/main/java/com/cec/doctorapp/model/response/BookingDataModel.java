package com.cec.doctorapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingDataModel {


    public boolean isDummy() {
        return dummy;
    }

    private final boolean dummy;

    public BookingDataModel() {
        this.dummy=false;
    }

    public BookingDataModel(boolean dummy) {
        this.id=-1;
        this.dummy=dummy;
    }


    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("from_time")
    @Expose
    public String fromTime;

    @SerializedName("name")
    @Expose
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(String isBooked) {
        this.isBooked = isBooked;
    }

    @SerializedName("to_time")
    @Expose
    public String toTime;
    @SerializedName("is_booked")
    @Expose
    public String isBooked;


}

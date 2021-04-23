package com.cec.doctorapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAvailabilityModel {

    @SerializedName("timing")
    @Expose
    public ArrayList<Timing> timing = null;
    @SerializedName("is_avail_now")
    @Expose
    public Integer isAvailNow;
    @SerializedName("future_avail_status")
    @Expose
    public Integer futureAvailStatus;
    @SerializedName("from_date")
    @Expose
    public String fromDate;
    @SerializedName("to_date")
    @Expose
    public String toDate;


}

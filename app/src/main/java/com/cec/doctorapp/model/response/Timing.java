package com.cec.doctorapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


    public class Timing {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("day")
        @Expose
        public String day;
        @SerializedName("from_time")
        @Expose
        public String fromTime;
        @SerializedName("to_time")
        @Expose
        public String toTime;

    }

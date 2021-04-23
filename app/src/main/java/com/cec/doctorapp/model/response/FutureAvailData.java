package com.cec.doctorapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FutureAvailData {
        @SerializedName("from_date")
        @Expose
        public String fromDate;
        @SerializedName("to_date")
        @Expose
        public String toDate;
    }




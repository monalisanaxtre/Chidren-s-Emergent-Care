package com.cec.doctorapp.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBookData {
    @SerializedName("date")
    @Expose
    public String date;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}

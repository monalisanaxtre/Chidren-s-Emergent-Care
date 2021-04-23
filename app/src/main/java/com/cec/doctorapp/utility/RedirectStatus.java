package com.cec.doctorapp.utility;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedirectStatus {

    @SerializedName("Status")
    @Expose
    public Boolean status;

    @SerializedName("Message")
    @Expose
    public String message;
}

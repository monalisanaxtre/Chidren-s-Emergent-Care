package com.cec.doctorapp.model.response;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FbFeedWrapper {

    @SerializedName("data")
    @Expose
    public ArrayList<GetFeedModel> data = new ArrayList<>() ;

}
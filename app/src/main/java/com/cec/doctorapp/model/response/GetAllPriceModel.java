package com.cec.doctorapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllPriceModel {

    @SerializedName("sl_no")
    @Expose
    public Integer slNo;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("offer")
    @Expose
    public String offer;
    @SerializedName("price")
    @Expose
    public Integer price;
    @SerializedName("description")
    @Expose
    public Object description;

}
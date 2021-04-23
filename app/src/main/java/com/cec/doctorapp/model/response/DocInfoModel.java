package com.cec.doctorapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocInfoModel {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("specialty")
    @Expose
    public String specialty;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("img_doc")
    @Expose
    public String imgDoc;
    @SerializedName("rating")
    @Expose
    public  Double rating;

}

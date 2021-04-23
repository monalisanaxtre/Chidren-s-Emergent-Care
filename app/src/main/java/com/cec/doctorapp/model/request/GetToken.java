package com.cec.doctorapp.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetToken {
    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("category_list")
    @Expose
    public List<CategoryList> categoryList = null;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("tasks")
    @Expose
    public List<String> tasks = null;


}

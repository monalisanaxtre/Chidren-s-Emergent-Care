package com.cec.doctorapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataBookModel  {

    @SerializedName("id")
    @Expose
    public Integer id=14;
    @SerializedName("book_date")
    @Expose
    public String bookDate="20-12-08";
    @SerializedName("book_time")
    @Expose
    public String bookTime="13:53:00";
}


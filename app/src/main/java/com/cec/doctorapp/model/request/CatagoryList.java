package com.cec.doctorapp.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 class CategoryList {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;

    }


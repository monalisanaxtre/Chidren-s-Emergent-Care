package com.cec.doctorapp.model.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFeedModel {

    @SerializedName("created_time")
    @Expose
    public String createdTime;

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_picture() {
        return full_picture;
    }

    public void setFull_picture(String full_picture) {
        this.full_picture = full_picture;
    }

    public String getPermalink_url() {
        return permalink_url;
    }

    public void setPermalink_url(String permalink_url) {
        this.permalink_url = permalink_url;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("full_picture")
    @Expose
    public String full_picture;
    @SerializedName("permalink_url")
    @Expose
    public String permalink_url;
    @SerializedName("picture")
    @Expose
    public String picture;

}


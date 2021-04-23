package com.cec.doctorapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetImagesFb {

    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("is_silhouette")
    @Expose
    public Boolean isSilhouette;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("width")
    @Expose
    public Integer width;

}

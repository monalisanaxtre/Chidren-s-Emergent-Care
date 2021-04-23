package com.cec.doctorapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpRegisterResponseModelBean {
    @SerializedName("booking_no")
    @Expose
    public String bookingNo;
    @SerializedName("dr_name")
    @Expose
    public String drName;
    @SerializedName("dr_speciality")
    @Expose
    public String drSpeciality;
    @SerializedName("dr_desc")
    @Expose
    public String drDesc;

    public String getImgDoc() {
        return imgDoc;
    }

    public void setImgDoc(String imgDoc) {
        this.imgDoc = imgDoc;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @SerializedName("img_doc")
    @Expose
    public String imgDoc;
    @SerializedName("rating")
    @Expose
    public  Double rating;

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrSpeciality() {
        return drSpeciality;
    }

    public void setDrSpeciality(String drSpeciality) {
        this.drSpeciality = drSpeciality;
    }

    public String getDrDesc() {
        return drDesc;
    }

    public void setDrDesc(String drDesc) {
        this.drDesc = drDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("book_date")
    @Expose
    public String bookDate;
    @SerializedName("book_time")
    @Expose
    public String bookTime;
}

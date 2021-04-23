package com.cec.doctorapp.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//public class PatientRegisterModel{
//    @SerializedName("status")
//    @Expose
//    public Integer status;
//    @SerializedName("msg")
//    @Expose
//    public String msg;
//    @SerializedName("dev_info")
//    @Expose
//    public String devInfo;
//    @SerializedName("patientregister")
//    @Expose
//    public Patientregister patientregister;
//}

public class PatientRegisterModel implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("date")
    @Expose
    private String date;

    public PatientRegisterModel(Parcel in) {
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        time = in.readString();
        date = in.readString();
    }

    public static final Creator<PatientRegisterModel> CREATOR = new Creator<PatientRegisterModel>() {
        @Override
        public PatientRegisterModel createFromParcel(Parcel in) {
            return new PatientRegisterModel(in);
        }

        @Override
        public PatientRegisterModel[] newArray(int size) {
            return new PatientRegisterModel[size];
        }
    };

    public PatientRegisterModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(time);
        dest.writeString(date);
    }
}


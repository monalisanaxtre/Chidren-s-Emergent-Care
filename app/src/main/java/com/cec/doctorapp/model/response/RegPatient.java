package com.cec.doctorapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegPatient {
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("msg")
        @Expose
        public String msg;
        @SerializedName("dev_info")
        @Expose
        public String devInfo;
        @SerializedName("data")
        @Expose
        public PatientRegisterResponseBean patientRegisterResponseBean;


}

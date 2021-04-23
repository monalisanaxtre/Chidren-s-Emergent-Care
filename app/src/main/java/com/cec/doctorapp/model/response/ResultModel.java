package com.cec.doctorapp.model.response;

public class ResultModel<T> {
    public Integer status = -1;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T data;
    public String msg = "";
    public String key = "";
    public T getResultData() {
        return data;
    }

    public void setResultData(T result_data) {
        this.data = result_data;
    }
}


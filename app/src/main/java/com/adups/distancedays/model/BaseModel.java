package com.adups.distancedays.model;

import com.google.gson.annotations.SerializedName;

/**
 * Response base model.
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public class BaseModel<T> {

    public static final int SUCCESS = 200;

    @SerializedName(value = "id", alternate = {"resultcode"})
    public int resultCode = -1;
    public String reason;
    public T result;
    @SerializedName("error_code")
    public int errorCode;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}

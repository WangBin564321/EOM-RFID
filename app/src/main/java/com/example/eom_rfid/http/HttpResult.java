package com.example.eom_rfid.http;

public class HttpResult<T> {

    int code;
    boolean desc;
    T content;
    boolean isSuccess;

    public HttpResult(int code, boolean desc) {
        this.code = code;
        this.desc = desc;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}

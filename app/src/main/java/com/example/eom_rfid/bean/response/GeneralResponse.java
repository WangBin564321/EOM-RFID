package com.example.eom_rfid.bean.response;

/**
 * Description:
 * Author:bwang
 * Date:2020/12/3 16:29
 */
public class GeneralResponse<T> {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private int code;
    private String msg;
    private T data;
}

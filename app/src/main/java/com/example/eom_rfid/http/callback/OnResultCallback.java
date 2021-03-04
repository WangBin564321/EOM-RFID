package com.example.eom_rfid.http.callback;


import com.example.eom_rfid.bean.response.GeneralResponse;
import com.example.eom_rfid.http.exception.ExceptionHandle;

public interface OnResultCallback {

    interface GeneralCallback {
        void doSuccess(String message);

        void doFail(String message);

        void error(ExceptionHandle.ResponseException e);
    }

    interface GetTCallback<T> {
        void getSuccess(T t);

        void getFail(String message);

        void error(ExceptionHandle.ResponseException e);
    }

}

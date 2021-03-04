package com.example.eom_rfid.ui.activity.check.result;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.eom_rfid.base.BaseViewModel;
import com.example.eom_rfid.bean.body.SubmitCheckBody;
import com.example.eom_rfid.http.RemoteSource;
import com.example.eom_rfid.http.callback.OnResultCallback;
import com.example.eom_rfid.http.exception.ExceptionHandle;

import static com.example.eom_rfid.utils.Constants.HTAG;

/**
 * Description:
 * Author:bwang
 * Date:2021/1/15 19:30
 */
public class ScanResultViewModel extends BaseViewModel {
    private RemoteSource remoteSource;
    public MutableLiveData<SubmitCheckBody> submitCheckBodyMutableLiveData = new MutableLiveData<>();

    public ScanResultViewModel() {
        remoteSource = new RemoteSource();
    }

    public void submitCheck() {
        SubmitCheckBody submitCheckBody = new SubmitCheckBody();
        submitCheckBody.setStatus(1);
        remoteSource.submitCheck(submitCheckBody, new OnResultCallback.GeneralCallback() {
            @Override
            public void doSuccess(String message) {
                showToast("提交成功", 0);
            }

            @Override
            public void doFail(String message) {
                showToast(message, 1);
            }

            @Override
            public void error(ExceptionHandle.ResponseException e) {
            }
        });
    }
}

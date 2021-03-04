package com.example.eom_rfid.ui.activity.main;

import androidx.lifecycle.MutableLiveData;

import com.example.eom_rfid.base.AppManager;
import com.example.eom_rfid.base.BaseApplication;
import com.example.eom_rfid.base.BaseViewModel;
import com.example.eom_rfid.http.RemoteSource;
import com.example.eom_rfid.http.callback.OnResultCallback;
import com.example.eom_rfid.http.exception.ExceptionHandle;
import com.example.eom_rfid.ui.activity.login.LoginActivity;
import com.example.eom_rfid.utils.SPUtil;

/**
 * Description:
 * Author:bwang
 * Date:2021/1/13 17:58
 */
public class MainViewModel extends BaseViewModel {

    public MutableLiveData<String> userName = new MutableLiveData<>("");
    private RemoteSource remoteSource;

    public MainViewModel() {
        remoteSource = new RemoteSource();
    }

    public void logout() {
        remoteSource.logout(new OnResultCallback.GeneralCallback() {
            @Override
            public void doSuccess(String message) {
                AppManager.getAppManager().finishAllActivity();
                SPUtil.clearInfo(BaseApplication.getInstance().getApplicationContext(), "LoginInfos");
                startActivity(LoginActivity.class);
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

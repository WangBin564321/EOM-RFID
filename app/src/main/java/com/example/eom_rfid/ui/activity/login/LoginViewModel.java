package com.example.eom_rfid.ui.activity.login;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.example.eom_rfid.base.BaseApplication;
import com.example.eom_rfid.base.BaseViewModel;
import com.example.eom_rfid.bean.body.LoginBody;
import com.example.eom_rfid.bean.response.LoginResponse;
import com.example.eom_rfid.http.RemoteSource;
import com.example.eom_rfid.http.callback.OnResultCallback;
import com.example.eom_rfid.http.exception.ExceptionHandle;
import com.example.eom_rfid.ui.activity.main.MainActivity;
import com.example.eom_rfid.utils.SPUtil;
import com.example.eom_rfid.utils.ToastUtil;


/**
 * Description:
 * Author:bwang
 * Date:2020/1/13 5:47
 */
public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRemember = new MutableLiveData<>(false);

    private RemoteSource remoteSource;

    public LoginViewModel() {
        remoteSource = new RemoteSource();
    }

    public void toMain() {
        if (TextUtils.isEmpty(userName.getValue())) {
            ToastUtil.showShort("用户名不能为空");
        } else if (TextUtils.isEmpty(password.getValue())) {
            ToastUtil.showShort("密码不能为空");
        } else {
            remoteSource.login(new LoginBody(userName.getValue(), password.getValue()), new OnResultCallback.GetTCallback() {
                @Override
                public void getSuccess(Object o) {
                    LoginResponse loginResponse = (LoginResponse) o;
                    SPUtil.saveUserName(BaseApplication.getInstance().getApplicationContext(),
                            userName.getValue());
                    SPUtil.savePassword(BaseApplication.getInstance().getApplicationContext(),
                            password.getValue());
                    SPUtil.saveToken(BaseApplication.getInstance().getApplicationContext(),
                            loginResponse.getToken());
                    SPUtil.saveIsRemember(BaseApplication.getInstance().getApplicationContext(), isRemember.getValue());
                    startActivity(MainActivity.class);
                }

                @Override
                public void getFail(String message) {
                    ToastUtil.showShort(message);
                }

                @Override
                public void error(ExceptionHandle.ResponseException e) {
                    ToastUtil.showShort(e.getMessage());
                }
            });
        }
    }

}

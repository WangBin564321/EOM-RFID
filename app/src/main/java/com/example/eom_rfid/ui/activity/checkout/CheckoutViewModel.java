package com.example.eom_rfid.ui.activity.checkout;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.eom_rfid.base.BaseViewModel;
import com.example.eom_rfid.bean.body.BillListBody;
import com.example.eom_rfid.bean.body.CheckoutBatchBody;
import com.example.eom_rfid.bean.body.CheckoutBody;
import com.example.eom_rfid.bean.body.GetSingleInfoBody;
import com.example.eom_rfid.bean.entity.ScanRfidResult;
import com.example.eom_rfid.bean.response.BatchInfoResponse;
import com.example.eom_rfid.bean.response.BillListResponse;
import com.example.eom_rfid.bean.response.CheckoutSingleInfoResponse;
import com.example.eom_rfid.bean.response.TaskResponse;
import com.example.eom_rfid.http.RemoteSource;
import com.example.eom_rfid.http.callback.OnResultCallback;
import com.example.eom_rfid.http.exception.ExceptionHandle;

import java.util.List;

import static com.example.eom_rfid.utils.Constants.HTAG;

/**
 * Description:
 * Author:bwang
 * Date:2021/1/13 19:17
 */
public class CheckoutViewModel extends BaseViewModel {

    public MutableLiveData<List<ScanRfidResult.EpcDataModel>> epcMutableLiveData = new MutableLiveData<>();


    public MutableLiveData<String> billId = new MutableLiveData<>();
    public MutableLiveData<String> stockNum = new MutableLiveData<>();

    public MutableLiveData<String> checkoutType = new MutableLiveData<>();
    public MutableLiveData<String> taskId = new MutableLiveData<>();
    public MutableLiveData<String> destroyCause = new MutableLiveData<>();
    public MutableLiveData<String> receiver = new MutableLiveData<>();
    public MutableLiveData<String> returnDate = new MutableLiveData<>();
    public MutableLiveData<String> remark = new MutableLiveData<>();

    public MutableLiveData<TaskResponse> taskMutableLiveData =
            new MutableLiveData<>();


    private RemoteSource remoteSource;

    public CheckoutViewModel() {
        remoteSource = new RemoteSource();
    }

    public MutableLiveData<Boolean> getSuccess = new MutableLiveData<>(false);
    public MutableLiveData<CheckoutSingleInfoResponse.DataBean> checkoutSingleInfoResponseMutableLiveData = new MutableLiveData<>();

    /**
     * 请求接口
     *
     * @param rfidCode
     */
    public void getSingleInfo(String rfidCode) {
        remoteSource.getSingleInfo(new GetSingleInfoBody(rfidCode), new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                CheckoutSingleInfoResponse checkoutSingleInfoResponse = (CheckoutSingleInfoResponse) o;
                checkoutSingleInfoResponseMutableLiveData.setValue(checkoutSingleInfoResponse.getData());
                if (checkoutSingleInfoResponse.getData() != null) {
                    getSuccess.setValue(true);
                } else {
                    showToast("暂无信息", 1);
                }

            }

            @Override
            public void getFail(String message) {
                getSuccess.setValue(false);
                showToast(message, 1);
            }

            @Override
            public void error(ExceptionHandle.ResponseException e) {
                getSuccess.setValue(false);

            }
        });
    }

    public void checkout(CheckoutBody checkoutBody) {
        if (checkoutType.getValue().equals("2")) {
            if (TextUtils.isEmpty(returnDate.getValue())) {
                showToast("归还日期必填", 1);
            }else {
                remoteSource.checkout(checkoutBody, new OnResultCallback.GeneralCallback() {
                    @Override
                    public void doSuccess(String message) {
                        showToast("出库成功", 0);
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
        }else {
            remoteSource.checkout(checkoutBody, new OnResultCallback.GeneralCallback() {
                @Override
                public void doSuccess(String message) {
                    showToast("出库成功", 0);
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



    public void getTaskList() {
        remoteSource.getTaskList(new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                taskMutableLiveData.setValue((TaskResponse) o);
            }

            @Override
            public void getFail(String message) {
                showToast(message, 1);
            }

            @Override
            public void error(ExceptionHandle.ResponseException e) {

            }
        });
    }

}

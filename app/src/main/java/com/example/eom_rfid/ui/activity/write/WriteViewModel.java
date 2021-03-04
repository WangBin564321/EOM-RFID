package com.example.eom_rfid.ui.activity.write;

import androidx.lifecycle.MutableLiveData;

import com.example.eom_rfid.base.BaseViewModel;
import com.example.eom_rfid.bean.body.GetSingleInfoBody;
import com.example.eom_rfid.bean.body.WriteNumBody;
import com.example.eom_rfid.bean.response.CheckoutSingleInfoResponse;
import com.example.eom_rfid.http.RemoteSource;
import com.example.eom_rfid.http.callback.OnResultCallback;
import com.example.eom_rfid.http.exception.ExceptionHandle;
import com.example.eom_rfid.utils.ToolUtil;

public class WriteViewModel extends BaseViewModel {

    private RemoteSource remoteSource;
    public MutableLiveData<String> rfid = new MutableLiveData<>();
    public MutableLiveData<String> rfidCode = new MutableLiveData<>();
    public MutableLiveData<String> num = new MutableLiveData<>();
    public MutableLiveData<String> location = new MutableLiveData<>();
    public MutableLiveData<String> type = new MutableLiveData<>();


    public WriteViewModel() {
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
        getSuccess.setValue(false);
        remoteSource.getSingleInfo(new GetSingleInfoBody(rfidCode), new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                CheckoutSingleInfoResponse checkoutSingleInfoResponse = (CheckoutSingleInfoResponse) o;
                checkoutSingleInfoResponseMutableLiveData.setValue(checkoutSingleInfoResponse.getData());
                if (checkoutSingleInfoResponse.getData() != null) {
                    getSuccess.setValue(true);
                    num.setValue(ToolUtil.isNull(checkoutSingleInfoResponse.getData().getStockNum()) + "");
                    location.setValue(ToolUtil.isNullTo(checkoutSingleInfoResponse.getData().getWarehouse()) + "/" + ToolUtil.isNullTo(checkoutSingleInfoResponse.getData().getZone()) + "/" + ToolUtil.isNullTo(checkoutSingleInfoResponse.getData().getShelfNumber()));

                    switch (checkoutSingleInfoResponse.getData().getMaterialType()) {
                        case "drug":
                            type.setValue("药品");
                            break;
                        case "material":
                            type.setValue("物资");
                            break;
                        case "equipment":
                            type.setValue("设备");
                            break;
                    }
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

    public void writeNum() {
        remoteSource.writeNum(new WriteNumBody(checkoutSingleInfoResponseMutableLiveData.getValue().getRfidCode(), checkoutSingleInfoResponseMutableLiveData.getValue().getId(), Integer.parseInt(num.getValue())), new OnResultCallback.GeneralCallback() {
            @Override
            public void doSuccess(String message) {
                showToast("同步数据库成功", 0);
                getSingleInfo(checkoutSingleInfoResponseMutableLiveData.getValue().getRfidCode());
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

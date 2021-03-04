package com.example.eom_rfid.ui.activity.checkout;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.eom_rfid.base.BaseViewModel;
import com.example.eom_rfid.bean.body.CheckoutBatchBody;
import com.example.eom_rfid.bean.entity.BatchBean;
import com.example.eom_rfid.bean.response.BatchInfoResponse;
import com.example.eom_rfid.bean.response.BillListResponse;
import com.example.eom_rfid.http.RemoteSource;
import com.example.eom_rfid.http.callback.OnResultCallback;
import com.example.eom_rfid.http.exception.ExceptionHandle;

import java.util.ArrayList;
import java.util.List;

import static com.example.eom_rfid.utils.Constants.HTAG;


public class BatchCheckoutViewModel extends BaseViewModel {

    private RemoteSource remoteSource;
    public MutableLiveData<String> num = new MutableLiveData<>();
    public MutableLiveData<String> bill = new MutableLiveData<>();
    CheckoutBatchBody checkoutBatchBody;
    public MutableLiveData<BatchInfoResponse> batchInfoMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<BatchBean>> batchBeanMutableLiveData = new MutableLiveData<>();
    List<BatchBean> batchBeanList = new ArrayList<>();

    public BatchCheckoutViewModel() {
        remoteSource = new RemoteSource();
    }

    public void checkoutBatch() {
//        checkoutBatchBody = new CheckoutBatchBody();
        if (billMutableLiveData.getValue() != null) {
            checkoutBatchBody.setType(billMutableLiveData.getValue().getType());
            checkoutBatchBody.setTaskId(billMutableLiveData.getValue().getTaskId());
            checkoutBatchBody.setDestroyCause(billMutableLiveData.getValue().getDestroyCause());
            checkoutBatchBody.setWaitOutWarehouseId(billMutableLiveData.getValue().getId());
            checkoutBatchBody.setWaitOutWarehouseNumber(billMutableLiveData.getValue().getNumber());
            checkoutBatchBody.setReceiver(billMutableLiveData.getValue().getReceiver());
            checkoutBatchBody.setReturnDate(billMutableLiveData.getValue().getReturnDate());
            checkoutBatchBody.setRemark(billMutableLiveData.getValue().getRemark());
            checkoutBatchBody.setStatus(1);
        }
//        if (getBatchSuccess.getValue()) {
//            List<BatchInfoResponse.DataBean> dataBeans = new ArrayList<>();
//            for (int i = 0; i <batchInfoMutableLiveData.getValue().getData().size() ; i++) {
//                Log.d(HTAG, "checkoutBatch: =============>"+batchInfoMutableLiveData.getValue().getData().get(i).getName());
//                dataBeans.add(batchInfoMutableLiveData.getValue().getData().get(i));
//            }
//            checkoutBatchBody.setWmsOutWarehouseItemDTOList(dataBeans);
        remoteSource.checkoutBatch(checkoutBatchBody, new OnResultCallback.GeneralCallback() {
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
//        } else {
//            showToast("请获取出库单信息", 1);
//        }

    }

    public MutableLiveData<Boolean> getBatchSuccess = new MutableLiveData<>();

    public void getBatchInfo() {
        getBatchSuccess.setValue(false);
        remoteSource.getBatchInfo(bill.getValue(), new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                BatchInfoResponse batchInfoResponse = (BatchInfoResponse) o;
                List<BatchInfoResponse.DataBean> sd = new ArrayList<>();
                if (batchInfoResponse.getData() != null) {
                    batchBeanList = new ArrayList<>();
                    for (int i = 0; i < batchInfoResponse.getData().size(); i++) {
                        sd.add(batchInfoResponse.getData().get(i));
                        batchBeanList.add(new BatchBean(batchInfoResponse.getData().get(i).getRfidCode(), batchInfoResponse.getData().get(i).getName(), false));
                    }
                    batchBeanMutableLiveData.setValue(batchBeanList);
                }
                checkoutBatchBody = new CheckoutBatchBody();
                checkoutBatchBody.setWmsOutWarehouseItemDTOList(sd);
                batchInfoMutableLiveData.setValue(batchInfoResponse);
                num.setValue("出库数量：" + batchInfoResponse.getData().size() + "");
                getBatchSuccess.setValue(true);
            }

            @Override
            public void getFail(String message) {

            }

            @Override
            public void error(ExceptionHandle.ResponseException e) {

            }
        });
    }

    public MutableLiveData<BillListResponse> billListResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<BillListResponse.DataBean> billMutableLiveData = new MutableLiveData<>();

    public void getBillList(String number) {
        remoteSource.getBillList(number, new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                billListResponseMutableLiveData.setValue((BillListResponse) o);
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

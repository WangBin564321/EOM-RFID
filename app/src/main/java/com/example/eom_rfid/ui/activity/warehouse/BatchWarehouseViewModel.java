package com.example.eom_rfid.ui.activity.warehouse;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.eom_rfid.base.BaseViewModel;
import com.example.eom_rfid.bean.body.CheckoutBatchBody;
import com.example.eom_rfid.bean.body.WarehouseBatchBody;
import com.example.eom_rfid.bean.body.WarehouseBody;
import com.example.eom_rfid.bean.entity.BatchBean;
import com.example.eom_rfid.bean.response.BatchInfoResponse;
import com.example.eom_rfid.bean.response.BillListResponse;
import com.example.eom_rfid.bean.response.WarehouseBatchListResponse;
import com.example.eom_rfid.http.RemoteSource;
import com.example.eom_rfid.http.callback.OnResultCallback;
import com.example.eom_rfid.http.exception.ExceptionHandle;

import java.util.ArrayList;
import java.util.List;

import static com.example.eom_rfid.utils.Constants.HTAG;


public class BatchWarehouseViewModel extends BaseViewModel {

    private RemoteSource remoteSource;
    public MutableLiveData<String> num = new MutableLiveData<>();
    public MutableLiveData<String> bill = new MutableLiveData<>();
    WarehouseBatchBody warehouseBatchBody;

    public BatchWarehouseViewModel() {
        remoteSource = new RemoteSource();
    }

    public void warehouseBatch() {
        if (billMutableLiveData.getValue() != null) {
            warehouseBatchBody.setType("0");
            warehouseBatchBody.setWarehouse(billMutableLiveData.getValue().getWarehouse());
            warehouseBatchBody.setRemark(billMutableLiveData.getValue().getRemark());
        }
        remoteSource.warehouseBatch(warehouseBatchBody, new OnResultCallback.GeneralCallback() {
            @Override
            public void doSuccess(String message) {
                showToast("入库成功", 0);
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

    public MutableLiveData<Boolean> getBatchSuccess = new MutableLiveData<>();
    public MutableLiveData<List<BatchBean>> batchBeanMutableLiveData = new MutableLiveData<>();
    List<BatchBean> batchBeanList = new ArrayList<>();

    public void getBatchInfo() {
        getBatchSuccess.setValue(false);
        remoteSource.getWarehouseBatchList(bill.getValue(), new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                WarehouseBatchListResponse warehouseBatchListResponse = (WarehouseBatchListResponse) o;
                List<WarehouseBatchListResponse.DataBean> sd = new ArrayList<>();

                if (warehouseBatchListResponse.getData() != null) {
                    batchBeanList = new ArrayList<>();
                    for (int i = 0; i < warehouseBatchListResponse.getData().size(); i++) {
                        sd.add(warehouseBatchListResponse.getData().get(i));
                        batchBeanList.add(new BatchBean(warehouseBatchListResponse.getData().get(i).getRfidCode(), warehouseBatchListResponse.getData().get(i).getName(), false));
                    }
                    batchBeanMutableLiveData.setValue(batchBeanList);
                }

                warehouseBatchBody = new WarehouseBatchBody();
                warehouseBatchBody.setList(sd);

                num.setValue("入库数量：" + warehouseBatchListResponse.getData().size() + "");
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

    public void getWarehouseBillList(String number) {
        remoteSource.getWarehouseBillList(number, new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                billListResponseMutableLiveData.setValue((BillListResponse) o);
            }

            @Override
            public void getFail(String message) {

            }

            @Override
            public void error(ExceptionHandle.ResponseException e) {

            }
        });
    }
}

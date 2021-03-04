package com.example.eom_rfid.ui.activity.warehouse;

import androidx.lifecycle.MutableLiveData;

import com.example.eom_rfid.base.BaseViewModel;
import com.example.eom_rfid.bean.body.GetSingleInfoBody;
import com.example.eom_rfid.bean.body.WarehouseBody;
import com.example.eom_rfid.bean.response.CheckoutSingleInfoResponse;
import com.example.eom_rfid.bean.response.DictTypeResponse;
import com.example.eom_rfid.bean.response.WarehouseSingleResponse;
import com.example.eom_rfid.http.RemoteSource;
import com.example.eom_rfid.http.callback.OnResultCallback;
import com.example.eom_rfid.http.exception.ExceptionHandle;
import com.example.eom_rfid.utils.ToolUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author:bwang
 * Date:2021/1/13 19:19
 */
public class WarehouseViewModel extends BaseViewModel {

    private RemoteSource remoteSource;
    public MutableLiveData<DictTypeResponse> warehouseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DictTypeResponse> zoneMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DictTypeResponse> shelfMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DictTypeResponse> specificationUnitMutableLiveData = new MutableLiveData<>();
    private DictTypeResponse.ListBean listBean = new DictTypeResponse.ListBean();
    public MutableLiveData<String> warehouseTypeCode = new MutableLiveData<>();
    public MutableLiveData<String> warehouse = new MutableLiveData<>();
    public MutableLiveData<String> zone = new MutableLiveData<>();
    public MutableLiveData<String> shelf = new MutableLiveData<>();
    public MutableLiveData<String> remark = new MutableLiveData<>();


    public MutableLiveData<String> rfid = new MutableLiveData<>();
    public MutableLiveData<String> rfidCode = new MutableLiveData<>();
    public MutableLiveData<String> num = new MutableLiveData<>();
    public MutableLiveData<Boolean> getSuccess = new MutableLiveData<>();
    public MutableLiveData<WarehouseSingleResponse.DataBean> warehouseSingleInfoResponseMutableLiveData = new MutableLiveData<>();


    public WarehouseViewModel() {
        listBean.setDictLabel("请选择");
        listBean.setDictValue(null);
        remoteSource = new RemoteSource();
    }

    public void getWarehouse() {
        remoteSource.getByDictType("warehouse-type", new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                DictTypeResponse dictTypeResponse = (DictTypeResponse) o;
                dictTypeResponse.getList().add(listBean);
                warehouseMutableLiveData.setValue(dictTypeResponse);
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

    public void getZone() {
        remoteSource.getByDictType("zone", new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                DictTypeResponse dictTypeResponse = (DictTypeResponse) o;
                dictTypeResponse.getList().add(listBean);
                zoneMutableLiveData.setValue(dictTypeResponse);
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

    public void getShelf() {
        remoteSource.getByDictType("shelf", new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                DictTypeResponse dictTypeResponse = (DictTypeResponse) o;
                dictTypeResponse.getList().add(listBean);
                shelfMutableLiveData.setValue(dictTypeResponse);
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


    /**
     * 请求接口
     *
     * @param rfidCode
     */
    public void getWarehouseSingleInfo(String rfidCode) {
        remoteSource.getWarehouseSingleInfo(rfidCode, new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                WarehouseSingleResponse warehouseSingleResponse = (WarehouseSingleResponse) o;
                warehouseSingleInfoResponseMutableLiveData.setValue(warehouseSingleResponse.getData());
                if (warehouseSingleResponse.getData() != null) {
                    getSuccess.setValue(true);
                    num.setValue(ToolUtil.isNull(warehouseSingleResponse.getData().getInNum()) + "");
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

    public void warehouse() {
        WarehouseBody warehouseBody = new WarehouseBody();
        warehouseBody.setRemark(remark.getValue());
        warehouseBody.setWarehouse(warehouse.getValue());
        warehouseBody.setZone(zone.getValue());
        warehouseBody.setShelf(shelf.getValue());
        warehouseBody.setType("0");
        List<WarehouseSingleResponse.DataBean> dataBeans = new ArrayList<>();
        dataBeans.add(warehouseSingleInfoResponseMutableLiveData.getValue());
        warehouseBody.setList(dataBeans);
        remoteSource.warehouse(warehouseBody, new OnResultCallback.GeneralCallback() {
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


}

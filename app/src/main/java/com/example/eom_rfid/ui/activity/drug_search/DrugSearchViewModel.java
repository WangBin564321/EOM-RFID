package com.example.eom_rfid.ui.activity.drug_search;

import androidx.lifecycle.MutableLiveData;

import com.example.eom_rfid.base.BaseViewModel;
import com.example.eom_rfid.bean.body.DrugEditBody;
import com.example.eom_rfid.bean.body.DrugSearchBody;
import com.example.eom_rfid.bean.response.DictTypeResponse;
import com.example.eom_rfid.bean.response.DrugSearchResponse;
import com.example.eom_rfid.http.RemoteSource;
import com.example.eom_rfid.http.callback.OnResultCallback;
import com.example.eom_rfid.http.exception.ExceptionHandle;

import java.util.ArrayList;

/**
 * Description:
 * Author:bwang
 * Date:2021/1/13 19:31
 */
public class DrugSearchViewModel extends BaseViewModel {
    private RemoteSource remoteSource;
    public MutableLiveData<String> checkTypeCode = new MutableLiveData<>();
    public MutableLiveData<DrugSearchResponse.DataBean> dataBeanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> warehouse = new MutableLiveData<>();
    public MutableLiveData<String> zone = new MutableLiveData<>();
    public MutableLiveData<String> shelf = new MutableLiveData<>();
    private DictTypeResponse.ListBean listBean = new DictTypeResponse.ListBean();

    public DrugSearchViewModel() {
        listBean.setDictLabel("请选择");
        listBean.setDictValue(null);
        remoteSource = new RemoteSource();
    }

    public MutableLiveData<DrugSearchResponse> drugSearchResponseMutableLiveData = new MutableLiveData<>();

    public void searchDrug(String searchContent) {
        remoteSource.searchDrug(new DrugSearchBody(checkTypeCode.getValue(), searchContent), new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                DrugSearchResponse drugSearchResponse = (DrugSearchResponse) o;
                drugSearchResponseMutableLiveData.setValue((DrugSearchResponse) o);
                if (drugSearchResponse.getData() == null || drugSearchResponse.getData().size() == 0) {
                    showToast("暂无商品", 1);
                }
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

    public MutableLiveData<DictTypeResponse> warehouseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DictTypeResponse> shelfMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DictTypeResponse> zoneMutableLiveData = new MutableLiveData<>();

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

    public void editDrug() {
        remoteSource.editDrug(new DrugEditBody(dataBeanMutableLiveData.getValue().getRfidCode(),
                dataBeanMutableLiveData.getValue().getId(),
                warehouse.getValue(),
                zone.getValue(),
                shelf.getValue()), new OnResultCallback.GeneralCallback() {
            @Override
            public void doSuccess(String message) {
                showToast("编辑成功", 0);
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

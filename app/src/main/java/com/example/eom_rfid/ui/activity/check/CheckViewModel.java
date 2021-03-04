package com.example.eom_rfid.ui.activity.check;

import androidx.lifecycle.MutableLiveData;

import com.example.eom_rfid.base.BaseViewModel;
import com.example.eom_rfid.bean.body.CheckListBody;
import com.example.eom_rfid.bean.entity.ScanRfidResult;
import com.example.eom_rfid.bean.response.CheckListResponse;
import com.example.eom_rfid.bean.response.DictTypeResponse;
import com.example.eom_rfid.http.RemoteSource;
import com.example.eom_rfid.http.callback.OnResultCallback;
import com.example.eom_rfid.http.exception.ExceptionHandle;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author:bwang
 * Date:2021/1/13 19:12
 */
public class CheckViewModel extends BaseViewModel {

    public MutableLiveData<ScanRfidResult> scanRfidResultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ScanRfidResult.EpcDataModel>> epcMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<String>> listMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> warehouse = new MutableLiveData<>();
    public MutableLiveData<String> checkTypeName = new MutableLiveData<>();
    public MutableLiveData<String> checkTypeCode = new MutableLiveData<>();
    public MutableLiveData<String> zone = new MutableLiveData<>();
    public MutableLiveData<String> checkNum = new MutableLiveData<>();
    public MutableLiveData<String> checkedNum = new MutableLiveData<>();
    public MutableLiveData<String> errorNum = new MutableLiveData<>("");
    public MutableLiveData<Boolean> getSuccess = new MutableLiveData<>(false);
    List<String> stringList;
    public MutableLiveData<String> checkTxt = new MutableLiveData<>("开始盘点");

    public MutableLiveData<CheckListResponse> checkListResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<String>> rfidCodeListMutableLiveData = new MutableLiveData<>();
    List<String> rfidCodeList;
    private DictTypeResponse.ListBean listBean = new DictTypeResponse.ListBean();


    CheckListResponse checkListResponse;
    List<CheckListResponse.DataBean> dataBeanList;

    public MutableLiveData<DictTypeResponse> warehouseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DictTypeResponse> zoneMutableLiveData = new MutableLiveData<>();

    private RemoteSource remoteSource;

    public CheckViewModel() {
        listBean.setDictLabel("全部区域");
        listBean.setDictValue(null);
        remoteSource = new RemoteSource();
    }

    public void getWarehouse() {
        remoteSource.getByDictType("warehouse-type", new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                DictTypeResponse dictTypeResponse = (DictTypeResponse) o;
                warehouseMutableLiveData.setValue(dictTypeResponse);
            }

            @Override
            public void getFail(String message) {

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
                dictTypeResponse.getList().add(0, listBean);
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


    public void getCheckList() {
        getSuccess.setValue(false);
        remoteSource.getList(new CheckListBody(checkTypeCode.getValue(), zone.getValue(), warehouse.getValue()), new OnResultCallback.GetTCallback() {
            @Override
            public void getSuccess(Object o) {
                rfidCodeList = new ArrayList<>();
                CheckListResponse checkListResponse = (CheckListResponse) o;
                checkNum.setValue(String.valueOf(checkListResponse.getData().size()));
                if (checkListResponse.getData().size() > 0) {
                    for (int i = 0; i < checkListResponse.getData().size(); i++) {
                        rfidCodeList.add(checkListResponse.getData().get(i).getRfidCode());
                    }
                }
                rfidCodeListMutableLiveData.setValue(rfidCodeList);
                getSuccess.setValue(true);
                checkListResponseMutableLiveData.setValue(checkListResponse);
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

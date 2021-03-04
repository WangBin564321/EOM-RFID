package com.example.eom_rfid.http;


import com.example.eom_rfid.bean.body.CheckListBody;
import com.example.eom_rfid.bean.body.CheckoutBatchBody;
import com.example.eom_rfid.bean.body.CheckoutBody;
import com.example.eom_rfid.bean.body.DrugEditBody;
import com.example.eom_rfid.bean.body.DrugSearchBody;
import com.example.eom_rfid.bean.body.GetSingleInfoBody;
import com.example.eom_rfid.bean.body.LoginBody;
import com.example.eom_rfid.bean.body.LogoutBody;
import com.example.eom_rfid.bean.body.SubmitCheckBody;
import com.example.eom_rfid.bean.body.WarehouseBatchBody;
import com.example.eom_rfid.bean.body.WarehouseBody;
import com.example.eom_rfid.bean.body.WriteNumBody;
import com.example.eom_rfid.bean.response.BatchInfoResponse;
import com.example.eom_rfid.bean.response.BillListResponse;
import com.example.eom_rfid.bean.response.CheckListResponse;
import com.example.eom_rfid.bean.response.CheckoutSingleInfoResponse;
import com.example.eom_rfid.bean.response.DrugSearchResponse;
import com.example.eom_rfid.bean.response.GeneralResponse;
import com.example.eom_rfid.bean.response.LoginResponse;
import com.example.eom_rfid.bean.response.TaskResponse;
import com.example.eom_rfid.bean.response.DictTypeResponse;
import com.example.eom_rfid.bean.response.WarehouseBatchListResponse;
import com.example.eom_rfid.bean.response.WarehouseSingleResponse;
import com.example.eom_rfid.http.api.ApiWrapper;
import com.example.eom_rfid.http.callback.OnResultCallback;
import com.example.eom_rfid.http.exception.ExceptionHandle;

import io.reactivex.disposables.Disposable;

public class RemoteSource {


    public void login(LoginBody loginBody,
                      OnResultCallback.GetTCallback getTCallback) {
        ApiWrapper.getInstance().login(loginBody)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<GeneralResponse<LoginResponse>>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        getTCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeneralResponse<LoginResponse> loginResponseGeneralResponse) {
                        if (loginResponseGeneralResponse.getCode() == 0) {
                            getTCallback.getSuccess(loginResponseGeneralResponse.getData());
                        } else {
                            getTCallback.getFail(loginResponseGeneralResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    /**
     * 退出登录
     *
     * @param generalCallback
     */
    public void logout(OnResultCallback.GeneralCallback generalCallback) {
        ApiWrapper.getInstance().logout(new LogoutBody())
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<GeneralResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(GeneralResponse generalResponse) {
                        if (generalResponse.getCode() == 0) {
                            generalCallback.doSuccess(generalResponse.getMessage());
                        } else {
                            generalCallback.doFail(generalResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        generalCallback.error(responseException);
                    }
                });
    }

    public void getByDictType(String type, OnResultCallback.GetTCallback getTCallback) {
        ApiWrapper.getInstance().getByDictType(type)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<GeneralResponse<DictTypeResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeneralResponse<DictTypeResponse> warehouseResponseGeneralResponse) {
                        if (warehouseResponseGeneralResponse.getCode() == 0) {
                            getTCallback.getSuccess(warehouseResponseGeneralResponse.getData());
                        } else {
                            getTCallback.getFail(warehouseResponseGeneralResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        getTCallback.error(responseException);
                    }
                });
    }


    public void getList(CheckListBody checkListBody,
                        OnResultCallback.GetTCallback getTCallback) {
        ApiWrapper.getInstance().getList(checkListBody)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<CheckListResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        getTCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CheckListResponse checkListResponse) {
                        if (checkListResponse.getCode() == 0) {
                            getTCallback.getSuccess(checkListResponse);
                        } else {
                            getTCallback.getFail(checkListResponse.getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }


    public void submitCheck(SubmitCheckBody submitCheckBody,
                            OnResultCallback.GeneralCallback generalCallback) {
        ApiWrapper.getInstance().submitCheck(submitCheckBody)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<GeneralResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        generalCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(GeneralResponse generalResponse) {
                        if (generalResponse.getCode() == 0) {
                            generalCallback.doSuccess(generalResponse.getMessage());
                        } else {
                            generalCallback.doFail(generalResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                    }

                });
    }

    public void getSingleInfo(GetSingleInfoBody getSingleInfoBody,
                              OnResultCallback.GetTCallback getTCallback) {
        ApiWrapper.getInstance().getSingleInfo(getSingleInfoBody)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<CheckoutSingleInfoResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        getTCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CheckoutSingleInfoResponse checkoutSingleInfoResponse) {
                        if (checkoutSingleInfoResponse.getCode() == 0) {
                            getTCallback.getSuccess(checkoutSingleInfoResponse);
                        } else {
                            getTCallback.getFail(checkoutSingleInfoResponse.getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    public void checkout(CheckoutBody checkoutBody,
                         OnResultCallback.GeneralCallback generalCallback) {
        ApiWrapper.getInstance().checkout(checkoutBody)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<GeneralResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        generalCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeneralResponse generalResponse) {
                        if (generalResponse.getCode() == 0) {
                            generalCallback.doSuccess(generalResponse.getMessage());
                        } else {
                            generalCallback.doFail(generalResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    public void checkoutBatch(CheckoutBatchBody checkoutBatchBody,
                              OnResultCallback.GeneralCallback generalCallback) {
        ApiWrapper.getInstance().checkoutBatch(checkoutBatchBody)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<GeneralResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        generalCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeneralResponse generalResponse) {
                        if (generalResponse.getCode() == 0) {
                            generalCallback.doSuccess(generalResponse.getMessage());
                        } else {
                            generalCallback.doFail(generalResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    /**
     * 获取任务列表
     *
     * @param generalCallback
     */
    public void getTaskList(OnResultCallback.GetTCallback generalCallback) {
        ApiWrapper.getInstance().getTaskList(1, 999999999)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<GeneralResponse<TaskResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(GeneralResponse<TaskResponse> myTaskResponseGeneralResponse) {
                        if (myTaskResponseGeneralResponse.getCode() == 0) {
                            generalCallback.getSuccess(myTaskResponseGeneralResponse.getData());
                        } else {
                            generalCallback.getFail(myTaskResponseGeneralResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        generalCallback.error(responseException);
                    }
                });
    }


    public void getBillList(String number,
                            OnResultCallback.GetTCallback getTCallback) {
        ApiWrapper.getInstance().getBillList(number)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<BillListResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        getTCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BillListResponse billListResponse) {
                        if (billListResponse.getCode() == 0) {
                            getTCallback.getSuccess(billListResponse);
                        } else {
                            getTCallback.getFail(billListResponse.getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }


    public void getBatchInfo(String number,
                             OnResultCallback.GetTCallback getTCallback) {
        ApiWrapper.getInstance().getBatchInfo(number)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<BatchInfoResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        getTCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BatchInfoResponse batchInfoResponse) {
                        if (batchInfoResponse.getCode() == 0) {
                            getTCallback.getSuccess(batchInfoResponse);
                        } else {
                            getTCallback.getFail(batchInfoResponse.getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    public void getWarehouseBillList(String number,
                                     OnResultCallback.GetTCallback getTCallback) {
        ApiWrapper.getInstance().getWarehouseBillList(number)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<BillListResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        getTCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BillListResponse billListResponse) {
                        if (billListResponse.getCode() == 0) {
                            getTCallback.getSuccess(billListResponse);
                        } else {
                            getTCallback.getFail(billListResponse.getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }


    public void getWarehouseSingleInfo(String rfidCode,
                                       OnResultCallback.GetTCallback getTCallback) {
        ApiWrapper.getInstance().getWarehouseSingleInfo(rfidCode)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<WarehouseSingleResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        getTCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WarehouseSingleResponse warehouseSingleResponse) {
                        if (warehouseSingleResponse.getCode() == 0) {
                            getTCallback.getSuccess(warehouseSingleResponse);
                        } else {
                            getTCallback.getFail(warehouseSingleResponse.getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    public void getWarehouseBatchList(String number,
                                      OnResultCallback.GetTCallback getTCallback) {
        ApiWrapper.getInstance().getWarehouseBatchList(number)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<WarehouseBatchListResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        getTCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WarehouseBatchListResponse billListResponse) {
                        if (billListResponse.getCode() == 0) {
                            getTCallback.getSuccess(billListResponse);
                        } else {
                            getTCallback.getFail(billListResponse.getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    public void warehouse(WarehouseBody warehouseBody,
                          OnResultCallback.GeneralCallback generalCallback) {
        ApiWrapper.getInstance().warehouse(warehouseBody)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<GeneralResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        generalCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeneralResponse generalResponse) {
                        if (generalResponse.getCode() == 0) {
                            generalCallback.doSuccess(generalResponse.getMessage());
                        } else {
                            generalCallback.doFail(generalResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    public void warehouseBatch(WarehouseBatchBody warehouseBatchBody,
                               OnResultCallback.GeneralCallback generalCallback) {
        ApiWrapper.getInstance().warehouseBatch(warehouseBatchBody)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<GeneralResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        generalCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeneralResponse generalResponse) {
                        if (generalResponse.getCode() == 0) {
                            generalCallback.doSuccess(generalResponse.getMessage());
                        } else {
                            generalCallback.doFail(generalResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    public void searchDrug(DrugSearchBody drugSearchBody,
                           OnResultCallback.GetTCallback getTCallback) {
        ApiWrapper.getInstance().searchDrug(drugSearchBody)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<DrugSearchResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        getTCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DrugSearchResponse drugSearchResponse) {
                        if (drugSearchResponse.getCode() == 0) {
                            getTCallback.getSuccess(drugSearchResponse);
                        } else {
                            getTCallback.getFail(drugSearchResponse.getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    public void editDrug(DrugEditBody drugEditBody,
                         OnResultCallback.GeneralCallback generalCallback) {
        ApiWrapper.getInstance().editDrug(drugEditBody)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<GeneralResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        generalCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeneralResponse generalResponse) {
                        if (generalResponse.getCode() == 0) {
                            generalCallback.doSuccess(generalResponse.getMessage());
                        } else {
                            generalCallback.doFail(generalResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    public void writeNum(WriteNumBody writeNumBody,
                         OnResultCallback.GeneralCallback generalCallback) {
        ApiWrapper.getInstance().writeNum(writeNumBody)
                .compose(RxHelper.to_mian())
                .subscribe(new MyObserver<GeneralResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponseException responseException) {
                        generalCallback.error(responseException);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeneralResponse generalResponse) {
                        if (generalResponse.getCode() == 0) {
                            generalCallback.doSuccess(generalResponse.getMessage());
                        } else {
                            generalCallback.doFail(generalResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }


}

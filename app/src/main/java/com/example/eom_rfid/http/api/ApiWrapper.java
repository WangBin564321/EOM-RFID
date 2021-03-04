package com.example.eom_rfid.http.api;


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
import com.example.eom_rfid.http.RetrofitHttpUtils;

import io.reactivex.Observable;
import retrofit2.http.Path;


public class ApiWrapper extends RetrofitHttpUtils {
    private static volatile ApiWrapper apiWrapper;

    private ApiWrapper() {
        init();
    }

    public static ApiWrapper getInstance() {
        if (apiWrapper == null) {
            synchronized (ApiWrapper.class) {
                if (apiWrapper == null) {
                    apiWrapper = new ApiWrapper();
                }
            }
        }
        return apiWrapper;
    }

    /**************************登录*************************/

    /**
     * 登录
     *
     * @param loginBody
     * @return
     */
    public Observable<GeneralResponse<LoginResponse>> login(LoginBody loginBody) {
        return getApiServer().login(loginBody);
    }

    /**
     * 退出登录
     *
     * @return
     */
    public Observable<GeneralResponse> logout(LogoutBody logoutBody) {
        return getApiServer().logout(logoutBody);
    }


    /**************************获取业务字典*************************/

    public Observable<GeneralResponse<DictTypeResponse>> getByDictType(@Path("dictType") String dictType) {
        return getApiServer().getByDictType(dictType);
    }

    /**************************盘点*************************/

    /**
     * 获取盘点清单
     *
     * @param checkListBody
     * @return
     */
    public Observable<CheckListResponse> getList(CheckListBody checkListBody) {
        return getApiServer().getList(checkListBody);
    }

    /**
     * 提交盘点
     *
     * @param submitCheckBody
     * @return
     */
    public Observable<GeneralResponse> submitCheck(SubmitCheckBody submitCheckBody) {
        return getApiServer().submitCheck(submitCheckBody);
    }


    /**************************出库*************************/

    /**
     * 根据RFID获取出库物料信息
     *
     * @param getSingleInfoBody
     * @return
     */
    public Observable<CheckoutSingleInfoResponse> getSingleInfo(GetSingleInfoBody getSingleInfoBody) {
        return getApiServer().getSingleInfo(getSingleInfoBody);
    }

    /**
     * 单个出库
     *
     * @param checkoutBody
     * @return
     */
    public Observable<GeneralResponse> checkout(CheckoutBody checkoutBody) {
        return getApiServer().checkout(checkoutBody);
    }

    /**
     * 获取出库单号
     *
     * @param number
     * @return
     */
    public Observable<BillListResponse> getBillList(String number) {
        return getApiServer().getBillList(number);
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    public Observable<GeneralResponse<TaskResponse>> getTaskList(int page, int limit) {
        return getApiServer().getTaskList(page, limit);
    }

    /**
     * 根据出库单获取出库单信息
     *
     * @param waitOutWarehouseNumber
     * @return
     */
    public Observable<BatchInfoResponse> getBatchInfo(String waitOutWarehouseNumber) {
        return getApiServer().getBatchInfo(waitOutWarehouseNumber);
    }


    /**
     * 批量出库
     *
     * @param checkoutBatchBody
     * @return
     */
    public Observable<GeneralResponse> checkoutBatch(CheckoutBatchBody checkoutBatchBody) {
        return getApiServer().checkoutBatch(checkoutBatchBody);
    }


    /**************************入库*************************/

    /**
     * 获取入库单号
     *
     * @param number
     * @return
     */
    public Observable<BillListResponse> getWarehouseBillList(String number) {
        return getApiServer().getWarehouseBillList(number);
    }


    public Observable<WarehouseSingleResponse> getWarehouseSingleInfo(String rfidCode) {
        return getApiServer().getWarehouseSingleInfo(rfidCode);
    }

    /**
     * 根据入库单号获取入库单详情
     *
     * @param number
     * @return
     */
    public Observable<WarehouseBatchListResponse> getWarehouseBatchList(String number) {
        return getApiServer().getWarehouseBatchList(number);
    }


    /**
     * 单个入库
     *
     * @param warehouseBody
     * @return
     */
    public Observable<GeneralResponse> warehouse(WarehouseBody warehouseBody) {
        return getApiServer().warehouse(warehouseBody);
    }


    /**
     * 批量入库
     *
     * @param warehouseBatchBody
     * @return
     */
    public Observable<GeneralResponse> warehouseBatch(WarehouseBatchBody warehouseBatchBody) {
        return getApiServer().warehouseBatch(warehouseBatchBody);
    }


    /**************************商品查询*************************/

    /**
     * 商品查询
     *
     * @param drugSearchBody
     * @return
     */
    public Observable<DrugSearchResponse> searchDrug(DrugSearchBody drugSearchBody) {
        return getApiServer().searchDrug(drugSearchBody);
    }

    /**
     * 商品编辑
     *
     * @param drugEditBody
     * @return
     */
    public Observable<GeneralResponse> editDrug(DrugEditBody drugEditBody) {
        return getApiServer().editDrug(drugEditBody);
    }


    /**************************写RFID更新数据库数量*************************/

    /**
     * 写RFID更新数据库数量
     *
     * @param writeNumBody
     * @return
     */
    public Observable<GeneralResponse> writeNum(WriteNumBody writeNumBody) {
        return getApiServer().writeNum(writeNumBody);
    }


}
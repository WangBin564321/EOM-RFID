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

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServer {


    /**************************登录*************************/

    /**
     * 登录
     *
     * @param loginBody
     * @return
     */
    @POST("api/login")
    Observable<GeneralResponse<LoginResponse>> login(@Body LoginBody loginBody);

    /**
     * 退出登录
     *
     * @return
     */
    @POST("api/logout")
    Observable<GeneralResponse> logout(@Body LogoutBody logoutBody);


    /**************************获取业务字典*************************/

    /**
     * 获取业务字典
     *
     * @param dictType
     * @return
     */
    @GET("api/sys/dict/busiDictData/{dictType}")
    Observable<GeneralResponse<DictTypeResponse>> getByDictType(@Path("dictType") String dictType);


    /**************************盘点*************************/

    /**
     * 获取盘点清单
     *
     * @param checkListBody
     * @return
     */
    @POST("api/wms/checkItem/getList")
    Observable<CheckListResponse> getList(@Body CheckListBody checkListBody);

    /**
     * 提交盘点
     *
     * @param submitCheckBody
     * @return
     */
    @POST("api/wms/check/autoCheck")
    Observable<GeneralResponse> submitCheck(@Body SubmitCheckBody submitCheckBody);


    /**************************出库*************************/

    /**
     * 根据RFID获取出库物料信息
     *
     * @param getSingleInfoBody
     * @return
     */
    @POST("api/wms/outWarehouse/getOutData")
    Observable<CheckoutSingleInfoResponse> getSingleInfo(@Body GetSingleInfoBody getSingleInfoBody);

    /**
     * 单个出库
     *
     * @param checkoutBody
     * @return
     */
    @POST("api/wms/outWarehouse/outWarehouse")
    Observable<GeneralResponse> checkout(@Body CheckoutBody checkoutBody);

    /**
     * 获取出库单号
     *
     * @param number
     * @return
     */
    @GET("api/wms/waitOutWarehouse/list")
    Observable<BillListResponse> getBillList(@Query("number") String number);

    /**
     * 获取任务列表
     *
     * @return
     */
    @GET("api/pms/task/mytask/page")
    Observable<GeneralResponse<TaskResponse>> getTaskList(@Query("page") int page,
                                                          @Query("limit") int limit);

    /**
     * 根据出库单获取出库单信息
     *
     * @param waitOutWarehouseNumber
     * @return
     */
    @GET("api/wms/waitOutWarehouseItem/list")
    Observable<BatchInfoResponse> getBatchInfo(@Query("waitOutWarehouseNumber") String waitOutWarehouseNumber);

    /**
     * 批量出库
     *
     * @param checkoutBatchBody
     * @return
     */
    @POST("api/wms/outWarehouse/outWarehouse")
    Observable<GeneralResponse> checkoutBatch(@Body CheckoutBatchBody checkoutBatchBody);


    /**************************入库*************************/

    /**
     * 获取入库单号
     *
     * @param number
     * @return
     */
    @GET("api/wms/waitwarehouse/numberlist")
    Observable<BillListResponse> getWarehouseBillList(@Query("number") String number);

    @GET("api/scanner/getInData")
    Observable<WarehouseSingleResponse> getWarehouseSingleInfo(@Query("rfidCode") String rfidCode);

    /**
     * 根据入库单号获取入库单详情
     *
     * @param number
     * @return
     */
    @GET("api/wms/waitwarehouse/getItemListByNumber")
    Observable<WarehouseBatchListResponse> getWarehouseBatchList(@Query("number") String number);

    /**
     * 单个入库
     *
     * @param warehouseBody
     * @return
     */
    @POST("api/wms/inwarehouse/direct")
    Observable<GeneralResponse> warehouse(@Body WarehouseBody warehouseBody);

    /**
     * 批量入库
     *
     * @param warehouseBatchBody
     * @return
     */
    @POST("api/wms/inwarehouse/direct")
    Observable<GeneralResponse> warehouseBatch(@Body WarehouseBatchBody warehouseBatchBody);


    /**************************商品查询*************************/

    /**
     * 商品查询
     *
     * @param drugSearchBody
     * @return
     */
    @POST("api/wms/outWarehouse/getData")
    Observable<DrugSearchResponse> searchDrug(@Body DrugSearchBody drugSearchBody);

    /**
     * 商品编辑
     *
     * @param drugEditBody
     * @return
     */
    @PUT("api/scanner/update")
    Observable<GeneralResponse> editDrug(@Body DrugEditBody drugEditBody);


    /**************************写RFID更新数据库数量*************************/

    /**
     * 写RFID更新数据库数量
     *
     * @param writeNumBody
     * @return
     */
    @PUT("api/scanner/update")
    Observable<GeneralResponse> writeNum(@Body WriteNumBody writeNumBody);


}


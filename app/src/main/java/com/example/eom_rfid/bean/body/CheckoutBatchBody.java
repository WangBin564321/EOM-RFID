package com.example.eom_rfid.bean.body;

import com.example.eom_rfid.bean.response.BatchInfoResponse;
import com.example.eom_rfid.bean.response.CheckoutSingleInfoResponse;

import java.io.Serializable;
import java.util.List;

public class CheckoutBatchBody {

    private String type;
    private String taskId;
    private String destroyCause;
    private String waitOutWarehouseId;
    private String waitOutWarehouseNumber;
    private String receiver;
    private String returnDate;
    private int status;
    private String remark;

    private List<BatchInfoResponse.DataBean> wmsOutWarehouseItemDTOList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDestroyCause() {
        return destroyCause;
    }

    public void setDestroyCause(String destroyCause) {
        this.destroyCause = destroyCause;
    }

    public String getWaitOutWarehouseId() {
        return waitOutWarehouseId;
    }

    public void setWaitOutWarehouseId(String waitOutWarehouseId) {
        this.waitOutWarehouseId = waitOutWarehouseId;
    }

    public String getWaitOutWarehouseNumber() {
        return waitOutWarehouseNumber;
    }

    public void setWaitOutWarehouseNumber(String waitOutWarehouseNumber) {
        this.waitOutWarehouseNumber = waitOutWarehouseNumber;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<BatchInfoResponse.DataBean> getWmsOutWarehouseItemDTOList() {
        return wmsOutWarehouseItemDTOList;
    }

    public void setWmsOutWarehouseItemDTOList(List<BatchInfoResponse.DataBean> wmsOutWarehouseItemDTOList) {
        this.wmsOutWarehouseItemDTOList = wmsOutWarehouseItemDTOList;
    }
}

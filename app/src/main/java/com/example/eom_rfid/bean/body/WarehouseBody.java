package com.example.eom_rfid.bean.body;

import com.example.eom_rfid.bean.response.CheckoutSingleInfoResponse;
import com.example.eom_rfid.bean.response.WarehouseSingleResponse;

import java.util.List;

public class WarehouseBody {

    private String type;
    private String warehouse;
    private String zone;
    private String shelf;
    private String remark;

    private List<WarehouseSingleResponse.DataBean> list;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<WarehouseSingleResponse.DataBean> getList() {
        return list;
    }

    public void setList(List<WarehouseSingleResponse.DataBean> list) {
        this.list = list;
    }
}

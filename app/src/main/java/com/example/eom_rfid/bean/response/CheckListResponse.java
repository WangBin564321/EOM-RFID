package com.example.eom_rfid.bean.response;

import java.io.Serializable;
import java.util.List;

public class CheckListResponse implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : [{"id":"1352525337609338882","checkId":null,"warehouse":"2号仓库","zone":null,"shelfNumber":null,"boxNumber":null,"barCode":"2342443123","name":"阿莫西林","model":null,"packing":"30 粒/盒;24 盒/件","producer":"上海","batchNumber":"987654321","stockNum":30,"actualNum":null,"profitLoss":null,"remark":null,"deleted":0,"materialId":null,"rfidCode":null,"warnStockNum":200,"warnStockUnit":"盒","stockUnit":"盒"},{"id":"1356429192594059266","checkId":null,"warehouse":"2号仓库","zone":"C区域","shelfNumber":"002","boxNumber":"A009","barCode":"dr3323srr2323","name":"测试待入库清单监护仪","model":"O-221","packing":null,"producer":"上海","batchNumber":"z1","stockNum":9,"actualNum":null,"profitLoss":null,"remark":null,"deleted":0,"materialId":null,"rfidCode":"SB609262148678378","warnStockNum":180,"warnStockUnit":"台","stockUnit":"台"}]
     */

    private int code;
    private String msg;
    /**
     * id : 1352525337609338882
     * checkId : null
     * warehouse : 2号仓库
     * zone : null
     * shelfNumber : null
     * boxNumber : null
     * barCode : 2342443123
     * name : 阿莫西林
     * model : null
     * packing : 30 粒/盒;24 盒/件
     * producer : 上海
     * batchNumber : 987654321
     * stockNum : 30
     * actualNum : null
     * profitLoss : null
     * remark : null
     * deleted : 0
     * materialId : null
     * rfidCode : null
     * warnStockNum : 200
     * warnStockUnit : 盒
     * stockUnit : 盒
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private String id;
        private Object checkId;
        private String warehouse;
        private String zone;
        private String shelfNumber;
        private Object boxNumber;
        private String barCode;
        private String name;
        private Object model;
        private String packing;
        private String producer;
        private String batchNumber;
        private int stockNum;
        private int actualNum;
        private int profitLoss;
        private Object remark;
        private int deleted;
        private Object materialId;
        private String rfidCode;
        private int warnStockNum;
        private String warnStockUnit;
        private String stockUnit;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getCheckId() {
            return checkId;
        }

        public void setCheckId(Object checkId) {
            this.checkId = checkId;
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

        public String getShelfNumber() {
            return shelfNumber;
        }

        public void setShelfNumber(String shelfNumber) {
            this.shelfNumber = shelfNumber;
        }

        public Object getBoxNumber() {
            return boxNumber;
        }

        public void setBoxNumber(Object boxNumber) {
            this.boxNumber = boxNumber;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getModel() {
            return model;
        }

        public void setModel(Object model) {
            this.model = model;
        }

        public String getPacking() {
            return packing;
        }

        public void setPacking(String packing) {
            this.packing = packing;
        }

        public String getProducer() {
            return producer;
        }

        public void setProducer(String producer) {
            this.producer = producer;
        }

        public String getBatchNumber() {
            return batchNumber;
        }

        public void setBatchNumber(String batchNumber) {
            this.batchNumber = batchNumber;
        }

        public int getStockNum() {
            return stockNum;
        }

        public void setStockNum(int stockNum) {
            this.stockNum = stockNum;
        }

        public int getActualNum() {
            return actualNum;
        }

        public void setActualNum(int actualNum) {
            this.actualNum = actualNum;
        }

        public int getProfitLoss() {
            return profitLoss;
        }

        public void setProfitLoss(int profitLoss) {
            this.profitLoss = profitLoss;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public Object getMaterialId() {
            return materialId;
        }

        public void setMaterialId(Object materialId) {
            this.materialId = materialId;
        }

        public String getRfidCode() {
            return rfidCode;
        }

        public void setRfidCode(String rfidCode) {
            this.rfidCode = rfidCode;
        }

        public int getWarnStockNum() {
            return warnStockNum;
        }

        public void setWarnStockNum(int warnStockNum) {
            this.warnStockNum = warnStockNum;
        }

        public String getWarnStockUnit() {
            return warnStockUnit;
        }

        public void setWarnStockUnit(String warnStockUnit) {
            this.warnStockUnit = warnStockUnit;
        }

        public String getStockUnit() {
            return stockUnit;
        }

        public void setStockUnit(String stockUnit) {
            this.stockUnit = stockUnit;
        }
    }
}

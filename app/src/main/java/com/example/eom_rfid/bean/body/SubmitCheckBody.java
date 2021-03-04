package com.example.eom_rfid.bean.body;

import java.util.List;

public class SubmitCheckBody {


    /**
     * status : 1
     * zone : 区域
     * warehouse : 1号仓库
     * type : drug
     * wmsCheckItemDTOList : [{"id":"1352525337609338882","checkId":null,"warehouse":"2号仓库","zone":null,"shelfNumber":null,"boxNumber":null,"barCode":"2342443123","name":"阿莫西林","model":null,"packing":"30 粒/盒;24 盒/件","producer":"上海","batchNumber":"987654321","stockNum":30,"actualNum":null,"profitLoss":null,"remark":null,"deleted":0,"materialId":null,"rfidCode":null,"warnStockNum":200,"warnStockUnit":"盒","stockUnit":"盒"},{"id":"1356429192594059266","checkId":null,"warehouse":"2号仓库","zone":"C区域","shelfNumber":"002","boxNumber":"A009","barCode":"dr3323srr2323","name":"测试待入库清单监护仪","model":"O-221","packing":null,"producer":"上海","batchNumber":"z1","stockNum":9,"actualNum":null,"profitLoss":null,"remark":null,"deleted":0,"materialId":null,"rfidCode":"SB609262148678378","warnStockNum":180,"warnStockUnit":"台","stockUnit":"台"}]
     */

    private int status;
    private String zone;
    private String warehouse;
    private String type;
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

    private List<WmsCheckItemDTOListBean> wmsCheckItemDTOList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<WmsCheckItemDTOListBean> getWmsCheckItemDTOList() {
        return wmsCheckItemDTOList;
    }

    public void setWmsCheckItemDTOList(List<WmsCheckItemDTOListBean> wmsCheckItemDTOList) {
        this.wmsCheckItemDTOList = wmsCheckItemDTOList;
    }

    public static class WmsCheckItemDTOListBean {
        private String id;
        private Object checkId;
        private String warehouse;
        private Object zone;
        private Object shelfNumber;
        private Object boxNumber;
        private String barCode;
        private String name;
        private Object model;
        private String packing;
        private String producer;
        private String batchNumber;
        private int stockNum;
        private Object actualNum;
        private Object profitLoss;
        private Object remark;
        private int deleted;
        private Object materialId;
        private Object rfidCode;
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

        public Object getZone() {
            return zone;
        }

        public void setZone(Object zone) {
            this.zone = zone;
        }

        public Object getShelfNumber() {
            return shelfNumber;
        }

        public void setShelfNumber(Object shelfNumber) {
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

        public Object getActualNum() {
            return actualNum;
        }

        public void setActualNum(Object actualNum) {
            this.actualNum = actualNum;
        }

        public Object getProfitLoss() {
            return profitLoss;
        }

        public void setProfitLoss(Object profitLoss) {
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

        public Object getRfidCode() {
            return rfidCode;
        }

        public void setRfidCode(Object rfidCode) {
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

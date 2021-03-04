package com.example.eom_rfid.bean.response;

import java.io.Serializable;
import java.util.List;

public class BatchInfoResponse  {

    /**
     * code : 0
     * msg : success
     * data : [{"id":"1366280979201384450","waitOutWarehouseId":"1366280978786148354","waitOutWarehouseType":"5","warehouse":"4号仓库","zone":"区域A","shelfNumber":"2","boxNumber":null,"name":"测试待入库清单阿青霉2222222222","materialType":"drug","type":"血清","barCode":"E01054543","abbr":"ceshidairukuqingdanaqingmei","vehicleNumber":null,"annualReviewEndDate":null,"specs":"30 ml","producer":"海南","batchNumber":"A012","produceDate":"2021-02-08","expireDate":"2022-02-11","warnExpire":100,"expireSurplus":347,"outNum":76,"outUnit":"盒","stockNum":76,"stockUnit":"盒","packing":"22 粒/盒;60 盒/件","model":null,"content":null,"rfidCode":"YP335303023063277","warnStockNum":90,"warnStockUnit":"盒","instruction":"测试待入库清单阿青霉","useMode":"肌注,静脉注射","defaultUseMode":"静脉注射","defaultDosage":"无","note":"无","remark":null,"approvalNumber":"21414143","contact":null,"contactPhone":null,"maintainPeriod":null,"maintainRemind":null,"maintainSurplus":null,"materialId":"1364873926838759425","creator":"1067246875800000001","createDate":"2021-03-01 14:55:44","updater":"1067246875800000001","updateDate":"2021-03-01 14:55:44","deleted":0,"waitOutWarehouseNumber":"TB2021030120934"}]
     */

    private int code;
    private String msg;
    /**
     * id : 1366280979201384450
     * waitOutWarehouseId : 1366280978786148354
     * waitOutWarehouseType : 5
     * warehouse : 4号仓库
     * zone : 区域A
     * shelfNumber : 2
     * boxNumber : null
     * name : 测试待入库清单阿青霉2222222222
     * materialType : drug
     * type : 血清
     * barCode : E01054543
     * abbr : ceshidairukuqingdanaqingmei
     * vehicleNumber : null
     * annualReviewEndDate : null
     * specs : 30 ml
     * producer : 海南
     * batchNumber : A012
     * produceDate : 2021-02-08
     * expireDate : 2022-02-11
     * warnExpire : 100
     * expireSurplus : 347
     * outNum : 76
     * outUnit : 盒
     * stockNum : 76
     * stockUnit : 盒
     * packing : 22 粒/盒;60 盒/件
     * model : null
     * content : null
     * rfidCode : YP335303023063277
     * warnStockNum : 90
     * warnStockUnit : 盒
     * instruction : 测试待入库清单阿青霉
     * useMode : 肌注,静脉注射
     * defaultUseMode : 静脉注射
     * defaultDosage : 无
     * note : 无
     * remark : null
     * approvalNumber : 21414143
     * contact : null
     * contactPhone : null
     * maintainPeriod : null
     * maintainRemind : null
     * maintainSurplus : null
     * materialId : 1364873926838759425
     * creator : 1067246875800000001
     * createDate : 2021-03-01 14:55:44
     * updater : 1067246875800000001
     * updateDate : 2021-03-01 14:55:44
     * deleted : 0
     * waitOutWarehouseNumber : TB2021030120934
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

    public static class DataBean {
        private String id;
        private String waitOutWarehouseId;
        private String waitOutWarehouseType;
        private String warehouse;
        private String zone;
        private String shelfNumber;
        private Object boxNumber;
        private String name;
        private String materialType;
        private String type;
        private String barCode;
        private String abbr;
        private Object vehicleNumber;
        private Object annualReviewEndDate;
        private String specs;
        private String producer;
        private String batchNumber;
        private String produceDate;
        private String expireDate;
        private int warnExpire;
        private int expireSurplus;
        private int outNum;
        private String outUnit;
        private int stockNum;
        private String stockUnit;
        private String packing;
        private Object model;
        private Object content;
        private String rfidCode;
        private int warnStockNum;
        private String warnStockUnit;
        private String instruction;
        private String useMode;
        private String defaultUseMode;
        private String defaultDosage;
        private String note;
        private Object remark;
        private String approvalNumber;
        private Object contact;
        private Object contactPhone;
        private Object maintainPeriod;
        private Object maintainRemind;
        private Object maintainSurplus;
        private String materialId;
        private String creator;
        private String createDate;
        private String updater;
        private String updateDate;
        private int deleted;
        private String waitOutWarehouseNumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWaitOutWarehouseId() {
            return waitOutWarehouseId;
        }

        public void setWaitOutWarehouseId(String waitOutWarehouseId) {
            this.waitOutWarehouseId = waitOutWarehouseId;
        }

        public String getWaitOutWarehouseType() {
            return waitOutWarehouseType;
        }

        public void setWaitOutWarehouseType(String waitOutWarehouseType) {
            this.waitOutWarehouseType = waitOutWarehouseType;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMaterialType() {
            return materialType;
        }

        public void setMaterialType(String materialType) {
            this.materialType = materialType;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public String getAbbr() {
            return abbr;
        }

        public void setAbbr(String abbr) {
            this.abbr = abbr;
        }

        public Object getVehicleNumber() {
            return vehicleNumber;
        }

        public void setVehicleNumber(Object vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        public Object getAnnualReviewEndDate() {
            return annualReviewEndDate;
        }

        public void setAnnualReviewEndDate(Object annualReviewEndDate) {
            this.annualReviewEndDate = annualReviewEndDate;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
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

        public String getProduceDate() {
            return produceDate;
        }

        public void setProduceDate(String produceDate) {
            this.produceDate = produceDate;
        }

        public String getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }

        public int getWarnExpire() {
            return warnExpire;
        }

        public void setWarnExpire(int warnExpire) {
            this.warnExpire = warnExpire;
        }

        public int getExpireSurplus() {
            return expireSurplus;
        }

        public void setExpireSurplus(int expireSurplus) {
            this.expireSurplus = expireSurplus;
        }

        public int getOutNum() {
            return outNum;
        }

        public void setOutNum(int outNum) {
            this.outNum = outNum;
        }

        public String getOutUnit() {
            return outUnit;
        }

        public void setOutUnit(String outUnit) {
            this.outUnit = outUnit;
        }

        public int getStockNum() {
            return stockNum;
        }

        public void setStockNum(int stockNum) {
            this.stockNum = stockNum;
        }

        public String getStockUnit() {
            return stockUnit;
        }

        public void setStockUnit(String stockUnit) {
            this.stockUnit = stockUnit;
        }

        public String getPacking() {
            return packing;
        }

        public void setPacking(String packing) {
            this.packing = packing;
        }

        public Object getModel() {
            return model;
        }

        public void setModel(Object model) {
            this.model = model;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
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

        public String getInstruction() {
            return instruction;
        }

        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }

        public String getUseMode() {
            return useMode;
        }

        public void setUseMode(String useMode) {
            this.useMode = useMode;
        }

        public String getDefaultUseMode() {
            return defaultUseMode;
        }

        public void setDefaultUseMode(String defaultUseMode) {
            this.defaultUseMode = defaultUseMode;
        }

        public String getDefaultDosage() {
            return defaultDosage;
        }

        public void setDefaultDosage(String defaultDosage) {
            this.defaultDosage = defaultDosage;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getApprovalNumber() {
            return approvalNumber;
        }

        public void setApprovalNumber(String approvalNumber) {
            this.approvalNumber = approvalNumber;
        }

        public Object getContact() {
            return contact;
        }

        public void setContact(Object contact) {
            this.contact = contact;
        }

        public Object getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(Object contactPhone) {
            this.contactPhone = contactPhone;
        }

        public Object getMaintainPeriod() {
            return maintainPeriod;
        }

        public void setMaintainPeriod(Object maintainPeriod) {
            this.maintainPeriod = maintainPeriod;
        }

        public Object getMaintainRemind() {
            return maintainRemind;
        }

        public void setMaintainRemind(Object maintainRemind) {
            this.maintainRemind = maintainRemind;
        }

        public Object getMaintainSurplus() {
            return maintainSurplus;
        }

        public void setMaintainSurplus(Object maintainSurplus) {
            this.maintainSurplus = maintainSurplus;
        }

        public String getMaterialId() {
            return materialId;
        }

        public void setMaterialId(String materialId) {
            this.materialId = materialId;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdater() {
            return updater;
        }

        public void setUpdater(String updater) {
            this.updater = updater;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public String getWaitOutWarehouseNumber() {
            return waitOutWarehouseNumber;
        }

        public void setWaitOutWarehouseNumber(String waitOutWarehouseNumber) {
            this.waitOutWarehouseNumber = waitOutWarehouseNumber;
        }
    }
}

package com.example.eom_rfid.bean.response;

import java.util.List;

public class WarehouseBatchListResponse {


    /**
     * code : 0
     * msg : success
     * data : [{"id":"1365215483357912345","waitWarehouseId":"1367301200735207426","warehouse":"6号仓库","zone":"区域B","shelfNumber":"A002","boxNumber":null,"barCode":"1000001019","materialType":"material","name":"N97口罩","abbr":null,"specs":null,"packing":"10 个/副","producer":"海南","batchNumber":"xxxxx1","produceDate":"2021-02-25","expireDate":"2021-03-01","inNum":6,"inUnit":"副","inStatus":0,"rfidCode":"WZ719548990108049","printed":0,"type":"防护类","approvalNumber":"1010101","useMode":null,"instruction":"外用","note":null,"defaultUseMode":null,"defaultDosage":null,"warnStockNum":60,"warnStockUnit":"副","warnExpire":60,"model":null,"contact":null,"contactPhone":null,"maintainPeriod":null,"maintainRemind":null},{"id":"1365215483357966123","waitWarehouseId":"1367301200735207426","warehouse":"6号仓库","zone":"区域B","shelfNumber":"A002","boxNumber":null,"barCode":"1000001019","materialType":"material","name":"N95口罩","abbr":null,"specs":null,"packing":"10 个/副","producer":"海南","batchNumber":"xxxxx1","produceDate":"2021-02-25","expireDate":"2021-03-01","inNum":6,"inUnit":"副","inStatus":0,"rfidCode":"WZ155660042951148","printed":0,"type":"防护类","approvalNumber":"1010101","useMode":null,"instruction":"外用","note":null,"defaultUseMode":null,"defaultDosage":null,"warnStockNum":60,"warnStockUnit":"副","warnExpire":60,"model":null,"contact":null,"contactPhone":null,"maintainPeriod":null,"maintainRemind":null},{"id":"1365215483357966337","waitWarehouseId":"1367301200735207426","warehouse":"6号仓库","zone":"区域B","shelfNumber":"A002","boxNumber":null,"barCode":"1000001019","materialType":"material","name":"N90口罩","abbr":null,"specs":null,"packing":"10 个/副","producer":"海南","batchNumber":"xxxxx1","produceDate":"2021-02-25","expireDate":"2021-03-01","inNum":6,"inUnit":"副","inStatus":0,"rfidCode":"WZ659209628168176","printed":0,"type":"防护类","approvalNumber":"1010101","useMode":null,"instruction":"外用","note":null,"defaultUseMode":null,"defaultDosage":null,"warnStockNum":60,"warnStockUnit":"副","warnExpire":60,"model":null,"contact":null,"contactPhone":null,"maintainPeriod":null,"maintainRemind":null}]
     */

    private int code;
    private String msg;
    /**
     * id : 1365215483357912345
     * waitWarehouseId : 1367301200735207426
     * warehouse : 6号仓库
     * zone : 区域B
     * shelfNumber : A002
     * boxNumber : null
     * barCode : 1000001019
     * materialType : material
     * name : N97口罩
     * abbr : null
     * specs : null
     * packing : 10 个/副
     * producer : 海南
     * batchNumber : xxxxx1
     * produceDate : 2021-02-25
     * expireDate : 2021-03-01
     * inNum : 6
     * inUnit : 副
     * inStatus : 0
     * rfidCode : WZ719548990108049
     * printed : 0
     * type : 防护类
     * approvalNumber : 1010101
     * useMode : null
     * instruction : 外用
     * note : null
     * defaultUseMode : null
     * defaultDosage : null
     * warnStockNum : 60
     * warnStockUnit : 副
     * warnExpire : 60
     * model : null
     * contact : null
     * contactPhone : null
     * maintainPeriod : null
     * maintainRemind : null
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
        private String waitWarehouseId;
        private String warehouse;
        private String zone;
        private String shelfNumber;
        private Object boxNumber;
        private String barCode;
        private String materialType;
        private String name;
        private Object abbr;
        private Object specs;
        private String packing;
        private String producer;
        private String batchNumber;
        private String produceDate;
        private String expireDate;
        private int inNum;
        private String inUnit;
        private int inStatus;
        private String rfidCode;
        private int printed;
        private String type;
        private String approvalNumber;
        private Object useMode;
        private String instruction;
        private Object note;
        private Object defaultUseMode;
        private Object defaultDosage;
        private int warnStockNum;
        private String warnStockUnit;
        private int warnExpire;
        private Object model;
        private Object contact;
        private Object contactPhone;
        private Object maintainPeriod;
        private Object maintainRemind;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWaitWarehouseId() {
            return waitWarehouseId;
        }

        public void setWaitWarehouseId(String waitWarehouseId) {
            this.waitWarehouseId = waitWarehouseId;
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

        public String getMaterialType() {
            return materialType;
        }

        public void setMaterialType(String materialType) {
            this.materialType = materialType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getAbbr() {
            return abbr;
        }

        public void setAbbr(Object abbr) {
            this.abbr = abbr;
        }

        public Object getSpecs() {
            return specs;
        }

        public void setSpecs(Object specs) {
            this.specs = specs;
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

        public int getInNum() {
            return inNum;
        }

        public void setInNum(int inNum) {
            this.inNum = inNum;
        }

        public String getInUnit() {
            return inUnit;
        }

        public void setInUnit(String inUnit) {
            this.inUnit = inUnit;
        }

        public int getInStatus() {
            return inStatus;
        }

        public void setInStatus(int inStatus) {
            this.inStatus = inStatus;
        }

        public String getRfidCode() {
            return rfidCode;
        }

        public void setRfidCode(String rfidCode) {
            this.rfidCode = rfidCode;
        }

        public int getPrinted() {
            return printed;
        }

        public void setPrinted(int printed) {
            this.printed = printed;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getApprovalNumber() {
            return approvalNumber;
        }

        public void setApprovalNumber(String approvalNumber) {
            this.approvalNumber = approvalNumber;
        }

        public Object getUseMode() {
            return useMode;
        }

        public void setUseMode(Object useMode) {
            this.useMode = useMode;
        }

        public String getInstruction() {
            return instruction;
        }

        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public Object getDefaultUseMode() {
            return defaultUseMode;
        }

        public void setDefaultUseMode(Object defaultUseMode) {
            this.defaultUseMode = defaultUseMode;
        }

        public Object getDefaultDosage() {
            return defaultDosage;
        }

        public void setDefaultDosage(Object defaultDosage) {
            this.defaultDosage = defaultDosage;
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

        public int getWarnExpire() {
            return warnExpire;
        }

        public void setWarnExpire(int warnExpire) {
            this.warnExpire = warnExpire;
        }

        public Object getModel() {
            return model;
        }

        public void setModel(Object model) {
            this.model = model;
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
    }
}

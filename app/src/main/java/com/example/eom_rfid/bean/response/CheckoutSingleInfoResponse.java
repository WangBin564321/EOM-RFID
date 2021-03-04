package com.example.eom_rfid.bean.response;

public class CheckoutSingleInfoResponse {


    /**
     * code : 0
     * msg : success
     * data : {"id":"1357532911425490946","outWarehouseId":null,"type":"防护类","warehouse":"1号仓库","zone":"区域A","materialType":null,"shelfNumber":"A001","boxNumber":"A001","barCode":"93214635463","name":"测试待入库清单N95口罩","abbr":null,"expireSurplus":364,"maintainSurplus":null,"vehicleNumber":null,"annualReviewEndDate":null,"specs":null,"producer":"海南","batchNumber":"z1","produceDate":"2020-12-28","expireDate":"2022-02-19","outNum":44,"outUnit":"袋","stockNum":44,"outWarehouseType":null,"packing":"100 个/袋;50 袋/盒","model":null,"content":null,"useMode":null,"deleted":0,"materialId":"1357532911425490946","rfidCode":"WZ155660042951148","warnStockNum":200,"warnStockUnit":"袋","instruction":"测试待入库清单N95口罩","note":null,"defaultUseMode":null,"defaultDosage":null,"remark":null,"approvalNumber":"5646465456","contact":null,"contactPhone":null,"maintainPeriod":null,"maintainRemind":null,"stockUnit":"袋","warnExpire":60}
     */

    private int code;
    private String msg;
    /**
     * id : 1357532911425490946
     * outWarehouseId : null
     * type : 防护类
     * warehouse : 1号仓库
     * zone : 区域A
     * materialType : null
     * shelfNumber : A001
     * boxNumber : A001
     * barCode : 93214635463
     * name : 测试待入库清单N95口罩
     * abbr : null
     * expireSurplus : 364
     * maintainSurplus : null
     * vehicleNumber : null
     * annualReviewEndDate : null
     * specs : null
     * producer : 海南
     * batchNumber : z1
     * produceDate : 2020-12-28
     * expireDate : 2022-02-19
     * outNum : 44
     * outUnit : 袋
     * stockNum : 44
     * outWarehouseType : null
     * packing : 100 个/袋;50 袋/盒
     * model : null
     * content : null
     * useMode : null
     * deleted : 0
     * materialId : 1357532911425490946
     * rfidCode : WZ155660042951148
     * warnStockNum : 200
     * warnStockUnit : 袋
     * instruction : 测试待入库清单N95口罩
     * note : null
     * defaultUseMode : null
     * defaultDosage : null
     * remark : null
     * approvalNumber : 5646465456
     * contact : null
     * contactPhone : null
     * maintainPeriod : null
     * maintainRemind : null
     * stockUnit : 袋
     * warnExpire : 60
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean  {
        private String id;
        private Object outWarehouseId;
        private String type;
        private String warehouse;
        private String zone;
        private String materialType;
        private String shelfNumber;
        private String boxNumber;
        private String barCode;
        private String name;
        private Object abbr;
        private int expireSurplus;
        private Object maintainSurplus;
        private Object vehicleNumber;
        private Object annualReviewEndDate;
        private String specs;
        private String producer;
        private String batchNumber;
        private String produceDate;
        private String expireDate;
        private int outNum;
        private String outUnit;
        private int stockNum;
        private Object outWarehouseType;
        private String packing;
        private Object model;
        private Object content;
        private Object useMode;
        private int deleted;
        private String materialId;
        private String rfidCode;
        private int warnStockNum;
        private String warnStockUnit;
        private String instruction;
        private Object note;
        private Object defaultUseMode;
        private Object defaultDosage;
        private String remark;
        private String approvalNumber;
        private Object contact;
        private Object contactPhone;
        private Object maintainPeriod;
        private Object maintainRemind;
        private String stockUnit;
        private int warnExpire;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getOutWarehouseId() {
            return outWarehouseId;
        }

        public void setOutWarehouseId(Object outWarehouseId) {
            this.outWarehouseId = outWarehouseId;
        }

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

        public String getMaterialType() {
            return materialType;
        }

        public void setMaterialType(String materialType) {
            this.materialType = materialType;
        }

        public String getShelfNumber() {
            return shelfNumber;
        }

        public void setShelfNumber(String shelfNumber) {
            this.shelfNumber = shelfNumber;
        }

        public String getBoxNumber() {
            return boxNumber;
        }

        public void setBoxNumber(String boxNumber) {
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

        public Object getAbbr() {
            return abbr;
        }

        public void setAbbr(Object abbr) {
            this.abbr = abbr;
        }

        public int getExpireSurplus() {
            return expireSurplus;
        }

        public void setExpireSurplus(int expireSurplus) {
            this.expireSurplus = expireSurplus;
        }

        public Object getMaintainSurplus() {
            return maintainSurplus;
        }

        public void setMaintainSurplus(Object maintainSurplus) {
            this.maintainSurplus = maintainSurplus;
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

        public Object getOutWarehouseType() {
            return outWarehouseType;
        }

        public void setOutWarehouseType(Object outWarehouseType) {
            this.outWarehouseType = outWarehouseType;
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

        public Object getUseMode() {
            return useMode;
        }

        public void setUseMode(Object useMode) {
            this.useMode = useMode;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public String getMaterialId() {
            return materialId;
        }

        public void setMaterialId(String materialId) {
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
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

        public String getStockUnit() {
            return stockUnit;
        }

        public void setStockUnit(String stockUnit) {
            this.stockUnit = stockUnit;
        }

        public int getWarnExpire() {
            return warnExpire;
        }

        public void setWarnExpire(int warnExpire) {
            this.warnExpire = warnExpire;
        }
    }
}

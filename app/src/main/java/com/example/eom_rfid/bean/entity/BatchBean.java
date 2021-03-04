package com.example.eom_rfid.bean.entity;

public class BatchBean {
    private String rfidCode;
    private String name;
    private boolean isScan;

    public BatchBean(String rfidCode, String name, boolean isScan) {
        this.rfidCode = rfidCode;
        this.name = name;
        this.isScan = isScan;
    }

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isScan() {
        return isScan;
    }

    public void setScan(boolean scan) {
        isScan = scan;
    }
}

package com.example.eom_rfid.bean.entity;

public class CheckListBody {
    private String rfidCode;
    private String scanRfidCode;
    private String name;
    private String shelf;
    private String stockNum;
    private String realNum;
    private boolean isScan;

    public CheckListBody(String rfidCode, String name, String shelf, String stockNum, String realNum, boolean isScan) {
        this.rfidCode = rfidCode;
        this.name = name;
        this.shelf = shelf;
        this.stockNum = stockNum;
        this.realNum = realNum;
        this.isScan = isScan;
    }

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public String getScanRfidCode() {
        return scanRfidCode;
    }

    public void setScanRfidCode(String scanRfidCode) {
        this.scanRfidCode = scanRfidCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getRealNum() {
        return realNum;
    }

    public void setRealNum(String realNum) {
        this.realNum = realNum;
    }

    public boolean isScan() {
        return isScan;
    }

    public void setScan(boolean scan) {
        isScan = scan;
    }
}

package com.example.eom_rfid.bean.body;

public class WriteNumBody {
    private String rfidCode;
    private String id;
    private int stockNum;

    public WriteNumBody(String rfidCode, String id, int stockNum) {
        this.rfidCode = rfidCode;
        this.id = id;
        this.stockNum = stockNum;
    }
}

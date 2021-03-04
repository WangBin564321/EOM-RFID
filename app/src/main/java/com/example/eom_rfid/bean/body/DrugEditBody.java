package com.example.eom_rfid.bean.body;

public class DrugEditBody {
    private String rfidCode;
    private String id;
    private String warehouse;
    private String zone;
    private String shelfNumber;

    public DrugEditBody(String rfidCode, String id, String warehouse, String zone, String shelfNumber) {
        this.rfidCode = rfidCode;
        this.id = id;
        this.warehouse = warehouse;
        this.zone = zone;
        this.shelfNumber = shelfNumber;
    }
}

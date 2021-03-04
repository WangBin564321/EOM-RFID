package com.example.eom_rfid.bean.body;

public class CheckListBody {
    private String types;
    private String zone;
    private String warehouse;

    public CheckListBody(String type, String zone, String warehouse) {
        this.types = type;
        this.zone = zone;
        this.warehouse = warehouse;
    }
}

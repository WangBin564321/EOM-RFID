package com.example.eom_rfid.bean.body;

public class DrugSearchBody {
    private String type;
    private String nameOrRfid;

    public DrugSearchBody(String type, String nameOrRfid) {
        this.type = type;
        this.nameOrRfid = nameOrRfid;
    }
}

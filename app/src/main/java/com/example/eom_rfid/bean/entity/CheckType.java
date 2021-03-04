package com.example.eom_rfid.bean.entity;

public class CheckType {
    public CheckType(String typeName, String typeCode) {
        this.typeName = typeName;
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    private String typeName;
    private String typeCode;

    @Override
    public String toString() {
        return typeName;
    }
}

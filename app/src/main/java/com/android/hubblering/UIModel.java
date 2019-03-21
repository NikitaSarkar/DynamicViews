package com.android.hubblering;

public class UIModel {
    String field_name;
    String type;
    int mid;

    public UIModel(String field_name, String type, int mid) {
        this.field_name = field_name;
        this.type = type;
        this.mid = mid;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }
}

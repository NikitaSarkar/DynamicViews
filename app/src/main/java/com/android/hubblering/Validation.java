package com.android.hubblering;

public class Validation {
    boolean isRequired=false;
    int min,max;
    String field_name;

    public Validation(String mfieldname, boolean isRequired, int min, int max) {
        this.isRequired = isRequired;
        this.min = min;
        this.max = max;
        this.field_name=mfieldname;
        }

    public Validation(boolean isRequired) {
        this.isRequired = isRequired;

    }


    public Validation(String mfieldname, boolean isRequired) {
        this.isRequired = isRequired;
        this.field_name=mfieldname;

    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}

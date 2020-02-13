package com.example.cookingapp.models;

public class MetricByIdModel {

    String unit;
    Double value;

    MetricByIdModel(){}

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

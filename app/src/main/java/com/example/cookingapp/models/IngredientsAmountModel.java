package com.example.cookingapp.models;

public class IngredientsAmountModel {

    MetricByIdModel metric;
    UsByIdModel us;

    IngredientsAmountModel(){}

    public MetricByIdModel getMetric() {
        return metric;
    }

    public void setMetric(MetricByIdModel metric) {
        this.metric = metric;
    }

    public UsByIdModel getUs() {
        return us;
    }

    public void setUs(UsByIdModel us) {
        this.us = us;
    }
}

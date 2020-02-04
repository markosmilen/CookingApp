package com.example.cookingapp.models;

public class MeasuresModel {
    MetricModel metric;
    UsModel us;

    public MeasuresModel(){}

    public MetricModel getMetric() {
        return metric;
    }

    public void setMetric(MetricModel metric) {
        this.metric = metric;
    }

    public UsModel getUs() {
        return us;
    }

    public void setUs(UsModel us) {
        this.us = us;
    }
}

package com.example.cookingapp.models;

import java.util.ArrayList;

public class ExtendedIngredientsModel {

    String aisle, consitency, image, name, original, originalName, unit;
    double amount;
    int id;
    MeasuresModel measures;

    public ExtendedIngredientsModel(){}

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getConsitency() {
        return consitency;
    }

    public void setConsitency(String consitency) {
        this.consitency = consitency;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MeasuresModel getMeasures() {
        return measures;
    }

    public void setMeasures(MeasuresModel measures) {
        this.measures = measures;
    }
}

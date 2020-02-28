package com.example.cookingapp.models;

import com.orm.SugarRecord;

public class ShoppingListModel extends SugarRecord<ShoppingListModel> {

    int id;
    double amount;
    String value;
    String title;

    public ShoppingListModel(){}

    public ShoppingListModel (int id, double amount, String value, String title){
        this.id = id;
        this.amount = amount;
        this.value = value;
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

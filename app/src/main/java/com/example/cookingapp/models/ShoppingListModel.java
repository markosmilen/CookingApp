package com.example.cookingapp.models;

import com.orm.SugarRecord;

public class ShoppingListModel extends SugarRecord<ShoppingListModel> {

    int recipeId;
    double amount;
    String value;
    String ingredientTitle;

    public ShoppingListModel(){}

    public ShoppingListModel (int id, double amount, String value, String title){
        this.recipeId = id;
        this.amount = amount;
        this.value = value;
        this.ingredientTitle = title;
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
        return ingredientTitle;
    }

    public void setTitle(String title) {
        this.ingredientTitle = title;
    }
}

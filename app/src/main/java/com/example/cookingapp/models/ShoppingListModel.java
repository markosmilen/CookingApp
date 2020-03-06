package com.example.cookingapp.models;

import com.orm.SugarRecord;

public class ShoppingListModel extends SugarRecord<ShoppingListModel> {

    double amount;
    String value;
    String ingredientTitle;
    boolean isBought;

    ShoppingRecipe recipe;

    public ShoppingListModel(){}

    public ShoppingListModel (double amount, String value, String title, ShoppingRecipe recipe, boolean isBought){
        this.amount = amount;
        this.value = value;
        this.ingredientTitle = title;
        this.recipe = recipe;
        this.isBought = isBought;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
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

package com.example.cookingapp.models;

public class IngredientsAndValueModel {

    String img;
    String name;
    IngredientsAmountModel amount;

    IngredientsAndValueModel(){}

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientsAmountModel getAmount() {
        return amount;
    }

    public void setAmount(IngredientsAmountModel amount) {
        this.amount = amount;
    }
}

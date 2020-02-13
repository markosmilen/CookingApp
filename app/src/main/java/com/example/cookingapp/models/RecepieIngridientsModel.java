package com.example.cookingapp.models;

import java.util.ArrayList;

public class RecepieIngridientsModel {

    ArrayList<IngredientsAndValueModel> ingredients;

    RecepieIngridientsModel(){}

    public ArrayList<IngredientsAndValueModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientsAndValueModel> ingredients) {
        this.ingredients = ingredients;
    }
}

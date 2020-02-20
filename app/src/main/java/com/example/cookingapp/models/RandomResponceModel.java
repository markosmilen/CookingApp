package com.example.cookingapp.models;

import java.util.ArrayList;

public class RandomResponceModel {

    ArrayList<RecipeInformationModel> recipes;

    RandomResponceModel (){}

    public ArrayList<RecipeInformationModel> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<RecipeInformationModel> recipes) {
        this.recipes = recipes;
    }
}

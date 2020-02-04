package com.example.cookingapp.models;

import java.util.ArrayList;

public class RandomResponceModel {

    ArrayList<DietMealsModel> results;
    ArrayList<RandomrRecipesModel> recipes;

    RandomResponceModel (){}

    public ArrayList<RandomrRecipesModel> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<RandomrRecipesModel> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<DietMealsModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<DietMealsModel> results) {
        this.results = results;
    }
}

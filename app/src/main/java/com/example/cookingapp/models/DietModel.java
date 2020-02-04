package com.example.cookingapp.models;

import java.util.ArrayList;

public class DietModel {

    ArrayList<DietMealsModel> results;

    DietModel(){}

    public ArrayList<DietMealsModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<DietMealsModel> results) {
        this.results = results;
    }
}

package com.example.cookingapp.models;

import java.util.ArrayList;

public class AnalyzedInstructionsModel {

    String name;
    ArrayList<StepsModel> steps;

    AnalyzedInstructionsModel(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<StepsModel> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepsModel> steps) {
        this.steps = steps;
    }
}

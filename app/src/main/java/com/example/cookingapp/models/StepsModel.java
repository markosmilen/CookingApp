package com.example.cookingapp.models;

import java.util.ArrayList;

public class StepsModel {

    int number;
    String step;
    ArrayList<IngredientsModel> ingredients;
    ArrayList<EquipmentModel> equipment;

    StepsModel(){}

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public ArrayList<IngredientsModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientsModel> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<EquipmentModel> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<EquipmentModel> equipment) {
        this.equipment = equipment;
    }
}

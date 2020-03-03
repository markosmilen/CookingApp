package com.example.cookingapp.interfaces;

import com.example.cookingapp.models.IngredientsAndValueModel;

import java.util.ArrayList;

public interface IngredientsListener {

    void passIngredients(ArrayList<IngredientsAndValueModel> ingredients);

}

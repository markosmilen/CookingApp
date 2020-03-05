package com.example.cookingapp.models;

import com.orm.SugarRecord;

public class ShoppingRecipe extends SugarRecord<ShoppingRecipe> {

    String recipeName;
    String recipeImg;
    int recepiID;

    public int getRecepiID() {
        return recepiID;
    }

    public void setRecepiID(int recepiID) {
        this.recepiID = recepiID;
    }

    public ShoppingRecipe(){}

    public ShoppingRecipe(String recipeName, String recipeImg, int id){
        this.recipeName = recipeName;
        this.recipeImg = recipeImg;
        this.recepiID = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeImg() {
        return recipeImg;
    }

    public void setRecipeImg(String recipeImg) {
        this.recipeImg = recipeImg;
    }


}

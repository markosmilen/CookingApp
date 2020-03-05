package com.example.cookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cookingapp.R;
import com.example.cookingapp.adapters.ShoppingRecipesAdapter;
import com.example.cookingapp.models.ShoppingRecipe;

import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<ShoppingRecipe> recipes = ShoppingRecipe.listAll(ShoppingRecipe.class);
    ShoppingRecipesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.shoppingRecipes_toolbar);
        toolbar.setTitle("Selected Recipes for shopping");
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_primary));
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.shoppingListRecipes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingRecipesAdapter(this, recipes);
        recyclerView.setAdapter(adapter);
    }
}

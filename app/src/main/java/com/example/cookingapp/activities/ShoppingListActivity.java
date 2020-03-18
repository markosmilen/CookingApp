package com.example.cookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.cookingapp.R;
import com.example.cookingapp.adapters.ShoppingRecipesAdapter;
import com.example.cookingapp.interfaces.MealListener;
import com.example.cookingapp.interfaces.SetVisibilityListener;
import com.example.cookingapp.models.ShoppingRecipe;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity implements MealListener, SetVisibilityListener {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<ShoppingRecipe> recipes = new ArrayList<>();
    ShoppingRecipesAdapter adapter;
    RelativeLayout noItemsLayout, layoutWithItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        noItemsLayout = (RelativeLayout) findViewById(R.id.no_shoppingList_items_layout);
        layoutWithItems = (RelativeLayout) findViewById(R.id.shoppinglist_with_items_layout);
        setLayoutsVisibility();
        new addShopingListItemsAsyncTask().execute();

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.shoppingRecipes_toolbar);
        toolbar.setTitle("Selected Recipes for shopping");
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_primary));
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.shoppingListRecipes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingRecipesAdapter(this, recipes, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getMealInfo(int id) {
        Intent detailsIntent = new Intent(this, DetailsActivity.class);
        detailsIntent.putExtra("ID", id);
        startActivity(detailsIntent);
    }

    private void setLayoutsVisibility() {
        if (recipes.isEmpty()){
            noItemsLayout.setVisibility(View.VISIBLE);
            layoutWithItems.setVisibility(View.INVISIBLE);
        } else {
            noItemsLayout.setVisibility(View.INVISIBLE);
            layoutWithItems.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setVisibility() {
        new addShopingListItemsAsyncTask().execute();
    }

    public class addShopingListItemsAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            recipes = ShoppingRecipe.listAll(ShoppingRecipe.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setLayoutsVisibility();
        }
    }
}

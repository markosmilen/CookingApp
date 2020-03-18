package com.example.cookingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cookingapp.R;
import com.example.cookingapp.adapters.ShoppingRecipesAdapter;
import com.example.cookingapp.fragments.TopLvlFeaturedFragment;
import com.example.cookingapp.interfaces.MealListener;
import com.example.cookingapp.interfaces.SetVisibilityListener;
import com.example.cookingapp.models.IngredientsModel;
import com.example.cookingapp.models.ShoppingListModel;
import com.example.cookingapp.models.ShoppingRecipe;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity implements MealListener, SetVisibilityListener {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<ShoppingRecipe> recipes = new ArrayList<>();
    ShoppingRecipesAdapter adapter;
    RelativeLayout noItemsLayout, layoutWithItems;
    ImageButton add;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopingnav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        ShoppingRecipe.deleteAll(ShoppingRecipe.class);
        ShoppingListModel.deleteAll(ShoppingListModel.class);
        adapter.notifyDataSetChanged();
        setVisibility();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        noItemsLayout = (RelativeLayout) findViewById(R.id.no_shoppingList_items_layout);
        layoutWithItems = (RelativeLayout) findViewById(R.id.shoppinglist_with_items_layout);
        add = (ImageButton) findViewById(R.id.add_button);
        final TopLvlFeaturedFragment fragment = new TopLvlFeaturedFragment();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingListActivity.this, BrowoseActivity.class);
                startActivity(intent);
            }
        });

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

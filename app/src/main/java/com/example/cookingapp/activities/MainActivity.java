package com.example.cookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cookingapp.R;
import com.example.cookingapp.adapters.DietsAdapter;
import com.example.cookingapp.interfaces.DietListener;
import com.google.android.material.appbar.AppBarLayout;

public class MainActivity extends AppCompatActivity implements DietListener {

    RecyclerView recyclerViewTop, recyclerViewMid, recyclerViewBot;
    DietsAdapter adapterTop, adapterMid, adapterBot;
    Button enterKitchen;
    int top[] = {0,1,2};
    int mid[] = {3,4,5,6};
    int bot[] = {7,8,9};
    AppBarLayout appbar;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        enterKitchen = (Button) findViewById(R.id.enter_kitchen);

        appbar = (AppBarLayout) findViewById(R.id.appbar);
        appbar.setExpanded(true,false);

        recyclerViewTop = (RecyclerView) findViewById(R.id.recycler_view_diets_top);
        recyclerViewMid= (RecyclerView) findViewById(R.id.recycler_view_diets_mid);
        recyclerViewBot = (RecyclerView) findViewById(R.id.recycler_view_diets_bottom);

        recyclerViewTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerViewMid.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerViewBot.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        adapterTop = new DietsAdapter(this, top, this);
        recyclerViewTop.setAdapter(adapterTop);

        adapterMid = new DietsAdapter(this, mid, this);
        recyclerViewMid.setAdapter(adapterMid);
        recyclerViewMid.scrollToPosition(2);

        adapterBot = new DietsAdapter(this, bot, this);
        recyclerViewBot.setAdapter(adapterBot);
    }

    @Override
    public void onDietClicked(String text) {

        if(text.equals("Gluten Free")){
            text = "GlutenFree";
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DIET", text);
        editor.commit();

        Intent browoseIntent = new Intent(MainActivity.this, BrowoseActivity.class);
        startActivity(browoseIntent);
        finish();
    }

    public void onEnterKitchenClicked(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DIET", "all");
        editor.commit();

        Intent browoseIntent = new Intent(MainActivity.this, BrowoseActivity.class);
        startActivity(browoseIntent);
    }
}

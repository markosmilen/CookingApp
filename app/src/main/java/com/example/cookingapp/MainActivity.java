package com.example.cookingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingapp.adapters.DietsAdapter;
import com.example.cookingapp.interfaces.DietListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DietListener {

    RecyclerView recyclerViewTop, recyclerViewMid, recyclerViewBot;
    DietsAdapter adapterTop, adapterMid, adapterBot;
    int top[] = {0,1,2};
    int mid[] = {3,4,5,6};
    int bot[] = {7,8,9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}

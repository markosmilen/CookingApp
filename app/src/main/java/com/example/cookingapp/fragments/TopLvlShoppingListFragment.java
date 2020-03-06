package com.example.cookingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cookingapp.R;
import com.example.cookingapp.activities.ShoppingListActivity;
import com.example.cookingapp.adapters.ShoppingAdapter;
import com.example.cookingapp.models.ShoppingListModel;
import com.example.cookingapp.models.ShoppingRecipe;

import java.util.ArrayList;
import java.util.List;


public class TopLvlShoppingListFragment extends Fragment {

    List<ShoppingListModel> cartList;
    List<ShoppingRecipe> shoppingRecipes;
    RecyclerView recyclerView;
    ShoppingAdapter adapter;
    TextView listSize;

    public TopLvlShoppingListFragment() {
        // Required empty public constructor
    }


    public static TopLvlShoppingListFragment newInstance(String param1, String param2) {
        TopLvlShoppingListFragment fragment = new TopLvlShoppingListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_lvl_shopping_list, container, false);

        listSize = (TextView) view.findViewById(R.id.list_size);
        cartList = ShoppingListModel.listAll(ShoppingListModel.class);
        updateView();
        listSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShoppingListActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_shopping);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ShoppingAdapter(getActivity(), cartList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        cartList.clear();
        cartList.addAll(ShoppingListModel.listAll(ShoppingListModel.class));
        updateView();
        adapter.notifyDataSetChanged();
    }

    public void updateView(){
        shoppingRecipes = ShoppingRecipe.listAll(ShoppingRecipe.class);
        int numberOfRecipes = shoppingRecipes.size();
        listSize.setText(numberOfRecipes + " " + "Recipes");
    }
}

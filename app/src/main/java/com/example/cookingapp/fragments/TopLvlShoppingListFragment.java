package com.example.cookingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cookingapp.R;
import com.example.cookingapp.adapters.ShoppingAdapter;
import com.example.cookingapp.models.ShoppingListModel;

import java.util.ArrayList;
import java.util.List;


public class TopLvlShoppingListFragment extends Fragment {

    List<ShoppingListModel> cartList;
    RecyclerView recyclerView;
    ShoppingAdapter adapter;
    Button delete;

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

        delete = (Button) view.findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingListModel.deleteAll(ShoppingListModel.class);
            }
        });
        cartList = ShoppingListModel.listAll(ShoppingListModel.class);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_shopping);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ShoppingAdapter(getActivity(), cartList);
        recyclerView.setAdapter(adapter);
        return view;

    }

}

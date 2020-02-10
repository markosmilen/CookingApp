package com.example.cookingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingapp.R;
import com.example.cookingapp.adapters.IngridientsAdapter;
import com.example.cookingapp.models.IngredientsModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;


public class IngridientsFragment extends Fragment {

    private static final String ARG_PARAM1 = "ID";

    ArrayList<IngredientsModel> ingridiens = new ArrayList<>();
    RecyclerView recycler;
    IngridientsAdapter adapter;
    private String id;
    ViewPager pager;


    public IngridientsFragment() {
        // Required empty public constructor
    }

    public static IngridientsFragment newInstance(String id) {
        IngridientsFragment fragment = new IngridientsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
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
        View view = inflater.inflate(R.layout.fragment_bottom_lvl_ingridients, container, false);

        pager = (ViewPager) view.findViewById(R.id.viewPagerIngridients);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view_ingridients);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        getIngridients(id);
        adapter = new IngridientsAdapter(getContext(), ingridiens);
        recycler.setAdapter(adapter);
        return view;
    }

    private void getIngridients(String id) {
       // HttpUrl url = new HttpUrl.Builder().scheme("https").host("www.spoonacular.com").addPathSegment("food").addPathSegment("ingredients").addPathSegment(id).addPathSegment("information").build();

        IngredientsModel model1 = new IngredientsModel();
        model1.setName("KROMID");
        ingridiens.add(model1);

        IngredientsModel model2 = new IngredientsModel();
        model2.setName("PRAZ");
        ingridiens.add(model2);

        IngredientsModel model3 = new IngredientsModel();
        model3.setName("LUK");
        ingridiens.add(model3);

    }

}

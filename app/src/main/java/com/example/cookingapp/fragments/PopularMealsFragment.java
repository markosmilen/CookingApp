package com.example.cookingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingapp.R;
import com.example.cookingapp.adapters.PopularMealsAdapter;
import com.example.cookingapp.models.DietMealsModel;
import com.example.cookingapp.models.RandomResponceModel;
import com.example.cookingapp.models.RandomrRecipesModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PopularMealsFragment extends Fragment {

    public static final String TAG = PopularMealsFragment.class.getSimpleName();

    Gson gson;
    ArrayList<RandomrRecipesModel> popularMeals;
    RecyclerView recyclerView;
    PopularMealsAdapter popularAdapter;

    public PopularMealsFragment() {
        // Required empty public constructor
    }


    public static PopularMealsFragment newInstance() {
        PopularMealsFragment fragment = new PopularMealsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular_meals, container, false);
        gson = new Gson();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_popular_meals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        generatePopularMeals();

        return view;
    }

    public void generatePopularMeals() {
        HttpUrl.Builder builder = HttpUrl.parse("https://api.spoonacular.com/recipes/random").newBuilder();
        builder.addQueryParameter("number", "20");
        builder.addQueryParameter("tags", "veryPopular,dinner");
        builder.addQueryParameter("apiKey", "538bac8dcbdc467c9c1683802b57809b");
        String url = builder.build().toString();

        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("ERROR2", e + "");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    RandomResponceModel model = gson.fromJson(jsonString, RandomResponceModel.class);
                    popularMeals = model.getRecipes();
                    if (getActivity() != null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                popularAdapter = new PopularMealsAdapter(getContext(), popularMeals);
                                recyclerView.setAdapter(popularAdapter);
                                popularAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                }
            }
        });
    }
}

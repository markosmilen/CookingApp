package com.example.cookingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.activities.DetailsActivity;
import com.example.cookingapp.adapters.MealsAdapter;
import com.example.cookingapp.adapters.RandomMealsAdapter;
import com.example.cookingapp.interfaces.MealListener;
import com.example.cookingapp.models.DietMealsModel;
import com.example.cookingapp.models.DietModel;
import com.example.cookingapp.models.RandomResponceModel;
import com.example.cookingapp.models.RecipeInformationModel;
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

public class BottomLvlDietsFragment extends Fragment implements MealListener {

    public static final String TAG = BottomLvlDietsFragment.class.getSimpleName();

    Gson gson;
    ArrayList<DietMealsModel> meals = new ArrayList<>();
    ArrayList<RecipeInformationModel> randomMeals = new ArrayList<>();
    RecyclerView recyclerView;
    MealsAdapter mealsAdapter;
    RandomMealsAdapter randomMealsAdapter;
    String diet;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    boolean isLoading = false;
    int offset = 0;

    public BottomLvlDietsFragment(){
    }
    public static BottomLvlDietsFragment newInstance(){
        BottomLvlDietsFragment mainFragment = new BottomLvlDietsFragment();
        Bundle bundle = new Bundle();
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_lvl_diets, container, false);
        sharedPreferences = getContext().getSharedPreferences("MY_SHARED_PREF", Context.MODE_PRIVATE);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_meals);

        diet = sharedPreferences.getString("DIET", null);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_selectedDiet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (diet.equals("all")){
            generateRandomMealsList();
            randomMealsAdapter = new RandomMealsAdapter(randomMeals, getContext(), BottomLvlDietsFragment.this);
            recyclerView.setAdapter(randomMealsAdapter);
        } else {
            generateDietMeals(diet, offset);
            mealsAdapter = new MealsAdapter(getContext(), meals, BottomLvlDietsFragment.this);
            recyclerView.setAdapter(mealsAdapter);
        }

        initScrollListener();

        gson = new Gson();
        return view;
    }

    private void generateRandomMealsList() {
        isLoading = true;
        HttpUrl.Builder builder = HttpUrl.parse("https://api.spoonacular.com/recipes/random").newBuilder();
        builder.addQueryParameter("number", "20");

        builder.addQueryParameter("apiKey", "538bac8dcbdc467c9c1683802b57809b");
        String url = builder.build().toString();

        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("ERROR_DAFAQ", e + "");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    RandomResponceModel model = gson.fromJson(jsonString, RandomResponceModel.class);
                    ArrayList<RecipeInformationModel> result = model.getRecipes();
                    for (int i = 0; i < result.size(); i++){
                        randomMeals.add(result.get(i));
                    }

                    if (getActivity() != null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int scrollposition = randomMeals.size();
                                randomMealsAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(scrollposition);
                                isLoading = false;
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
        });
    }

    public void generateDietMeals(String diet, int offset){

        isLoading = true;
        HttpUrl.Builder builder = HttpUrl.parse("https://api.spoonacular.com/recipes/search").newBuilder();
        builder.addQueryParameter("diet", diet);
        builder.addQueryParameter("number", "20");
        if (offset != 0){
            builder.addQueryParameter("offset", offset +"");
        }
        builder.addQueryParameter("apiKey", "538bac8dcbdc467c9c1683802b57809b");
        String url = builder.build().toString();

        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("ERROR", e + "");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    DietModel model = gson.fromJson(jsonString, DietModel.class);
                    ArrayList<DietMealsModel> result = model.getResults();
                    for (int i = 0; i < result.size(); i++){
                        meals.add(result.get(i));
                    }

                    Log.d("MEALS", meals.size() + "");
                    if (getActivity() != null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mealsAdapter.notifyDataSetChanged();
                                isLoading = false;
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
        });
    }

    public void initScrollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                final LinearLayoutManager linearLayoutManager =(LinearLayoutManager) recyclerView.getLayoutManager();

                if (diet.equals("all")){
                    if(!isLoading){
                        if(linearLayoutManager != null && linearLayoutManager.findFirstCompletelyVisibleItemPosition() == randomMeals.size()-1){
                            randomMeals.add(null);
                            mealsAdapter.notifyItemInserted(randomMeals.size()-1);
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    randomMeals.remove(randomMeals.size()-1);
                                    int scrollPosition = randomMeals.size();
                                    mealsAdapter.notifyItemRemoved(scrollPosition);
                                  //  recyclerView.scrollToPosition(scrollPosition);
                                    generateRandomMealsList();
                                    isLoading = true;
                                }
                            });
                        }
                    }
                } else {
                    if(!isLoading){
                        if(linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == meals.size()-1){
                            offset = offset + 20;
                            meals.add(null);
                            mealsAdapter.notifyItemInserted(meals.size()-1);
                            recyclerView.scrollToPosition(meals.size()-1);

                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    meals.remove(meals.size()-1);
                                    int scrollPosition = meals.size();
                                    mealsAdapter.notifyItemRemoved(scrollPosition);
                                    generateDietMeals(diet, offset);
                                    isLoading = true;
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    @Override
    public void getMealInfo(int id) {
        Intent detailsIntent = new Intent(getContext(), DetailsActivity.class);
        detailsIntent.putExtra("ID", id);
        startActivity(detailsIntent);
    }
}

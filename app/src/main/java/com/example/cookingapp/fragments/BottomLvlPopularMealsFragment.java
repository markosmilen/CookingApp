package com.example.cookingapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingapp.R;
import com.example.cookingapp.activities.DetailsActivity;
import com.example.cookingapp.adapters.PopularMealsAdapter;
import com.example.cookingapp.interfaces.MealListener;
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

public class BottomLvlPopularMealsFragment extends Fragment implements MealListener {

    public static final String TAG = BottomLvlPopularMealsFragment.class.getSimpleName();

    Gson gson;
    ArrayList<RecipeInformationModel> popularMeals = new ArrayList<>();
    RecyclerView recyclerView;
    PopularMealsAdapter popularAdapter;
    boolean isLoading = false;
    int offset = 0;

    public BottomLvlPopularMealsFragment() {
        // Required empty public constructor
    }

    public static BottomLvlPopularMealsFragment newInstance() {
        BottomLvlPopularMealsFragment fragment = new BottomLvlPopularMealsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_lvl_popular_meals, container, false);
        gson = new Gson();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_popular_meals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        popularAdapter = new PopularMealsAdapter(getContext(), popularMeals, this);
        recyclerView.setAdapter(popularAdapter);
        generatePopularMeals(offset);
        initScrollListener();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    public void generatePopularMeals(int offset) {
        isLoading = true;
        HttpUrl.Builder builder = HttpUrl.parse("https://api.spoonacular.com/recipes/random").newBuilder();
        builder.addQueryParameter("number", "20");
        builder.addQueryParameter("tags", "veryPopular,dinner");

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
                Log.d("ERROR2", e + "");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    RandomResponceModel model = gson.fromJson(jsonString, RandomResponceModel.class);
                    ArrayList<RecipeInformationModel> result = model.getRecipes();
                    for (int i = 0; i < result.size(); i++){
                        popularMeals.add(result.get(i));
                    }

                    if (getActivity() != null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int scrollposition = popularMeals.size();
                                popularAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(scrollposition);
                                isLoading = false;
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
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager =(LinearLayoutManager) recyclerView.getLayoutManager();

                if(!isLoading){
                    if(linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == popularMeals.size()-1){
                        popularMeals.add(null);
                        popularAdapter.notifyItemInserted(popularMeals.size()-1);
                        recyclerView.scrollToPosition(popularMeals.size()-1);
                        offset = offset + 20;
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                popularMeals.remove(popularMeals.size()-1);
                                int scrollPosition = popularMeals.size();
                                popularAdapter.notifyItemRemoved(scrollPosition);
                                generatePopularMeals(offset);
                                isLoading = true;
                            }
                        });
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

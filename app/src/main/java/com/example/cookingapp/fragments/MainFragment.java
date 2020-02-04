package com.example.cookingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.adapters.MealsAdapter;
import com.example.cookingapp.models.DietMealsModel;
import com.example.cookingapp.models.DietModel;
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

public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    Gson gson;
    ArrayList<DietMealsModel> meals = new ArrayList<>();
    RecyclerView recyclerView;
    MealsAdapter mealsAdapter;
    String diet;
    SharedPreferences sharedPreferences;

    public MainFragment(){
    }

    public static MainFragment newInstance(String query){
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        sharedPreferences = getContext().getSharedPreferences("MY_SHARED_PREF", Context.MODE_PRIVATE);
        diet = sharedPreferences.getString("DIET", null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_selectedDiet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        generateRandomMeals(diet);

        gson = new Gson();
        return view;
    }

    public void generateRandomMeals(String diet){
        HttpUrl.Builder builder = HttpUrl.parse("https://api.spoonacular.com/recipes/search").newBuilder();
        builder.addQueryParameter("diet", diet);
        builder.addQueryParameter("number", "20");
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
                    meals = model.getResults();
                    Log.d("MEALS", meals.size() + "");
                    if (getActivity() != null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mealsAdapter = new MealsAdapter(getContext(), meals);
                                recyclerView.setAdapter(mealsAdapter);
                                mealsAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                }

            }
        });
    }

}

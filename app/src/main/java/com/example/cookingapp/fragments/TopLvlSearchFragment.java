package com.example.cookingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.load.model.stream.HttpUriLoader;
import com.example.cookingapp.R;
import com.example.cookingapp.adapters.SearchAdapter;
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

public class TopLvlSearchFragment extends Fragment {

    LinearLayout searchFields, results_layout, pasta, vegetarian, asian, desserts, quick, chicken, fish, noGluten, salads ;
    EditText searchBar;
    String querySelected, queryString;
    boolean isLoading, isSeachEmpty = false;
    RecyclerView recyclerView;
    ArrayList<DietMealsModel> mealsList = new ArrayList<>();
    Gson gson;
    SearchAdapter searchAdapter;
    int offset = 0;
    ProgressBar progressBar;


    public TopLvlSearchFragment() {
        // Required empty public constructor
    }

    public static TopLvlSearchFragment newInstance() {
        TopLvlSearchFragment fragment = new TopLvlSearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_top_lvl_search, container, false);
        searchFields = (LinearLayout) view.findViewById(R.id.search_fields_layout);
        pasta = (LinearLayout) view.findViewById(R.id.search_pasta);
        vegetarian = (LinearLayout) view.findViewById(R.id.search_vegetarian);
        asian = (LinearLayout) view.findViewById(R.id.search_asian);
        desserts = (LinearLayout) view.findViewById(R.id.search_desserts);
        quick = (LinearLayout) view.findViewById(R.id.search_quick);
        chicken = (LinearLayout) view.findViewById(R.id.search_chicken);
        fish = (LinearLayout) view.findViewById(R.id.search_fish);
        noGluten = (LinearLayout) view.findViewById(R.id.search_no_gluten);
        salads = (LinearLayout) view.findViewById(R.id.search_salads);
        results_layout = (LinearLayout) view.findViewById(R.id.results_layout);
        searchBar = (EditText) view.findViewById(R.id.search_edit_text);
        progressBar = (ProgressBar) view.findViewById(R.id.search_progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_results);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        gson = new Gson();


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                queryString = s.toString();
                if (s.toString().length() > 3) {

                    searchFields.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);

                    loadSearchedMeals(queryString, 0);
                    searchAdapter = new SearchAdapter(mealsList, getContext());
                    recyclerView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();


                    //            loadMeals(queryString);
                } else {
                    mealsList = new ArrayList<>();
                    searchFields.setVisibility(View.VISIBLE);
                    results_layout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        return view;
    }

    public void loadSearchedMeals(String query, int offset){
        mealsList.clear();
        isLoading = true;
        HttpUrl.Builder builder = HttpUrl.parse("https://api.spoonacular.com/recipes/search").newBuilder();
        builder.addQueryParameter("query", query);
        builder.addQueryParameter("number", "30");
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

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonstring = response.body().string();
                    DietModel model = gson.fromJson(jsonstring, DietModel.class);
                    ArrayList<DietMealsModel> result = model.getResults();
                    for (int i = 0; i < result.size(); i++){
                        mealsList.add(result.get(i));
                    }

                    if (getActivity() != null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                searchAdapter.notifyDataSetChanged();
                                isLoading = false;
                                progressBar.setVisibility(View.GONE);
                                results_layout.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            }
        });

    }

    public void onSectionClicked(View view){
        switch (view.getId()){
            case R.id.search_pasta:
                querySelected = "pasta";
                break;
            case R.id.search_vegetarian:
                querySelected = "vegetarian";
                break;
            case R.id.search_asian:
                querySelected = "asian";
                break;
            case R.id.search_desserts:
                querySelected = "desserts";
                break;
            case R.id.search_quick:
                querySelected = "quick";
                break;
            case R.id.search_chicken:
                querySelected = "chicken";
                break;
            case R.id.search_fish:
                querySelected = "fish";
                break;
            case R.id.search_no_gluten:
                querySelected = "nogluten";
                break;
            case R.id.search_salads:
                querySelected = "salads";
                break;

            }



        }


}

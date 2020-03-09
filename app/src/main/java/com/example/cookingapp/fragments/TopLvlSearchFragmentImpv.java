package com.example.cookingapp.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class TopLvlSearchFragmentImpv extends Fragment implements View.OnClickListener {

    LinearLayout searchFields, results_layout, mediterranean, vegetarian, asian, desserts, quick, mexican, indian, noGluten, salads ;
    EditText searchBar;
    String queryString, queryMediterranean, queryVegetarian, queryAsian, queryMexican, queryIndian, queryQuick, queryDessert, queryNoGluten, querySalad;
    boolean isLoading = false;
    RecyclerView recyclerView;
    ArrayList<DietMealsModel> mealsList = new ArrayList<>();
    Gson gson;
    SearchAdapter searchAdapter;
    int offset = 0;
    ProgressBar progressBar;
    boolean isType, isCuisine, isDiet, ismaxReadyTime, isMediterranean, isMexican, isIndian, isVegetarian, isAsian, isDessert, isQuick, isNoGluten, isSalad = false;
    ArrayList<String> cuisineList = new ArrayList<>(), typeList = new ArrayList<>(), timeList = new ArrayList<>();
    ArrayList<String> dietList= new ArrayList<>();

    public TopLvlSearchFragmentImpv(){
    }

    public static TopLvlSearchFragmentImpv getInstance(){
        TopLvlSearchFragmentImpv fragment = new TopLvlSearchFragmentImpv();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_lvl_search_imp, container, false);

        initView(view);

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
        if(dietList != null){
            dietList.clear();
        }
        if(cuisineList != null){
            cuisineList.clear();
        }
        if(typeList != null){
            typeList.clear();
        }
        if(timeList != null){
            timeList.clear();
        }
        isLoading = true;
        prepareQueries();

        HttpUrl.Builder builder = HttpUrl.parse("https://api.spoonacular.com/recipes/complexSearch?").newBuilder();
        builder.addQueryParameter("query", query);
        builder.addQueryParameter("number", "30");
        if (offset != 0){
            builder.addQueryParameter("offset", offset +"");
        }
        if (!cuisineList.isEmpty()){
            String searchCuisine = populateList(cuisineList);
            builder.addQueryParameter("cuisine", searchCuisine);
        }
        if (!typeList.isEmpty()){
            String searchType = populateList(typeList);
            builder.addQueryParameter("type", searchType);
        }
        if (!timeList.isEmpty()){
            String searchQuick = populateList(timeList);
            builder.addQueryParameter("maxReadyTime", searchQuick);
        }
        if (!dietList.isEmpty()){
            String searchDiet = populateList(dietList);
            builder.addQueryParameter("diet", searchDiet);
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
                                searchAdapter = new SearchAdapter(mealsList, getContext());
                                recyclerView.setAdapter(searchAdapter);
                                searchAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.INVISIBLE);
                                results_layout.setVisibility(View.VISIBLE);
                                isLoading = false;
                            }
                        });
                    }
                }
            }
        });
    }

    public void initView(View view){
        searchFields = (LinearLayout) view.findViewById(R.id.search_fields_layout);
        mediterranean = (LinearLayout) view.findViewById(R.id.search_mediterranian);
        mediterranean.setOnClickListener(this);
        vegetarian = (LinearLayout) view.findViewById(R.id.search_vegetarian);
        vegetarian.setOnClickListener(this);
        asian = (LinearLayout) view.findViewById(R.id.search_asian);
        asian.setOnClickListener(this);
        desserts = (LinearLayout) view.findViewById(R.id.search_desserts);
        desserts.setOnClickListener(this);
        quick = (LinearLayout) view.findViewById(R.id.search_quick);
        quick.setOnClickListener(this);
        mexican = (LinearLayout) view.findViewById(R.id.search_mexican);
        mexican.setOnClickListener(this);
        indian = (LinearLayout) view.findViewById(R.id.search_indian);
        indian.setOnClickListener(this);
        noGluten = (LinearLayout) view.findViewById(R.id.search_no_gluten);
        noGluten.setOnClickListener(this);
        salads = (LinearLayout) view.findViewById(R.id.search_salads);
        salads.setOnClickListener(this);
        results_layout = (LinearLayout) view.findViewById(R.id.results_layout);
        searchBar = (EditText) view.findViewById(R.id.search_edit_text);
        progressBar = (ProgressBar) view.findViewById(R.id.search_progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_results);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_mediterranian:
                if (isMediterranean == false){
                    isMediterranean = true;
                    mediterranean.setBackground(getActivity().getDrawable(R.drawable.search_button_curved_selected));
                    queryMediterranean = "mediterranean";
                } else {
                    isMediterranean = false;
                    queryMediterranean = null;
                    mediterranean.setBackground(getActivity().getDrawable(R.drawable.search_button_curved));
                }
                break;
            case R.id.search_vegetarian:
                if (isVegetarian == false){
                    isVegetarian = true;
                    vegetarian.setBackground(getActivity().getDrawable(R.drawable.search_button_curved_selected));
                    queryVegetarian = "vegetarian";
                } else {
                    isVegetarian = false;
                    queryVegetarian = null;
                    vegetarian.setBackground(getActivity().getDrawable(R.drawable.search_button_curved));
                }
                break;
            case R.id.search_asian:
                if (isAsian == false){
                    isAsian = true;
                    queryAsian = "asian";
                    asian.setBackground(getActivity().getDrawable(R.drawable.search_button_curved_selected));
                } else {
                    isAsian = false;
                    queryAsian = null;
                    asian.setBackground(getActivity().getDrawable(R.drawable.search_button_curved));
                }
                break;
            case R.id.search_desserts:
                if (isDessert == false){
                    isDessert = true;
                    queryDessert = "dessert";
                    desserts.setBackground(getActivity().getDrawable(R.drawable.search_button_curved_selected));
                } else {
                    isDessert = false;
                    queryDessert = null;
                    desserts.setBackground(getActivity().getDrawable(R.drawable.search_button_curved));
                }
                break;
            case R.id.search_quick:
                if (isQuick == false){
                    isQuick = true;
                    queryQuick = "15";
                    quick.setBackground(getActivity().getDrawable(R.drawable.search_button_curved_selected));
                } else {
                    isQuick = false;
                    queryQuick = null;
                    quick.setBackground(getActivity().getDrawable(R.drawable.search_button_curved));
                }
                break;
            case R.id.search_mexican:
                if (isMexican == false){
                    isMexican = true;
                    mexican.setBackground(getActivity().getDrawable(R.drawable.search_button_curved_selected));
                    queryMexican = "mexican";
                } else {
                    isMexican = false;
                    queryMexican = null;
                    mexican.setBackground(getActivity().getDrawable(R.drawable.search_button_curved));
                }
                break;
            case R.id.search_indian:
                if (isIndian == false){
                    isIndian = true;
                    indian.setBackground(getActivity().getDrawable(R.drawable.search_button_curved_selected));
                    queryIndian = "indian";
                } else {
                    isIndian = false;
                    queryIndian = null;
                    indian.setBackground(getActivity().getDrawable(R.drawable.search_button_curved));
                }
                break;
            case R.id.search_no_gluten:
                if (isNoGluten == false){
                    isNoGluten = true;
                    queryNoGluten = "nogluten";
                    noGluten.setBackground(getActivity().getDrawable(R.drawable.search_button_curved_selected));
                } else {
                    isNoGluten = false;
                    queryNoGluten = null;
                    noGluten.setBackground(getActivity().getDrawable(R.drawable.search_button_curved));
                }
                break;
            case R.id.search_salads:
                if (isSalad == false){
                    isSalad = true;
                    querySalad = "salad";
                    salads.setBackground(getActivity().getDrawable(R.drawable.search_button_curved_selected));
                } else {
                    isSalad = false;
                    querySalad = null;
                    salads.setBackground(getActivity().getDrawable(R.drawable.search_button_curved));
                }
                break;
        }
    }

    public void prepareQueries(){
        if (queryAsian != null || queryIndian != null || queryMexican != null || queryMediterranean != null){
            isCuisine = true;
        }
        if (queryVegetarian != null || queryNoGluten != null){
            isDiet = true;
        }
        if (queryDessert != null || querySalad != null){
            isType = true;
        }
        if (queryQuick != null){
            ismaxReadyTime = true;
        }
        if (isMediterranean == true) {
            cuisineList.add(queryMediterranean);
        }
        if (isVegetarian == true){
            dietList.add(queryVegetarian);
        }
        if (isAsian == true){
            cuisineList.add(queryAsian);
        }
        if (isDessert == true){
            typeList.add(queryDessert);
        }
        if (isQuick == true){
            timeList.add(queryQuick);
        }
        if (isMexican == true){
            cuisineList.add(queryMexican);
        }
        if (isIndian == true){
            cuisineList.add(queryIndian);
        }
        if (isNoGluten == true){
            dietList.add(queryNoGluten);
        }
        if (isSalad == true){
            typeList.add(querySalad);
        }
    }

    public String populateList(ArrayList<String> list){
        String query = "";
        for (int i=0; i<list.size(); i++){
            if (query.equals("")) {
                query = query + list.get(i);
            } else {
                query = query + "," + list.get(i);
            }
        }
        return query;
    }
}

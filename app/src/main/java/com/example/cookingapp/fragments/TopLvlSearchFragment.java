package com.example.cookingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.cookingapp.R;
import com.example.cookingapp.models.DietMealsModel;

import java.util.ArrayList;

public class TopLvlSearchFragment extends Fragment {

    LinearLayout searchFields, pasta, vegetarian, asian, desserts, quick, chicken, fish, noGluten, salads ;
    EditText searchBar;
    String querySelected, queryString;
    boolean isLoading, isSeachEmpty = false;
    RecyclerView recyclerView;
    ArrayList<DietMealsModel> mealsList = new ArrayList<>();



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
        searchBar = (EditText) view.findViewById(R.id.search_edit_text);

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
                    //            loadMeals(queryString);
                } else {
                    mealsList = new ArrayList<>();
                    //       searchAdapter = new SearchAdapter(mealsList, TopLvlSearchFragment.this);

                }
            }
        });

        return view;
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

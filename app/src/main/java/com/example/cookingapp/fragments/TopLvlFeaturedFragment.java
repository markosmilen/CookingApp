package com.example.cookingapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingapp.R;
import com.example.cookingapp.activities.BrowoseActivity;
import com.example.cookingapp.adapters.MealListPagerAdapter;
import com.example.cookingapp.adapters.MealsAdapter;
import com.example.cookingapp.models.DietMealsModel;
import com.example.cookingapp.models.DietModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
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


public class TopLvlFeaturedFragment extends Fragment {

    String diet;
    SharedPreferences sharedPreferences;
    TabLayout tabLayout;
    ViewPager pager;
    AppBarLayout appBarLayout;

    public TopLvlFeaturedFragment() {
        // Required empty public constructor
    }


    public static TopLvlFeaturedFragment newInstance(String param1, String param2) {
        TopLvlFeaturedFragment fragment = new TopLvlFeaturedFragment();
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
        View view = inflater.inflate(R.layout.fragment_top_lvl_featured, container, false);
        sharedPreferences = getContext().getSharedPreferences("MY_SHARED_PREF", Context.MODE_PRIVATE);
        diet = sharedPreferences.getString("DIET", null);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Featured"));
        tabLayout.addTab(tabLayout.newTab().setText("Popular"));
        tabLayout.addTab(tabLayout.newTab().setText("Desserts"));

        final MealListPagerAdapter pagerAdapter = new MealListPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        pager = (ViewPager) view.findViewById(R.id.viewPager);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        pager.setOffscreenPageLimit(3);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
              //  pager.setCurrentItem(tab.getPosition());
            }
        });

        //making appBar change color from transparent to white when collapsed
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appBarL);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            int scorllRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scorllRange == -1){
                    scorllRange = appBarLayout.getTotalScrollRange();
                }

                if (scorllRange + verticalOffset  == 0){
                    tabLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                    tabLayout.setTabTextColors(getResources().getColor(R.color.color_primary), getResources().getColor(R.color.color_primary));
                }else{
                    tabLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
                    tabLayout.setTabTextColors(getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.colorWhite));
                }
            }
        });

        return view;
    }




 /*   public void generateRandomMeals(String diet){
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

  */

}

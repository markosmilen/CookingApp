package com.example.cookingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.cookingapp.R;
import com.example.cookingapp.adapters.MealListPagerAdapter;
import com.example.cookingapp.adapters.MealsAdapter;
import com.example.cookingapp.fragments.MainFragment;
import com.example.cookingapp.models.DietMealsModel;
import com.example.cookingapp.models.DietModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class BrowoseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    ViewPager pager;
    AppBarLayout appBarLayout;
    String dietSelected;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browose);

        dietSelected = getIntent().getStringExtra("DIET");
        Log.d("DIET_PASSED", "" + dietSelected);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Featured"));
        tabLayout.addTab(tabLayout.newTab().setText("Popular"));
        tabLayout.addTab(tabLayout.newTab().setText("New"));
        pager = (ViewPager) findViewById(R.id.viewPager);

        final MealListPagerAdapter pagerAdapter = new MealListPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
                //pager.setCurrentItem(tab.getPosition());
            }
        });

        //making appBar change color from transparent to white when collapsed
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarL);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            int scorllRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scorllRange == -1){
                    scorllRange = appBarLayout.getTotalScrollRange();
                }

                if (scorllRange + verticalOffset  == 0){
                    tabLayout.setBackgroundColor(ContextCompat.getColor(BrowoseActivity.this, R.color.colorWhite));
                    tabLayout.setTabTextColors(getResources().getColor(R.color.color_primary), getResources().getColor(R.color.color_primary));
                }else{
                    tabLayout.setBackgroundColor(ContextCompat.getColor(BrowoseActivity.this, R.color.colorTransparent));
                    tabLayout.setTabTextColors(getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.colorWhite));
                }
            }
        });

        //setting bottom navigation
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.featured_nav);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.featured_nav:

                break;
            case R.id.search_nav:

                break;
            case R.id.saved_nav:

                break;
            case R.id.cookbook_nav:

                break;
        }
        return false;
    }
}

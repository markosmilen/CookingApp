package com.example.cookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookingapp.R;
import com.example.cookingapp.adapters.MealDeatilsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class DetailsActivity extends AppCompatActivity {

    ImageView mealImg;
    TextView name, servings, time;
    MealDeatilsPagerAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mealImg = (ImageView) findViewById(R.id.meal_details_img);
        name = (TextView) findViewById(R.id.meal_details_title);
        servings = (TextView) findViewById(R.id.servings);
        time = (TextView) findViewById(R.id.prep_time);
        viewPager = (ViewPager) findViewById(R.id.viewPagerIngridients);

        id = getIntent().getIntExtra("ID", 0);
        tabLayout = (TabLayout) findViewById(R.id.meal_detail_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Ingridients"));
        tabLayout.addTab(tabLayout.newTab().setText("Ingridients"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MealDeatilsPagerAdapter pagerAdapter = new MealDeatilsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}

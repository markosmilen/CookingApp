package com.example.cookingapp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cookingapp.fragments.MainFragment;
import com.example.cookingapp.fragments.PopularMealsFragment;

public class MealListPagerAdapter extends FragmentPagerAdapter {

    int tabsNum;

    public MealListPagerAdapter(FragmentManager fm, int tabsNum) {
        super(fm);
        this.tabsNum = tabsNum;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MainFragment();
            case 1:
                return new PopularMealsFragment();
            case 2:
                return new MainFragment();
            default: return new MainFragment();
        }
    }

    @Override
    public int getCount() {
        return tabsNum;
    }

}

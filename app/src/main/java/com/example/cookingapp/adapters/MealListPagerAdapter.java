package com.example.cookingapp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cookingapp.fragments.BottomLvlDietsFragment;
import com.example.cookingapp.fragments.BottomLvlListsFragment;
import com.example.cookingapp.fragments.BottomLvlPopularMealsFragment;
import com.example.cookingapp.fragments.BottomLvlVideosFragment;

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
                return new BottomLvlDietsFragment();
            case 1:
                return new BottomLvlPopularMealsFragment();
            case 2:
                return new BottomLvlVideosFragment();
            default: return new BottomLvlDietsFragment();
        }
    }

    @Override
    public int getCount() {
        return tabsNum;
    }

}

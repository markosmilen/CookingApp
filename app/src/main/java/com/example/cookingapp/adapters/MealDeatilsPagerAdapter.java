package com.example.cookingapp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cookingapp.fragments.IngridientsFragment;
import com.example.cookingapp.fragments.InstuctionsFragment;

public class MealDeatilsPagerAdapter extends FragmentPagerAdapter {

    int numTubs;

    public MealDeatilsPagerAdapter(FragmentManager fm, int numTubs) {
        super(fm);
        this.numTubs = numTubs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new IngridientsFragment();
            case 1:
                return new InstuctionsFragment();
                default: return new IngridientsFragment();
        }

    }

    @Override
    public int getCount() {
        return numTubs;
    }
}

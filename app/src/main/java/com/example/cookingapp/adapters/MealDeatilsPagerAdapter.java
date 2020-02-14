package com.example.cookingapp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cookingapp.fragments.EquipmentFragment;
import com.example.cookingapp.fragments.IngridientsFragment;
import com.example.cookingapp.fragments.InstuctionsFragment;

public class MealDeatilsPagerAdapter extends FragmentPagerAdapter {

    int numTubs;
    String id;

    public MealDeatilsPagerAdapter(FragmentManager fm, int numTubs, String id) {
        super(fm);
        this.numTubs = numTubs;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return IngridientsFragment.newInstance(id);
        } else {
            return EquipmentFragment.newInstance(id);
        }
    }

    @Override
    public int getCount() {
        return numTubs;
    }
}

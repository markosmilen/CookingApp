package com.example.cookingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.cookingapp.R;
import com.example.cookingapp.fragments.TopLvlBookmarkFragment;
import com.example.cookingapp.fragments.TopLvlFeaturedFragment;
import com.example.cookingapp.fragments.TopLvlSearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BrowoseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browose);

        //setting bottom navigation
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.featured_nav);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.featured_nav:
                showTopLevelFragment(new TopLvlFeaturedFragment());
                break;
            case R.id.search_nav:
                showTopLevelFragment(new TopLvlSearchFragment());
                break;
            case R.id.saved_nav:
                showTopLevelFragment(new TopLvlBookmarkFragment());
                break;
            case R.id.cookbook_nav:

                break;
        }
        return false;
    }

    private void showTopLevelFragment(Fragment fragment) {
        // Use the fragment manager to dynamically change the fragment displayed in the FrameLayout.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.top_lvl_frame, fragment)
                .addToBackStack(null)
                .commit();
    }
}

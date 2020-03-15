package com.example.cookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.cookingapp.R;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String diet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        diet = sharedPreferences.getString("DIET", null);
        if (diet.equals("all") || diet.isEmpty()){
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(mainIntent);
        } else {
            Intent searchINtent = new Intent(SplashActivity.this, BrowoseActivity.class);
            startActivity(searchINtent);
        }
        finish();
    }
}

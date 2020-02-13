package com.example.cookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cookingapp.R;
import com.example.cookingapp.adapters.MealDeatilsPagerAdapter;
import com.example.cookingapp.fragments.IngridientsFragment;
import com.example.cookingapp.models.RandomrRecipesModel;
import com.example.cookingapp.models.SummerizeRecipeModel;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.sufficientlysecure.htmltextview.HtmlAssetsImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity {

    ImageView mealImg;
    TextView name, servings, time;
    HtmlTextView summary;
    MealDeatilsPagerAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    int id;
    Gson gson;
    SummerizeRecipeModel model;
    RandomrRecipesModel recepiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mealImg = (ImageView) findViewById(R.id.meal_details_img);
        name = (TextView) findViewById(R.id.meal_details_title);
        summary = (HtmlTextView) findViewById(R.id.recipe_summary);
        servings = (TextView) findViewById(R.id.servings);
        time = (TextView) findViewById(R.id.prep_time);
        viewPager = (ViewPager) findViewById(R.id.viewPagerIngredients);
        gson = new Gson();

        id = getIntent().getIntExtra("ID", 0);
        tabLayout = (TabLayout) findViewById(R.id.meal_detail_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Instructions"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        getRecipeSummary(id);
        getRecipeInformation(id);

        final MealDeatilsPagerAdapter pagerAdapter = new MealDeatilsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), id+"");
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Toast.makeText(DetailsActivity.this, "THIS IS THE FIRST CLICK", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void getRecipeSummary(int id) {

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.spoonacular.com")
                .addPathSegment("recipes")
                .addPathSegment(id+"")
                .addPathSegment("summary")
                .addQueryParameter("apiKey", "538bac8dcbdc467c9c1683802b57809b").build();


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonString = response.body().string();
                    model = gson.fromJson(jsonString, SummerizeRecipeModel.class);
                    DetailsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            name.setText(model.getTitle());
                            summary.setHtml(model.getSummary(), new HtmlAssetsImageGetter(summary));
                        }
                    });
                }
            }
        });

    }

    private void getRecipeInformation(int id){
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.spoonacular.com")
                .addPathSegment("recipes")
                .addPathSegment(id+"")
                .addPathSegment("information")
                .addQueryParameter("apiKey", "538bac8dcbdc467c9c1683802b57809b").build();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonString = response.body().string();
                    recepiInfo = gson.fromJson(jsonString, RandomrRecipesModel.class);
                    DetailsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            servings.setText(recepiInfo.getServings()+" " + "ss");
                            time.setText(recepiInfo.getReadyInMinutes() + " " + "min");
                            String img = recepiInfo.getImage();
                            Glide.with(DetailsActivity.this).load(img).placeholder(R.drawable.placeholder).into(mealImg);
                        }
                    });
                }
            }
        });
    }
}

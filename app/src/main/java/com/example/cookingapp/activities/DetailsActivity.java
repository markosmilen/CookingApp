package com.example.cookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cookingapp.R;
import com.example.cookingapp.adapters.MealDeatilsPagerAdapter;
import com.example.cookingapp.models.BookmarkedModel;
import com.example.cookingapp.models.RecipeInformationModel;
import com.example.cookingapp.models.SummerizeRecipeModel;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.sufficientlysecure.htmltextview.HtmlAssetsImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    RecipeInformationModel recepiInfo;
    Boolean isBookmarked = false;
    String mealName, imgUrl;
    ArrayList<BookmarkedModel> bookmarks = new ArrayList<>();
    Button bookmarked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mealImg = (ImageView) findViewById(R.id.meal_details_img);
        name = (TextView) findViewById(R.id.meal_details_title);
        summary = (HtmlTextView) findViewById(R.id.recipe_summary);
        servings = (TextView) findViewById(R.id.servings);
        time = (TextView) findViewById(R.id.prep_time);
        bookmarked = (Button) findViewById(R.id.bookmark_button);
        viewPager = (ViewPager) findViewById(R.id.viewPagerIngredients);
        gson = new Gson();

        id = getIntent().getIntExtra("ID", 0);
        tabLayout = (TabLayout) findViewById(R.id.meal_detail_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Instructions"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       // getRecipeSummary(id);
        getRecipeInformation(id);
        isBookmarked = isMealBookmarked(id);

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

    /* private void getRecipeSummary(int id) {

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
                            mealName = model.getTitle();
                            name.setText(mealName);
                            //summary.setHtml(model.getSummary(), new HtmlAssetsImageGetter(summary));
                        }
                    });
                }
            }
        });
    }
*/

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
                    recepiInfo = gson.fromJson(jsonString, RecipeInformationModel.class);
                    DetailsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            servings.setText(recepiInfo.getServings()+" " + "ss");
                            time.setText(recepiInfo.getReadyInMinutes() + " " + "min");
                            imgUrl = recepiInfo.getImage();
                            summary.setHtml(recepiInfo.getInstructions(), new HtmlAssetsImageGetter(summary));
                            Glide.with(DetailsActivity.this).load(imgUrl).placeholder(R.drawable.placeholder).into(mealImg);
                        }
                    });
                }
            }
        });
    }

    public void onBookmarkClicked(View view) {
        if(!isBookmarked){
            isBookmarked = true;
            BookmarkedModel bookmark = new BookmarkedModel(id, imgUrl, mealName);
            Log.d("GOT_ID", bookmark.getIdentificationNum() + "");
            bookmark.save();
            bookmarked.setBackgroundResource(R.drawable.ic_bookmarked);
            Toast.makeText(this, "NOW ITS BOOKMARKED", Toast.LENGTH_SHORT).show();

        } else {
            isBookmarked = false;
            BookmarkedModel.deleteAll(BookmarkedModel.class);
            bookmarked.setBackgroundResource(R.drawable.ic_not_bookmarked);
            Toast.makeText(this, "NOW IT IS NOT", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isMealBookmarked(int id){

        List <BookmarkedModel> bookmarkedMeals = BookmarkedModel.listAll(BookmarkedModel.class);
        boolean check = false;

        if (bookmarkedMeals != null){
            for (int i = 0; i<bookmarkedMeals.size(); i++) {
                BookmarkedModel favmeal = bookmarkedMeals.get(i);
                int identNum = favmeal.getIdentificationNum();
                if (identNum == id) {
                    check = true;
                    bookmarked.setBackgroundResource(R.drawable.ic_bookmarked);
                }
            }
        }
        return check;
    }
}

package com.example.cookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cookingapp.R;
import com.example.cookingapp.adapters.MealDeatilsPagerAdapter;
import com.example.cookingapp.fragments.IngredientsFragment;
import com.example.cookingapp.interfaces.IngredientsListener;
import com.example.cookingapp.models.BookmarkedModel;
import com.example.cookingapp.models.CookedModel;
import com.example.cookingapp.models.IngredientsAndValueModel;
import com.example.cookingapp.models.NutritionModel;
import com.example.cookingapp.models.RecipeInformationModel;
import com.example.cookingapp.models.ShoppingListModel;
import com.example.cookingapp.models.ShoppingRecipe;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.orm.query.Condition;
import com.orm.query.Select;

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

public class DetailsActivity extends AppCompatActivity implements IngredientsListener {

    ImageView mealImg;
    TextView name, servings, time, ratingText, cooked, uncoocked, calories, protein, carbs, fat, shoppingList;
    HtmlTextView summary;
    NutritionModel model;
    MealDeatilsPagerAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    int mealID;
    Gson gson;
    RecipeInformationModel recepiInfo;
    Boolean isBookmarked, isCooked = false, isLoaded = false;
    String mealName, imgUrl;
    ProgressBar progressBar, shopping_progress;
    LinearLayout linearLayout;
    RatingBar ratingBar;
    Button bookmarked;
    List <BookmarkedModel> bookmarkedMeals;
    List<CookedModel> cookedMeals;
    ArrayList<IngredientsAndValueModel> ingredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
        loadRating();

        gson = new Gson();

        mealID = getIntent().getIntExtra("ID", 0);
        tabLayout = (TabLayout) findViewById(R.id.meal_detail_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Equipment"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        getRecipeInformation(mealID);
        getRecipeNutrition(mealID);
        isBookmarked = isMealBookmarked(mealID);
        isCooked = isMealCooked(mealID);

        final MealDeatilsPagerAdapter pagerAdapter = new MealDeatilsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), mealID+"");
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
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
                            mealName = recepiInfo.getTitle();
                            name.setText(mealName);
                            int starsInt = (int)recepiInfo.getSpoonacularScore()/20;
                            ratingBar.setRating(starsInt);
                            ratingText.setText("(app score -" + " " + (int)recepiInfo.getSpoonacularScore() + ")");

                            if (recepiInfo.getInstructions() != null){
                                summary.setHtml(recepiInfo.getInstructions(), new HtmlAssetsImageGetter(summary));
                            }
                            Glide.with(getApplicationContext()).load(imgUrl).placeholder(R.drawable.placeholder).into(mealImg);
                            progressBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            isLoaded = true;
                        }
                    });
                }
            }
        });
    }

    public void onBookmarkClicked(View view) {
        bookmarked.setEnabled(false);
        if(!isBookmarked){
            isBookmarked = true;
            BookmarkedModel bookmark = new BookmarkedModel(mealID, imgUrl, mealName);
            bookmark.save();
            bookmarked.setBackgroundResource(R.drawable.ic_bookmarked);
            Toast.makeText(this, "Bookmarked!", Toast.LENGTH_SHORT).show();

        } else {
            isBookmarked = false;
            if(bookmarkedMeals != null){
                for (int i=0; i<bookmarkedMeals.size(); i++){
                    BookmarkedModel deleteModel = bookmarkedMeals.get(i);
                    int identNum = deleteModel.getIdentificationNum();
                    if (identNum == mealID){
                        deleteModel.delete();
                    }
                }
            }
            bookmarked.setBackgroundResource(R.drawable.ic_not_bookmarked);
            Toast.makeText(this, "Removed from bookmarks!", Toast.LENGTH_SHORT).show();
        }
        bookmarkedMeals = BookmarkedModel.listAll(BookmarkedModel.class);
        bookmarked.setEnabled(true);
    }

    public boolean isMealBookmarked(int id){

        bookmarkedMeals = BookmarkedModel.listAll(BookmarkedModel.class);
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

    public boolean isMealCooked (int id){
        cookedMeals = CookedModel.listAll(CookedModel.class);
        boolean checkCooked = false;

        if (cookedMeals != null){
            for (int i=0; i<cookedMeals.size(); i++){
                CookedModel cookMeal = cookedMeals.get(i);

                if(cookMeal.getIdentificationNum() == id){
                    checkCooked = true;
                    cooked.setVisibility(View.INVISIBLE);
                    uncoocked.setVisibility(View.VISIBLE);
                }
            }
        }
        return checkCooked;
    }

    public void loadRating(){
        ratingBar.setMax(5);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(0.1f);
    }

    public void getRecipeNutrition(int id){
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.spoonacular.com")
                .addPathSegment("recipes")
                .addPathSegment(id +"")
                .addPathSegment("nutritionWidget.json")
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
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    model = gson.fromJson(jsonString, NutritionModel.class);
                    String caloriesText = model.getCalories() +"";
                    Log.d("CALORIES", caloriesText);
                    DetailsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            calories.setText(model.getCalories());
                            carbs.setText(model.getCarbs());
                            fat.setText(model.getFat());
                            protein.setText(model.getProtein());
                        }
                    });
                }
            }
        });
    }

    public void onAddToShoppingCartClicked (View view){
        shoppingList.setVisibility(View.INVISIBLE);
        shopping_progress.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                shoppingList.setVisibility(View.VISIBLE);
                shopping_progress.setVisibility(View.INVISIBLE);
                Toast.makeText(DetailsActivity.this, "Ingrеdients added to shopping cart", Toast.LENGTH_SHORT).show();
                ShoppingRecipe shoppingRecipe = new ShoppingRecipe(mealName, imgUrl, mealID);
                shoppingRecipe.save();

                for (int i = 0; i<ingredients.size(); i++){
                    IngredientsAndValueModel model = ingredients.get(i);
                    double ingredientValue = model.getAmount().getMetric().getValue();
                    String ingredientUnit = model.getAmount().getMetric().getUnit();
                    String ingredientName = model.getName();
                    ShoppingListModel cart = new ShoppingListModel(ingredientValue,ingredientUnit, ingredientName, shoppingRecipe, false);
                    cart.save();
                }
            }
        };
        handler.postDelayed(r,1500);
    }

    private void initView(){
        mealImg = (ImageView) findViewById(R.id.meal_details_img);
        name = (TextView) findViewById(R.id.meal_details_title);
        ratingText = (TextView) findViewById(R.id.rating_text);
        summary = (HtmlTextView) findViewById(R.id.recipe_summary);
        servings = (TextView) findViewById(R.id.servings);
        time = (TextView) findViewById(R.id.prep_time);
        cooked = (TextView) findViewById(R.id.cooked_meal_button);
        uncoocked = (TextView) findViewById(R.id.undo_cooked_meal);
        calories = (TextView) findViewById(R.id.calories);
        carbs = (TextView) findViewById(R.id.carbs);
        protein = (TextView) findViewById(R.id.protein);
        fat = (TextView) findViewById(R.id.fat);
        shoppingList = (TextView) findViewById(R.id.add_shopping_list);
        bookmarked = (Button) findViewById(R.id.bookmark_button);
        viewPager = (ViewPager) findViewById(R.id.viewPagerIngredients);
        progressBar = (ProgressBar) findViewById(R.id.progress_details);
        shopping_progress = (ProgressBar) findViewById(R.id.shopping_progress);
        linearLayout = (LinearLayout) findViewById(R.id.details_layout);
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);

        cooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoaded){
                    isCooked = true;
                    CookedModel model = new CookedModel(mealID, imgUrl, mealName);
                    model.save();
                    cooked.setVisibility(View.INVISIBLE);
                    uncoocked.setVisibility(View.VISIBLE);
                }
            }
        });
        uncoocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCooked = false;
                if(cookedMeals != null){
                    for (int i=0; i<cookedMeals.size(); i++){
                        CookedModel deleteModel = cookedMeals.get(i);
                        int identNum = deleteModel.getIdentificationNum();
                        if (identNum == mealID){
                            deleteModel.delete();
                        }
                    }
                }
                cooked.setVisibility(View.VISIBLE);
                uncoocked.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void passIngredients(ArrayList<IngredientsAndValueModel> passedIngredients) {
        ingredients = passedIngredients;
    }
}

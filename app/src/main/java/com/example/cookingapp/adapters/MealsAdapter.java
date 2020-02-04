package com.example.cookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookingapp.R;
import com.example.cookingapp.models.DietMealsModel;
import com.example.cookingapp.models.Meal;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_HEAD = 1;

    public ArrayList<DietMealsModel> meals;
    Context context;
    LayoutInflater inflater;

    public MealsAdapter(Context context,ArrayList<DietMealsModel> meals) {
        this.meals = meals;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEAD){
            View view = inflater.inflate(R.layout.item_meal_header_browose,parent,false);
            return new HeadMealViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_meals_rw_diets, parent,false);
            return new MealsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeadMealViewHolder){
            showHeaderMeal((HeadMealViewHolder) holder, position);
        } else {
            populateMeals((MealsViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEAD : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class HeadMealViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, category;

        public HeadMealViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.top_item_image);
            title = (TextView) itemView.findViewById(R.id.top_item_title);
            category = (TextView) itemView.findViewById(R.id.top_item_category);
        }
    }

    public class MealsViewHolder extends RecyclerView.ViewHolder {

        ImageView mealcImg;
        TextView mealsTitle, mealsCathegory, score;

        public MealsViewHolder(@NonNull View itemView) {
            super(itemView);

            mealcImg = (ImageView) itemView.findViewById(R.id.meal_image);
            mealsTitle = (TextView) itemView.findViewById(R.id.meal_title_rw_diets_regular);
            mealsCathegory = (TextView) itemView.findViewById(R.id.meal_category_rw_diets_regular);
            score = (TextView) itemView.findViewById(R.id.meal_score_rw_diets_regular);
        }
    }

    private void showHeaderMeal (HeadMealViewHolder holder, int position){
        DietMealsModel meal = meals.get(position);
        holder.title.setText(meal.getTitle());
        //holder.category.setText(meal.getCuisines().toString());
        if (meal.getImage() != null) {
            String baseurl = "https://spoonacular.com/recipeImages/";
            String img =  baseurl + meal.getImage();
            Glide.with(context).load(img).placeholder(R.drawable.placeholder).into(holder.imageView);
        }
    }

    private void populateMeals (MealsViewHolder holder, int position){
        DietMealsModel meal = meals.get(position);
        holder.mealsTitle.setText(meal.getTitle());
     //   holder.mealsCathegory.setText(meal.getCuisines().toString());
     //   holder.score.setText(meal.getSpoonacularScore() + "");
        if (meal.getImage() != null) {
            String baseurl = "https://spoonacular.com/recipeImages/";
            String img = baseurl + meal.getImage();
            Glide.with(context).load(img).placeholder(R.drawable.placeholder).into(holder.mealcImg);
        }
    }
}

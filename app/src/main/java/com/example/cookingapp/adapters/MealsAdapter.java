package com.example.cookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookingapp.R;
import com.example.cookingapp.interfaces.MealListener;
import com.example.cookingapp.models.DietMealsModel;
import com.example.cookingapp.models.Meal;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_HEAD = 1;
    private final int VIEW_TYPE_LOADING = 2;

    public ArrayList<DietMealsModel> meals;
    Context context;
    LayoutInflater inflater;
    MealListener listener;

    public MealsAdapter(Context context,ArrayList<DietMealsModel> meals, MealListener listener) {
        this.meals = meals;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_HEAD){
            View view = inflater.inflate(R.layout.item_meal_header_browose,parent,false);
            return new HeadMealViewHolder(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.item_meals_rw_diets, parent,false);
            return new MealsViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_loading, parent,false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeadMealViewHolder){
            showHeaderMeal((HeadMealViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        } else {
            populateMeals((MealsViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return VIEW_TYPE_HEAD;
        } else if (meals.get(position) == null){
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_ITEM;
        }
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getMealInfo(meals.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    public class MealsViewHolder extends RecyclerView.ViewHolder {

        ImageView mealcImg;
        TextView mealsTitle, mealsCathegory, scoretext, scoreNum, healthtext, healthNum;
        View devider;

        public MealsViewHolder(@NonNull View itemView) {
            super(itemView);

            mealcImg = (ImageView) itemView.findViewById(R.id.meal_image);
            mealsTitle = (TextView) itemView.findViewById(R.id.meal_title_rw_diets_regular);
            mealsCathegory = (TextView) itemView.findViewById(R.id.meal_category_rw_diets_regular);
            scoretext = (TextView) itemView.findViewById(R.id.meal_score_rw_diets_regular);
            scoreNum = (TextView) itemView.findViewById(R.id.score_number);
            healthtext = (TextView) itemView.findViewById(R.id.health_score);
            healthNum = (TextView) itemView.findViewById(R.id.health_score_number);
            devider = (View) itemView.findViewById(R.id.devider);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getMealInfo(meals.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
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

        DecimalFormat df = new DecimalFormat("0.###");
        holder.scoreNum.setText(df.format(meal.getReadyInMinutes())+ " " + "min");
        holder.healthtext.setText(df.format(meal.getServings())+ " " + "servings");

        holder.mealsCathegory.setVisibility(View.INVISIBLE);
    //    holder.healthNum.setVisibility(View.GONE);
     //   holder.healthtext.setVisibility(View.GONE);
     //   holder.devider.setVisibility(View.GONE);
     //   holder.scoretext.setVisibility(View.GONE);
     //   holder.scoreNum.setVisibility(View.GONE);

        if (meal.getImage() != null) {
            String baseurl = "https://spoonacular.com/recipeImages/";
            String img = baseurl + meal.getImage();
            Glide.with(context).load(img).placeholder(R.drawable.placeholder).into(holder.mealcImg);
        }
    }

    private void showLoadingView (LoadingViewHolder viewHolder, int position){
        //ProgressBar would be displayed
    }
}

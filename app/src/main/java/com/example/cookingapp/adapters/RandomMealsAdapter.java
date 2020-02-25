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
import com.example.cookingapp.models.RecipeInformationModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RandomMealsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_HEAD = 1;
    private final int VIEW_TYPE_LOADING = 2;

    public ArrayList<RecipeInformationModel> meals;
    Context context;
    LayoutInflater inflater;
    MealListener listener;

    public RandomMealsAdapter(ArrayList<RecipeInformationModel> meals, Context context, MealListener listener) {
        this.meals = meals;
        this.context = context;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_HEAD){
            View view = inflater.inflate(R.layout.item_meal_header_browose,parent,false);
            return new RandomMealsAdapter.HeadMealViewHolder(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.item_meals_rw_diets, parent,false);
            return new RandomMealsAdapter.MealsViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_loading, parent,false);
            return new RandomMealsAdapter.LoadingViewHolder(view);
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
    public int getItemCount() {
        return meals.size();
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

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    public class MealsViewHolder extends RecyclerView.ViewHolder {

        ImageView mealcImg;
        TextView mealsTitle, mealsCathegory, score, healthScore;

        public MealsViewHolder(@NonNull View itemView) {
            super(itemView);

            mealcImg = (ImageView) itemView.findViewById(R.id.meal_image);
            mealsTitle = (TextView) itemView.findViewById(R.id.meal_title_rw_diets_regular);
            mealsCathegory = (TextView) itemView.findViewById(R.id.meal_category_rw_diets_regular);
            score = (TextView) itemView.findViewById(R.id.score_number);
            healthScore = (TextView) itemView.findViewById(R.id.health_score_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getMealInfo(meals.get(getAdapterPosition()).getId());
                }
            });
        }
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

    private void showHeaderMeal (RandomMealsAdapter.HeadMealViewHolder holder, int position){
        RecipeInformationModel meal = meals.get(position);
        holder.title.setText(meal.getTitle());
        ArrayList<String> dishtype =  meal.getDishTypes();
        if (dishtype != null){
            holder.category.setText(dishtype.get(0));
        } else {holder.category.setText("No Category");}
        if (!meal.getImage().isEmpty()) {
            String img =  meal.getImage();
            Glide.with(context).load(img).placeholder(R.drawable.placeholder).into(holder.imageView);
        }
    }

    private void populateMeals (RandomMealsAdapter.MealsViewHolder holder, int position){
        RecipeInformationModel meal = meals.get(position);
        holder.mealsTitle.setText(meal.getTitle());

        if (meal.getImage() != null) {
            String img =  meal.getImage();
            Glide.with(context).load(img).placeholder(R.drawable.placeholder).into(holder.mealcImg);
        }
        ArrayList<String> dishtype =  meal.getDishTypes();
        if (dishtype != null){
            String mealChategory = getDishTypesInfo(dishtype);
            holder.mealsCathegory.setText(mealChategory);
        }
        DecimalFormat df = new DecimalFormat("0.###");
        holder.score.setText(df.format(meal.getReadyInMinutes())+ " " + "min");
        holder.healthScore.setText(df.format(meal.getServings())+ " " + "servings");
    }

    private void showLoadingView (RandomMealsAdapter.LoadingViewHolder viewHolder, int position){
        //ProgressBar would be displayed
    }

    private String getDishTypesInfo (ArrayList<String> dishType){
        String mealCategory = "Category:";
        for (int i = 0; i<dishType.size(); i++){
            String type = dishType.get(i);
            if (i == dishType.size()-1){
                mealCategory = mealCategory + " " + type;
            } else {
                mealCategory = mealCategory + " " + type+ ",";
            }
        }
        return mealCategory;
    }
}

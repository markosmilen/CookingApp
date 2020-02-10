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
import com.example.cookingapp.models.DietMealsModel;
import com.example.cookingapp.models.RandomrRecipesModel;

import java.util.ArrayList;

public class PopularMealsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    Context context;
    ArrayList<RandomrRecipesModel> popularMeals;
    LayoutInflater inflater;

    public PopularMealsAdapter(Context context, ArrayList<RandomrRecipesModel> popularMeals) {
        this.context = context;
        this.popularMeals = popularMeals;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM){
            View view = inflater.inflate(R.layout.item_popular_meal, parent, false);
            return new PopularMealsViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (popularMeals.get(position) == null){
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PopularMealsViewHolder){
            showPopularMeal((PopularMealsViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return popularMeals.size();
    }

    /*   @NonNull
    @Override
    public PopularMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_popular_meal, parent, false);
        return new PopularMealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMealsViewHolder holder, int position) {
        String dishtype = "";
        RandomrRecipesModel model = popularMeals.get(position);
        holder.title.setText(model.getTitle());
        if (model.getDishTypes() != null){
            dishtype = model.getDishTypes().get(0);
        }
        holder.dishType.setText(dishtype);
        String img = model.getImage();
        Glide.with(context).load(img).placeholder(R.drawable.placeholder_big).into(holder.dishIMG);
    }

    @Override
    public int getItemCount() {
        return popularMeals.size();
    }
*/
    public class PopularMealsViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView dishType;
        ImageView dishIMG;

        public PopularMealsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.popular_item_title);
            dishType = itemView.findViewById(R.id.popular_item_chategory);
            dishIMG = itemView.findViewById(R.id.popular_item_image);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    private void showLoadingView (LoadingViewHolder viewHolder, int position){
        //ProgressBar would be displayed
    }

    private void showPopularMeal (PopularMealsViewHolder holder, int position){
        String dishtype = "";
        RandomrRecipesModel model = popularMeals.get(position);
        holder.title.setText(model.getTitle());
        if (model.getDishTypes() != null){
            dishtype = model.getDishTypes().get(0);
        }
        holder.dishType.setText(dishtype);
        String img = model.getImage();
        Glide.with(context).load(img).placeholder(R.drawable.placeholder_big).into(holder.dishIMG);
    }
}

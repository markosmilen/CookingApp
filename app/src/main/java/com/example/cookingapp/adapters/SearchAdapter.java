package com.example.cookingapp.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.cookingapp.activities.DetailsActivity;
import com.example.cookingapp.models.DietMealsModel;
import com.example.cookingapp.models.RecipeInformationModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    ArrayList<DietMealsModel> searchedMeals;
    LayoutInflater inflater;
    Context context;

    public SearchAdapter(ArrayList<DietMealsModel> searchedMeals, Context context) {
        this.searchedMeals = searchedMeals;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {

        if (viewType == VIEW_TYPE_ITEM){
            View view = inflater.inflate(R.layout.item_search_meal, parent,false);
            return new SearchAdapter.SearchViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_loading, parent, false);
            return new SearchAdapter.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchAdapter.SearchViewHolder){
            showSearchedMeals((SearchAdapter.SearchViewHolder) holder, position);
        } else if (holder instanceof PopularMealsAdapter.LoadingViewHolder) {
            showLoadingView((PopularMealsAdapter.LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (searchedMeals.get(position) == null){
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return searchedMeals.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView searchMealTitle;
        ImageView searchMealImg;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            searchMealTitle = (TextView) itemView.findViewById(R.id.search_meal_name);
            searchMealImg = (ImageView) itemView.findViewById(R.id.search_meal_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsIntent = new Intent(context, DetailsActivity.class);
                    detailsIntent.putExtra("ID", searchedMeals.get(getAdapterPosition()).getId());
                    context.startActivity(detailsIntent);
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

    private void showLoadingView (PopularMealsAdapter.LoadingViewHolder viewHolder, int position){
        //ProgressBar would be displayed
    }

    private void showSearchedMeals (SearchAdapter.SearchViewHolder holder, int position){
        DietMealsModel model = searchedMeals.get(position);
        String baseurl = "https://spoonacular.com/recipeImages/";
        if (model.getImage() != null){
            String img = model.getImage();
            Glide.with(context).load(img).centerCrop().into(holder.searchMealImg);
        }
        holder.searchMealTitle.setText(model.getTitle());
    }
}

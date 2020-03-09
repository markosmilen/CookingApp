package com.example.cookingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookingapp.R;
import com.example.cookingapp.activities.DetailsActivity;
import com.example.cookingapp.models.DietMealsModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

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
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_search_meal, parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        DietMealsModel model = searchedMeals.get(position);
        String baseurl = "https://spoonacular.com/recipeImages/";
        String img = model.getImage();
        Glide.with(context).load(img).centerCrop().into(holder.searchMealImg);
        holder.searchMealTitle.setText(model.getTitle());
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
}

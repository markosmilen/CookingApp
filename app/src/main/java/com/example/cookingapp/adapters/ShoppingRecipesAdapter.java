package com.example.cookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookingapp.R;
import com.example.cookingapp.interfaces.MealListener;
import com.example.cookingapp.interfaces.RerfreshListener;
import com.example.cookingapp.models.ShoppingListModel;
import com.example.cookingapp.models.ShoppingRecipe;

import java.util.List;

public class ShoppingRecipesAdapter extends RecyclerView.Adapter<ShoppingRecipesAdapter.ShoppingRecipesViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<ShoppingRecipe> recipes;
    int id;
    MealListener listener;

    public ShoppingRecipesAdapter(Context context, List<ShoppingRecipe> recipes, MealListener listener) {
        this.context = context;
        this.recipes = recipes;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShoppingRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_shopping_list_recipe, parent, false);
        return new ShoppingRecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingRecipesViewHolder holder, int position) {
        ShoppingRecipe recipe = recipes.get(position);
        holder.mealTitle.setText(recipe.getRecipeName());
        String url = recipe.getRecipeImg();
        Glide.with(context).load(url).into(holder.mealImg);
        id = recipe.getRecepiID();
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ShoppingRecipesViewHolder extends RecyclerView.ViewHolder {

        ImageView mealImg;
        TextView mealTitle;
        Button delete;

        public ShoppingRecipesViewHolder(@NonNull View itemView) {
            super(itemView);

            mealImg = (ImageView) itemView.findViewById(R.id.shopping_list_img);
            mealImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getMealInfo(recipes.get(getAdapterPosition()).getRecepiID());
                }
            });
            mealTitle = (TextView) itemView.findViewById(R.id.shoppingRecipe_title);
            delete = (Button) itemView.findViewById(R.id.shoppingRecipe_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShoppingListModel.deleteAll(ShoppingListModel.class, "recipe = ?", recipes.get(getAdapterPosition()).getId() + "");
                    ShoppingRecipe deleteModel = recipes.get(getAdapterPosition());
                    deleteModel.delete();
                    recipes.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}

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
import com.example.cookingapp.interfaces.DeleteBookmarkListener;
import com.example.cookingapp.models.CookedModel;

import java.util.List;

public class CookedMealsAdapter extends RecyclerView.Adapter<CookedMealsAdapter.CookedMealsViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<CookedModel> cookedMeals;
    DeleteBookmarkListener listener;

    public CookedMealsAdapter(Context context, List<CookedModel> cookedMeals, DeleteBookmarkListener listener) {
        this.context = context;
        this.cookedMeals = cookedMeals;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CookedMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_bookmark, parent,false);
        return new CookedMealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CookedMealsViewHolder holder, int position) {
        CookedModel model = cookedMeals.get(position);
        holder.title.setText(model.getName());
        String url = model.getImg();
        Glide.with(context.getApplicationContext()).load(url).centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cookedMeals.size();
    }

    public class CookedMealsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        Button delete_cookedMeal;

        public CookedMealsViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.bookmark_meal_imake);
            title = (TextView) itemView.findViewById(R.id.bookmark_meal_name);
            delete_cookedMeal = (Button) itemView.findViewById(R.id.delete_bookmark);
            delete_cookedMeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCookedMealDeleted(cookedMeals.get(getAdapterPosition()).getIdentificationNum(), getAdapterPosition());
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getMealInfo(cookedMeals.get(getAdapterPosition()).getIdentificationNum());
                }
            });
        }
    }
}

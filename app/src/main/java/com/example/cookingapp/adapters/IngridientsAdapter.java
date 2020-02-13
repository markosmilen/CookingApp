package com.example.cookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.models.IngredientsAmountModel;
import com.example.cookingapp.models.IngredientsAndValueModel;
import com.example.cookingapp.models.IngredientsModel;

import java.util.List;

public class IngridientsAdapter extends RecyclerView.Adapter<IngridientsAdapter.IngridientsViewHolder> {

    List<IngredientsAndValueModel> ingredients;
    Context context;
    LayoutInflater inflater;

    public IngridientsAdapter(Context context, List<IngredientsAndValueModel> ingredients) {
        this.ingredients = ingredients;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public IngridientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_ingridient, parent, false);
        return new IngridientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngridientsViewHolder holder, int position) {
        IngredientsAndValueModel ingredientsAndValueModel = ingredients.get(position);
        holder.ingridient.setText(ingredientsAndValueModel.getName());
        holder.amounth.setText(String.format("%.2f", ingredientsAndValueModel.getAmount().getMetric().getValue()));
        holder.description.setText(ingredientsAndValueModel.getAmount().getMetric().getUnit());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngridientsViewHolder extends RecyclerView.ViewHolder {

        TextView amounth, description, ingridient;

        public IngridientsViewHolder(@NonNull View itemView) {
            super(itemView);
            amounth = (TextView) itemView.findViewById(R.id.ingridients_amount);
            ingridient = (TextView) itemView.findViewById(R.id.ingridients_name);
            description = (TextView) itemView.findViewById(R.id.ingredients_amount_description);
        }
    }
}

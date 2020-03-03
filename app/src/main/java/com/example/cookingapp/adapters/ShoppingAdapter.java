package com.example.cookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.models.ShoppingListModel;

import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingListViewHilder> {

    List<ShoppingListModel> cartList;
    LayoutInflater inflater;
    Context context;

    public ShoppingAdapter (Context context, List<ShoppingListModel> cartList){
        this.cartList = cartList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ShoppingListViewHilder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_shopping_ingridient, parent, false);
        return new ShoppingListViewHilder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHilder holder, int position) {
        ShoppingListModel model = cartList.get(position);
        String amount = model.getAmount() + "";
        String value = model.getValue();
        String name = model.getTitle();
        holder.amount.setText(amount + " " +value);
        holder.ingredient.setText(name);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ShoppingListViewHilder extends RecyclerView.ViewHolder {

        TextView amount, ingredient;
        ImageView checked;

        public ShoppingListViewHilder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.shpping_ingredient_amount);
            ingredient = itemView.findViewById(R.id.shopping_ingredient_name);
            checked = itemView.findViewById(R.id.ingredient_checked);
        }
    }
}

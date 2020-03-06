package com.example.cookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.models.ShoppingListModel;

import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingListViewHilder> {

    List<ShoppingListModel> cartList;
    LayoutInflater inflater;
    Context context;
    Boolean bought;

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
        bought = model.isBought();
        if (bought == true){
           holder.amount.setTextColor(context.getResources().getColor(R.color.colorGray));
           holder.checked.setVisibility(View.VISIBLE);
           holder.unchecked.setVisibility(View.INVISIBLE);
        } else {
            holder.amount.setTextColor(context.getResources().getColor(R.color.color_black));
            holder.checked.setVisibility(View.INVISIBLE);
            holder.unchecked.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ShoppingListViewHilder extends RecyclerView.ViewHolder {

        TextView amount, ingredient;
        ImageView checked, unchecked;

        public ShoppingListViewHilder(@NonNull final View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.shpping_ingredient_amount);
            ingredient = itemView.findViewById(R.id.shopping_ingredient_name);
            checked = itemView.findViewById(R.id.ingredient_checked);
            unchecked = itemView.findViewById(R.id.ingredient_unchecked);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bought = cartList.get(getAdapterPosition()).isBought();
                    if (bought==false){
                        ShoppingListModel model = cartList.get(getAdapterPosition());
                        model.setBought(true);
                        model.save();
                        amount.setTextColor(itemView.getResources().getColor(R.color.colorGray));
                        checked.setVisibility(View.VISIBLE);
                        unchecked.setVisibility(View.INVISIBLE);
                    } else {
                        ShoppingListModel model = cartList.get(getAdapterPosition());
                        model.setBought(false);
                        model.save();
                        amount.setTextColor(itemView.getResources().getColor(R.color.color_black));
                        checked.setVisibility(View.INVISIBLE);
                        unchecked.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}

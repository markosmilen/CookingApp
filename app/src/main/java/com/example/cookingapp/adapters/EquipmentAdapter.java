package com.example.cookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookingapp.R;
import com.example.cookingapp.models.EquipmentModel;

import java.util.List;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder> {

    Context context;
    List<EquipmentModel> equipment;
    LayoutInflater inflater;

    public EquipmentAdapter(Context context, List<EquipmentModel> equipment) {
        this.context = context;
        this.equipment = equipment;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_equipment, parent, false);
        return new EquipmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentViewHolder holder, int position) {
        EquipmentModel model = equipment.get(position);
        holder.equipment.setText(model.getName());
        String img = "https://spoonacular.com/cdn/equipment_250x250/" +  model.getImage();
        Glide.with(context).load(img).centerInside().into(holder.equipmentImg);
    }

    @Override
    public int getItemCount() {
        return equipment == null ? 0 : equipment.size();
    }

    public class EquipmentViewHolder extends RecyclerView.ViewHolder {
        TextView equipment;
        ImageView equipmentImg;

        public EquipmentViewHolder(@NonNull View itemView) {
            super(itemView);
            equipment = (TextView) itemView.findViewById(R.id.equipment_item);
            equipmentImg = (ImageView) itemView.findViewById(R.id.equipment_img);
        }
    }
}

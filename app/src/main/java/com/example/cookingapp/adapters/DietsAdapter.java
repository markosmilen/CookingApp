package com.example.cookingapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.interfaces.DietListener;
import com.example.cookingapp.models.ButtonModel;

import java.util.List;

public class DietsAdapter extends RecyclerView.Adapter<DietsAdapter.DietViewHolder> {

    private List<ButtonModel> diets;
    Context context;
    LayoutInflater inflater;
    int[] indexces;
    DietListener listener;

    public DietsAdapter(Context context, int[] indexces, DietListener listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.indexces = indexces;
        this.listener = listener;
        Log.d("INDEXES", indexces[0] + "" + indexces[1] + "" + indexces[2] + "");

        diets = ButtonModel.prepareButtons(context.getResources().getStringArray(R.array.diets));
    }

    @NonNull
    @Override
    public DietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_button, parent, false);
        return new DietViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DietViewHolder holder, int position) {

        ButtonModel model = diets.get(indexces[position]);
        holder.button.setText(model.getButton());
    }

    @Override
    public int getItemCount() {
        return indexces.length;
    }

    public class DietViewHolder extends RecyclerView.ViewHolder {

        Button button;

        public DietViewHolder(@NonNull View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.item_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDietClicked(button.getText().toString());
                }
            });
        }
    }
}

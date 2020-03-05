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
import com.example.cookingapp.interfaces.VideoListener;
import com.example.cookingapp.models.VideoModel;

import java.util.List;

public class VIdeoMealsAdapter extends RecyclerView.Adapter<VIdeoMealsAdapter.VideoMealsViewHolder> {

    Context context;
    List<VideoModel> videos;
    LayoutInflater inflater;
    VideoListener listener;


    public VIdeoMealsAdapter(Context context, List<VideoModel> videos, VideoListener listener) {
        this.context = context;
        this.videos = videos;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_videos, parent, false);
        return new VideoMealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoMealsViewHolder holder, int position) {
        VideoModel model = videos.get(position);
        holder.mealTitle.setText(model.getTitle());
        String img = model.getThumbnail();
        Glide.with(context).load(img).centerCrop().into(holder.mealImg);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoMealsViewHolder extends RecyclerView.ViewHolder {

        ImageView mealImg;
        TextView mealTitle;

        public VideoMealsViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImg = (ImageView) itemView.findViewById(R.id.image_video);
            mealTitle = (TextView) itemView.findViewById(R.id.video_meal_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.openYoutube(v, videos.get(getAdapterPosition()).getYouTubeId());
                }
            });
        }
    }
}

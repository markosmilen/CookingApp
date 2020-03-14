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
import com.example.cookingapp.interfaces.MealListener;
import com.example.cookingapp.models.BookmarkedModel;

import java.util.List;


public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarkViewHolder> {

    Context context;
    public List<BookmarkedModel> bookmarks;
    LayoutInflater inflater;
    DeleteBookmarkListener listener;
    int id;

    public BookmarksAdapter(Context context, List<BookmarkedModel> bookmarks, DeleteBookmarkListener listener) {
        this.context = context;
        this.bookmarks = bookmarks;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_bookmark, parent, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        BookmarkedModel model = bookmarks.get(position);
        String imgUrl = model.getImg();
        holder.bookmarkName.setText(model.getName());
        id = model.getIdentificationNum();
        Glide.with(context.getApplicationContext()).load(imgUrl).centerCrop().placeholder(R.drawable.placeholder).into(holder.bookmarkImg);
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder {

        ImageView bookmarkImg;
        TextView bookmarkName;
        Button delete_bookmark;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            bookmarkImg = (ImageView) itemView.findViewById(R.id.bookmark_meal_imake);
            bookmarkName = (TextView) itemView.findViewById(R.id.bookmark_meal_name);
            delete_bookmark = (Button) itemView.findViewById(R.id.delete_bookmark);

            delete_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBookmarkDeleted(bookmarks.get(getAdapterPosition()).getIdentificationNum(),getAdapterPosition());
                }
            });

            bookmarkImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getMealInfo(bookmarks.get(getAdapterPosition()).getIdentificationNum());
                }
            });
        }
    }
}

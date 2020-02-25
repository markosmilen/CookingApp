package com.example.cookingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.example.cookingapp.R;
import com.example.cookingapp.activities.DetailsActivity;
import com.example.cookingapp.adapters.BookmarksAdapter;
import com.example.cookingapp.interfaces.DeleteBookmarkListener;
import com.example.cookingapp.models.BookmarkedModel;

import java.util.List;


public class TopLvlBookmarkFragment extends Fragment implements DeleteBookmarkListener {

    List<BookmarkedModel> bookmarkedMeals;
    RelativeLayout noBookmarks, withBookmarks;
    androidx.appcompat.widget.Toolbar toolbar;
    RecyclerView recyclerViewBookmarks;
    BookmarksAdapter bookmarksAdapter;

    public TopLvlBookmarkFragment() {
        // Required empty public constructor
    }


    public static TopLvlBookmarkFragment newInstance(String param1, String param2) {
        TopLvlBookmarkFragment fragment = new TopLvlBookmarkFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_lvl_bookmark, container, false);
        noBookmarks = (RelativeLayout) view.findViewById(R.id.bookmark_layout_no_items);
        withBookmarks = (RelativeLayout) view.findViewById(R.id.bookmark_layout_with_items);
        bookmarkedMeals = BookmarkedModel.listAll(BookmarkedModel.class);

        recyclerViewBookmarks = (RecyclerView) view.findViewById(R.id.recycler_view_bookmarked);
        recyclerViewBookmarks.setLayoutManager(new GridLayoutManager(getContext(), 2));
        bookmarksAdapter = new BookmarksAdapter(getContext(),bookmarkedMeals, this);
        recyclerViewBookmarks.setAdapter(bookmarksAdapter);


        setLayoutsVisibility();

        toolbar = (androidx.appcompat.widget.Toolbar) view.findViewById(R.id.toolbarBookmark);
        toolbar.setTitle("My Saved Meals");
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_primary));


        return  view;
    }

    private void setLayoutsVisibility() {
        if (bookmarkedMeals.isEmpty()){
            noBookmarks.setVisibility(View.VISIBLE);
            withBookmarks.setVisibility(View.INVISIBLE);
        } else { noBookmarks.setVisibility(View.INVISIBLE);
            withBookmarks.setVisibility(View.VISIBLE);}
    }

    @Override
    public void onBookmarkDeleted(int id, int position) {
        BookmarkedModel model = (BookmarkedModel.find(BookmarkedModel.class,
                "IDENTIFICATION_NUM = ?", String.valueOf(id)))
                .get(0);
        if (model != null) {
            model.delete();
        }

        bookmarkedMeals.remove(position);
        recyclerViewBookmarks.getRecycledViewPool().clear();
        bookmarksAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMealInfo(int id) {
        Intent detailsIntent = new Intent(getContext(), DetailsActivity.class);
        detailsIntent.putExtra("ID", id);
        startActivity(detailsIntent);
    }
}

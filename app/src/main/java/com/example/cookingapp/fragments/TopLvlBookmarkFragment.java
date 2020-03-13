package com.example.cookingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.cookingapp.R;
import com.example.cookingapp.activities.DetailsActivity;
import com.example.cookingapp.adapters.BookmarksAdapter;
import com.example.cookingapp.adapters.CookedMealsAdapter;
import com.example.cookingapp.interfaces.DeleteBookmarkListener;
import com.example.cookingapp.models.BookmarkedModel;
import com.example.cookingapp.models.CookedModel;

import java.util.ArrayList;
import java.util.List;


public class TopLvlBookmarkFragment extends Fragment implements DeleteBookmarkListener {

    List<BookmarkedModel> bookmarkedMeals = new ArrayList<>();
    List<CookedModel> cookBookmMeals = new ArrayList<>();
    RelativeLayout noBookmarks;
    LinearLayout withBookmarks;
    androidx.appcompat.widget.Toolbar toolbar, cookbook;
    RecyclerView recyclerViewBookmarks, recyclerViewCookedMeals;
    BookmarksAdapter bookmarksAdapter;
    CookedMealsAdapter cookedMealsAdapter;
    ImageButton addMeals;

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
        withBookmarks = (LinearLayout) view.findViewById(R.id.bookmark_layout_with_items);
        final TopLvlFeaturedFragment fragment = new TopLvlFeaturedFragment();
        addMeals = (ImageButton) view.findViewById(R.id.add_button);
        addMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.top_lvl_frame, fragment, null).addToBackStack(null).commit();
            }
        });

        new GetAllBookmarksAsyncTask().execute();

     //   bookmarkedMeals = BookmarkedModel.listAll(BookmarkedModel.class);
     //   cookBookmMeals = CookedModel.listAll(CookedModel.class);

        recyclerViewBookmarks = (RecyclerView) view.findViewById(R.id.recycler_view_bookmarked);
        recyclerViewCookedMeals = (RecyclerView) view.findViewById(R.id.recycler_view_cookbook);


        toolbar = (androidx.appcompat.widget.Toolbar) view.findViewById(R.id.toolbarBookmark);
        toolbar.setTitle("My Saved Meals");
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_primary));

        cookbook = (Toolbar) view.findViewById(R.id.toolbarCookBook);
        cookbook.setTitle("My Cooked Meals");
        cookbook.setTitleTextColor(getResources().getColor(R.color.color_primary));

        return  view;
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
        setLayoutsVisibility();
    }

    @Override
    public void getMealInfo(int id) {
        Intent detailsIntent = new Intent(getContext(), DetailsActivity.class);
        detailsIntent.putExtra("ID", id);
        startActivity(detailsIntent);
    }

    @Override
    public void onCookedMealDeleted(int id, int position) {
        CookedModel model = (CookedModel.find(CookedModel.class, "IDENTIFICATION_NUM = ?", String.valueOf(id)))
                .get(0);
        if (model != null){
            model.delete();
        }
        cookBookmMeals.remove(position);
        recyclerViewCookedMeals.getRecycledViewPool().clear();
        cookedMealsAdapter.notifyDataSetChanged();
        setLayoutsVisibility();
    }

    private void setLayoutsVisibility() {

        if (bookmarkedMeals.isEmpty() && cookBookmMeals.isEmpty()){
            noBookmarks.setVisibility(View.VISIBLE);
            withBookmarks.setVisibility(View.INVISIBLE);
        } else if (bookmarkedMeals != null || cookBookmMeals != null){ noBookmarks.setVisibility(View.INVISIBLE);
            withBookmarks.setVisibility(View.VISIBLE);}

        if (bookmarkedMeals.isEmpty()){
            recyclerViewBookmarks.setVisibility(View.GONE);
        }
        if (cookBookmMeals.isEmpty()){
            recyclerViewCookedMeals.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        new GetAllBookmarksAsyncTask().execute();
        super.onResume();
    }

    public class GetAllBookmarksAsyncTask extends AsyncTask <Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            bookmarkedMeals = BookmarkedModel.listAll(BookmarkedModel.class);
            cookBookmMeals = CookedModel.listAll(CookedModel.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            recyclerViewBookmarks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            bookmarksAdapter = new BookmarksAdapter(getContext(),bookmarkedMeals, TopLvlBookmarkFragment.this);
            recyclerViewBookmarks.setAdapter(bookmarksAdapter);

            recyclerViewCookedMeals.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            cookedMealsAdapter = new CookedMealsAdapter(getContext(), cookBookmMeals,TopLvlBookmarkFragment.this);
            recyclerViewCookedMeals.setAdapter(cookedMealsAdapter);

            setLayoutsVisibility();
            super.onPostExecute(aVoid);
        }
    }

}

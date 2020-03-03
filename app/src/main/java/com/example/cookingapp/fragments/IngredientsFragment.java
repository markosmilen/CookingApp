package com.example.cookingapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingapp.R;
import com.example.cookingapp.adapters.IngridientsAdapter;
import com.example.cookingapp.interfaces.IngredientsListener;
import com.example.cookingapp.models.IngredientsAndValueModel;
import com.example.cookingapp.models.RecepieIngridientsModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class IngredientsFragment extends Fragment {

    public static final String TAG = IngredientsFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "ID";

    private

    ArrayList<IngredientsAndValueModel> ingrediens = new ArrayList<>();
    RecyclerView recycler;
    IngridientsAdapter adapter;
    IngredientsListener listener;
    private String id;
    ViewPager pager;
    Gson gson;


    public IngredientsFragment() {
        // Required empty public constructor
    }

    public static IngredientsFragment newInstance(String id) {
        IngredientsFragment fragment = new IngredientsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_PARAM1);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_lvl_ingridients, container, false);
        gson = new Gson();

        pager = (ViewPager) view.findViewById(R.id.viewPagerIngredients);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view_ingridients);

        poulateIngridients(id);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new IngridientsAdapter(getContext(), ingrediens);
        recycler.setAdapter(adapter);
        return view;
    }

    public ArrayList<IngredientsAndValueModel> passList(){
        return ingrediens;
    }

    private void poulateIngridients(String id) {
        HttpUrl url = new HttpUrl.Builder().scheme("https").host("api.spoonacular.com").addPathSegment("recipes").addPathSegment(id).addPathSegment("ingredientWidget.json").addQueryParameter("apiKey", "538bac8dcbdc467c9c1683802b57809b").build();
        Log.d("URLINGR", url + "");
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("INGRIDIENTS_ERROR", e + "");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    Log.d("THISISTHE", jsonString);
                    RecepieIngridientsModel model = gson.fromJson(jsonString, RecepieIngridientsModel.class);
                    ingrediens = model.getIngredients();
                    listener.passIngredients(ingrediens);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter = new IngridientsAdapter(getContext(), ingrediens);
                            recycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (IngredientsListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}

package com.example.cookingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.RequestBuilder;
import com.example.cookingapp.R;
import com.example.cookingapp.adapters.EquipmentAdapter;
import com.example.cookingapp.models.EquipmentByIdModel;
import com.example.cookingapp.models.EquipmentModel;
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

public class EquipmentFragment extends Fragment {

    private static final String ARG_PARAM1 = "ID";

    RecyclerView recyclerView;
    EquipmentAdapter adapter;
    ArrayList<EquipmentModel> equpment;
    Gson gson;
    private String id;


    public EquipmentFragment() {
        // Required empty public constructor
    }

    public static EquipmentFragment newInstance(String id) {
        EquipmentFragment fragment = new EquipmentFragment();
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equipment, container, false);
        gson = new Gson();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_equipment);
        getEquipment(id);
        return view;

    }

    private void getEquipment(String id) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.spoonacular.com")
                .addPathSegment("recipes")
                .addPathSegment(id)
                .addPathSegment("equipmentWidget.json")
                .addQueryParameter("apiKey", "538bac8dcbdc467c9c1683802b57809b")
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonstring = response.body().string();
                    EquipmentByIdModel model = gson.fromJson(jsonstring, EquipmentByIdModel.class);
                    equpment = model.getEquipment();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter = new EquipmentAdapter(getContext(), equpment);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });


    }

}

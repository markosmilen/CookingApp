package com.example.cookingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingapp.R;
import com.example.cookingapp.activities.BrowoseActivity;
import com.example.cookingapp.adapters.VIdeoMealsAdapter;
import com.example.cookingapp.interfaces.VideoListener;
import com.example.cookingapp.models.VideoModel;
import com.example.cookingapp.models.VideoResponseModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BottomLvlVideosFragment extends Fragment implements VideoListener {

    int offset;
    RecyclerView recyclerView;
    VIdeoMealsAdapter adapter;
    ArrayList<VideoModel> videos =  new ArrayList<>();
    Gson gson;

    public BottomLvlVideosFragment() {
        // Required empty public constructor
    }

    public static BottomLvlVideosFragment newInstance(String param1, String param2) {
        BottomLvlVideosFragment fragment = new BottomLvlVideosFragment();
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
        View view = inflater.inflate(R.layout.fragment_bottom_lvl_videos, container, false);
        gson = new Gson();
        offset = generateRandomOffset();
        generateVideos(offset);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_videos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public int generateRandomOffset (){
        int min = 0;
        int max = 170;
        Random r = new Random();
        int randomOffset = r.nextInt(max - min + 1) + min;
        return randomOffset;
    }

    public void generateVideos (int offset){
        HttpUrl.Builder builder = HttpUrl.parse("https://api.spoonacular.com/food/videos/search").newBuilder();
        builder.addQueryParameter("query", "a");
        builder.addQueryParameter("number", "30");
        builder.addQueryParameter("offset", offset+"");
        builder.addQueryParameter("apiKey", "538bac8dcbdc467c9c1683802b57809b");
        String url = builder.build().toString();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    VideoResponseModel model = gson.fromJson(jsonString, VideoResponseModel.class);
                    videos = model.getVideos();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new VIdeoMealsAdapter(getContext(),videos, BottomLvlVideosFragment.this);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void openYoutube(View view, String youtubeID) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+youtubeID));
        intent.putExtra("VIDEO_ID", youtubeID);
        intent.putExtra("force_fullscreen",true);
        startActivity(intent);
    }
}

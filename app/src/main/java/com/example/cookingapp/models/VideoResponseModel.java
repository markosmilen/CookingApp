package com.example.cookingapp.models;

import java.util.ArrayList;
import java.util.List;

public class VideoResponseModel {

    ArrayList<VideoModel> videos;

    VideoResponseModel(){}

    public ArrayList<VideoModel> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<VideoModel> videos) {
        this.videos = videos;
    }
}

package com.example.cookingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingapp.R;

public class TopLvlSearchFragment extends Fragment {


    public TopLvlSearchFragment() {
        // Required empty public constructor
    }



    public static TopLvlSearchFragment newInstance() {
        TopLvlSearchFragment fragment = new TopLvlSearchFragment();
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
        return inflater.inflate(R.layout.fragment_top_lvl_search, container, false);
    }




}

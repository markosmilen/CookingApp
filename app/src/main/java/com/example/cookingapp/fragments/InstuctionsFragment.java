package com.example.cookingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InstuctionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InstuctionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstuctionsFragment extends Fragment {

    public InstuctionsFragment() {
        // Required empty public constructor
    }


    public static InstuctionsFragment newInstance() {
        InstuctionsFragment fragment = new InstuctionsFragment();
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
        return inflater.inflate(R.layout.fragment_bottom_lvl_instuctions, container, false);
    }
}

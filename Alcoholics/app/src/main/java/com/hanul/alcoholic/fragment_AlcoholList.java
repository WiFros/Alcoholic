package com.hanul.alcoholic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_AlcoholList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_AlcoholList extends Fragment {


    public static fragment_AlcoholList newInstance(String param1, String param2) {
        fragment_AlcoholList fragment = new fragment_AlcoholList();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__alcohol_list, container, false);
    }
}
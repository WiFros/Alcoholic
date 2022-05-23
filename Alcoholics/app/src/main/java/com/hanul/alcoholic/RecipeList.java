package com.hanul.alcoholic;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeList extends Fragment{
    public View view;
    public Context context;

    public RecipeList() {
        // Required empty public constructor
    }

    public static RecipeList newInstance(String param1, String param2) {
        RecipeList fragment = new RecipeList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        Button button = view.findViewById(R.id.cocktailbtn);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getContext(), "Read all Recipe", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),API_Parsing_TEST_Ver.class);
                startActivity(intent);
            }
        });

        Button favoriteBtn = view.findViewById(R.id.favorite);
        favoriteBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Read all Recipe", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),myshelf.class);
                startActivity(intent);
            }
        });

        Button cocktailList = view.findViewById(R.id.cocktailListbtn);
        cocktailList.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Show all cocktail List", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),fastscroll_listview.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
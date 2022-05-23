package com.hanul.alcoholic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class myshelf extends AppCompatActivity {

    ArrayList<String> favorited = new ArrayList<String>();
    public int var;
    public static Context context_favorite;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshelf);
        context_favorite = this;
        //favorited = new ArrayList<String>();
        //final ArrayAdapter
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, favorited) ;
        //ListAdapter listAdapter = new ListAdapter(this, favorited);
        ListView listView = (ListView) findViewById(R.id.myshelflistview);
        Button button = findViewById(R.id.addBtn);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"add favorite", Toast.LENGTH_SHORT).show();
                favorited.add("ABC");
                adapter.notifyDataSetChanged();
            }
        });

        //List<String> favorited = ((API_Parsing)API_Parsing.context_api).newFavorite;
        //ListView listView = (ListView) findViewById(R.id.myshelflistview);
        //String[] fruits = getResources().getStringArray(R.array.ingredients_array);

        // //listView.setFastScrollEnabled(true);
        // //List<String> newFavorite = null;
        // //newFavorite.add(String.valueOf(favorited));
        // //List<String> fruitList = Arrays.asList(fruits);

        //ListAdapter listAdapter = new ListAdapter(this, favorited);
        //listView.setAdapter(listAdapter);
    }

    public void AddtoFavorite() {

        favorited.add("ABC");
        adapter.notifyDataSetChanged();

        Log.d("DDDDDDDDDDDDDDDD",favorited.toString());
    }
}
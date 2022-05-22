package com.hanul.alcoholic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class myshelf extends AppCompatActivity {

    public int var;
    public static Context context_favorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshelf);
        context_favorite = this;


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
        Log.d("AAAAA","AAAAAAAAAAAAA");
        List<String> favorited = ((API_Parsing)API_Parsing.context_api).newFavorite;
        ListAdapter listAdapter = new ListAdapter(this, favorited);
        ListView listView = (ListView) findViewById(R.id.myshelflistview);
        listView.setAdapter(listAdapter);
        favorited.add("item");
        String[] fruits = getResources().getStringArray(R.array.ingredients_array);

        Log.d("DDDDDDDDDDDDDDDD",favorited.toString());

    }
}
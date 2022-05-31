package com.hanul.alcoholic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddFavorite extends AppCompatActivity {

    private Button favoriteButton;

    ArrayList<String> favoriteName= new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailrecipedialog);

        favoriteButton=(Button)findViewById(R.id.detailRecipeBtn);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }


}

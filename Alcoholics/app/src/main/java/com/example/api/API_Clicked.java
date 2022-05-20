package com.hanul.alcoholic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class API_Clicked extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_clicked);
        Bundle bundle=getIntent().getExtras();
        String text=bundle.getString("arr_text");
        TextView textView=(TextView) findViewById(R.id.text);
        textView.setText(text);

    }
}

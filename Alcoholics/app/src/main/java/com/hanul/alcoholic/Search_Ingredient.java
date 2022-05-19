package com.hanul.alcoholic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class Search_Ingredient extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    CheckBox vodka_checkbox;
    CheckBox gin_checkbox;
    CheckBox rum_checkbox;
    CheckBox whiskey_checkbox;
    CheckBox tequila_checkbox;
    CheckBox brandy_checkbox;
    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vodka_checkbox = (CheckBox)findViewById(R.id.chkVodka);
        gin_checkbox = (CheckBox)findViewById(R.id.chkGin);
        rum_checkbox = (CheckBox)findViewById(R.id.chkRum);
        whiskey_checkbox = (CheckBox)findViewById(R.id.chkWhiskey);
        tequila_checkbox = (CheckBox)findViewById(R.id.chkTequila);
        brandy_checkbox = (CheckBox)findViewById(R.id.chkBrandy);
        resultTV = (TextView)findViewById(R.id.ingredientList);

        vodka_checkbox.setOnCheckedChangeListener(this);
        gin_checkbox.setOnCheckedChangeListener(this);
        rum_checkbox.setOnCheckedChangeListener(this);
        whiskey_checkbox.setOnCheckedChangeListener(this);
        tequila_checkbox.setOnCheckedChangeListener(this);
        brandy_checkbox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        String result = "";

        if(vodka_checkbox.isChecked()) result += "Vodka ";
        if(gin_checkbox.isChecked()) result += "Gin ";
        if(rum_checkbox.isChecked()) result += "Rum ";
        if(whiskey_checkbox.isChecked()) result += "Whiskey ";
        if(tequila_checkbox.isChecked()) result += "Tequila ";
        if(brandy_checkbox.isChecked()) result += "Brandy ";

        resultTV.setText("결과 : " + result);
    }
}
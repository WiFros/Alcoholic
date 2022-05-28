package com.hanul.alcoholic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.hanul.alcoholic.API_Ingredient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class API_Search_Ingredient extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    public static String key = "9973533";
    public static String address = "https://www.thecocktaildb.com/api/json/v2/";
    private ListView listView;
    private Button ibutton,sbutton;
    private ImageButton backbtn;
    private EditText editText;
    ArrayAdapter adapter;

    CheckBox vodka_checkbox;
    CheckBox gin_checkbox;
    CheckBox rum_checkbox;
    CheckBox whiskey_checkbox;
    CheckBox tequila_checkbox;
    CheckBox brandy_checkbox;
    TextView resultTV;

    // 칵테일 제목을 담을 ArrayList 변수(items) 선언
    ArrayList<String> items = new ArrayList<String>();
    String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_search_ingredient);

        listView = (ListView)findViewById(R.id.data);
        // adapter 스타일 선언 및 items 적용
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        // listView에 adapter 적용
        listView.setAdapter(adapter);
        ibutton=(Button)findViewById(R.id.btn_search);
        sbutton=(Button)findViewById(R.id.search);
        backbtn=(ImageButton) findViewById(R.id.btn_back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        editText=(EditText)findViewById(R.id.editText_search);
        Handler handler =new Handler(Looper.getMainLooper());


        ibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String line="";
                items.clear();
                String name = editText.getText().toString();
                try {
                    InputStream is = getResources().openRawResource(R.raw.ingredients_v2);
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader buf=new BufferedReader(isr);
                    while((line=buf.readLine())!=null){
                        if(line.toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))) items.add(line);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        });
        // 리스트뷰의 아이템 클릭 이벤트 > 텍스트뷰 바뀜
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(result.contains(items.get(position)+",")){
                    result=result.replace(items.get(position)+",","");
                }
                else result = result + items.get(position)+",";
                resultTV.setText(result);
            }
        });

        sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), API_Ingredient.class);
                intent.putExtra("Ingredient",result);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(vodka_checkbox.isChecked()&& !result.contains("Vodka,")) result += "Vodka,";
        else if (!vodka_checkbox.isChecked()&&result.contains("Vodka,")) result=result.replace("Vodka,","");
        if(gin_checkbox.isChecked()&& !result.contains("Gin,")) result += "Gin,";
        else if (!gin_checkbox.isChecked()&&result.contains("Gin,")) result=result.replace("Gin,","");
        if(rum_checkbox.isChecked()&& !result.contains("Rum,")) result += "Rum,";
        else if (!rum_checkbox.isChecked()&&result.contains("Rum,")) result=result.replace("Rum,","");
        if(whiskey_checkbox.isChecked()&& !result.contains("Whiskey,")) result += "Whiskey,";
        else if (!whiskey_checkbox.isChecked()&&result.contains("Whiskey,")) result=result.replace("Whiskey,","");
        if(tequila_checkbox.isChecked()&& !result.contains("Tequila,")) result += "Tequila,";
        else if (!tequila_checkbox.isChecked()&&result.contains("Tequila,")) result=result.replace("Tequila,","");
        if(brandy_checkbox.isChecked()&& !result.contains("Brandy,")) result += "Brandy,";
        else if (!brandy_checkbox.isChecked()&&result.contains("Brandy,")) result=result.replace("Brandy,","");
        resultTV.setText(result);
    }
}

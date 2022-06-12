package com.hanul.alcoholic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class API_Search_Ingredient extends Fragment implements CompoundButton.OnCheckedChangeListener {


    public static String key = "";
    public static String address = "";
    private ListView listView;
    private Button ibutton,sbutton,cbutton;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.api_search_ingredient, container, false);
        listView = (ListView)view.findViewById(R.id.data);
        // adapter 스타일 선언 및 items 적용
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, items);
        // listView에 adapter 적용
        listView.setAdapter(adapter);
        ibutton=(Button)view.findViewById(R.id.btn_search);
        sbutton=(Button)view.findViewById(R.id.search);
        cbutton=(Button)view.findViewById(R.id.strclear);

        vodka_checkbox = (CheckBox)view.findViewById(R.id.chkVodka);
        gin_checkbox = (CheckBox)view.findViewById(R.id.chkGin);
        rum_checkbox = (CheckBox)view.findViewById(R.id.chkRum);
        whiskey_checkbox = (CheckBox)view.findViewById(R.id.chkWhiskey);
        tequila_checkbox = (CheckBox)view.findViewById(R.id.chkTequila);
        brandy_checkbox = (CheckBox)view.findViewById(R.id.chkBrandy);
        resultTV = (TextView)view.findViewById(R.id.ingredientList);

        vodka_checkbox.setOnCheckedChangeListener(this);
        gin_checkbox.setOnCheckedChangeListener(this);
        rum_checkbox.setOnCheckedChangeListener(this);
        whiskey_checkbox.setOnCheckedChangeListener(this);
        tequila_checkbox.setOnCheckedChangeListener(this);
        brandy_checkbox.setOnCheckedChangeListener(this);

        editText=(EditText)view.findViewById(R.id.editText_search);
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
                Intent intent = new Intent(getContext().getApplicationContext(), API_Ingredient.class);
                intent.putExtra("Ingredient", result);
                startActivity(intent);
            }
        });
        cbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                result="";
                resultTV.setText(result);
                vodka_checkbox.setChecked(false);
                gin_checkbox.setChecked(false);
                rum_checkbox.setChecked(false);
                whiskey_checkbox.setChecked(false);
                tequila_checkbox.setChecked(false);
                brandy_checkbox.setChecked(false);
            }
        });
        return view;
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

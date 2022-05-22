//검색된 재료를 바탕으로 칵테일 검색
package com.hanul.alcoholic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class API_Ingredient extends AppCompatActivity {

    public static String key = "9973533";
    public static String address = "https://www.thecocktaildb.com/api/json/v2/";
    private ListView listView;
    ArrayAdapter adapter;

    // 칵테일 제목을 담을 ArrayList 변수(items) 선언
    ArrayList<String> items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_ingredient);
        Bundle bundle = getIntent().getExtras();
        String ing = bundle.getString("Ingredient");

        listView = (ListView) findViewById(R.id.listView1);
        // adapter 스타일 선언 및 items 적용
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        // listView에 adapter 적용
        listView.setAdapter(adapter);
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                items.clear();
                String urlAddress = address + key + "/filter.php?i=" + removeComma(ing);

                try {
                    URL url = new URL(urlAddress);

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine();
                    while (line != null) {
                        buffer.append(line + "\n");
                        line = reader.readLine();
                    }

                    String jsonData = buffer.toString();
                    if (jsonData.trim().equals("{\"drinks\":\"None Found\"}")) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "칵테일을 찾을 수 없어요!", Toast.LENGTH_SHORT).show();
                            }
                        }, 100);
                    }
                    else {
                        // jsonData를 먼저 JSONObject 형태로 바꾼다.
                        JSONObject obj = new JSONObject(jsonData);
                        // obj의 JSONObject에서 "drinkList"의 JSONArray 추출
                        JSONArray drinkList = (JSONArray) obj.get("drinks");
                        for (int i = 0; i < drinkList.length(); i++) {
                            JSONObject temp = drinkList.getJSONObject(i);
                            String drinkNm = temp.getString("strDrink");
                            items.add(drinkNm);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Button button = findViewById(R.id.back1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // 리스트뷰의 아이템 클릭 이벤트 > 토스트 메시지 띄우기
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), API_Recipe.class);
                intent.putExtra("Drink", (String) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });
    }
    static String removeComma(String str) {
        if (str.endsWith(",")) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }
}

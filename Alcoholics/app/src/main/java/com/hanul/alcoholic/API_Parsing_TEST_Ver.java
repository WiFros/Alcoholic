package com.hanul.alcoholic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SectionIndexer;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class API_Parsing_TEST_Ver extends AppCompatActivity {

    private String key = "9973533";
    private String address = "https://www.thecocktaildb.com/api/json/v2/";
    private ListView listView;
    private Button btnData;
    private EditText editText;
    ArrayAdapter adapter;

    // 영화 제목을 담을 ArrayList 변수(items) 선언
    ArrayList<String> items = new ArrayList<String>();
    public static Context context_api;
    List<String> newFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_parsing_test_ver);

        context_api = this;
        listView = (ListView)findViewById(R.id.listView1);
        //listView.setFastScrollEnabled(true);

        // adapter 스타일 선언 및 items 적용
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, items);

        //###Change Arraylist to List And setAdapter
        //List<String> fruitList = new ArrayList<String>(Arrays.<String>asList(String.valueOf(items)));
        //ListAdapter listAdapter = new ListAdapter(this, fruitList);
        //listAdapter= new ListAdapter(this, fruitList);
        //listView.setAdapter(listAdapter);
        ////###

        // listView에 adapter 적용
        listView.setAdapter(adapter);
        btnData = (Button)findViewById(R.id.btnData);
        editText=(EditText)findViewById(R.id.name);

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        items.clear();
                        String name=editText.getText().toString();
                        String urlAddress = address+key+"/search.php?s="+name;

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

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        // 리스트뷰의 아이템 클릭 이벤트 > 토스트 메시지 띄우기
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = (String)parent.getItemAtPosition(position);
                Toast.makeText(API_Parsing_TEST_Ver.this, data, Toast.LENGTH_SHORT).show();
                newFavorite = Collections.singletonList(data);
                Log.d("BBBBBBBB",data);
                CheckedTextView check = (CheckedTextView)view;
                check.setChecked(!check.isChecked());
                Log.d("CCCCCCCCCCC","CCCCCCCCCCCCCCC");

                if(check.isChecked())
                {
                    try{
                        ((myshelf)myshelf.context_favorite).AddtoFavorite();
                    }catch(NullPointerException e){
                        Log.d("##############", "NULLLPOINTEXCEPTION");
                    }

                }


            }
        });
    }

    class ListAdapter extends ArrayAdapter<String> implements SectionIndexer {

        HashMap<String, Integer> mapIndex;
        String[] sections;
        List<String> fruits;

        public ListAdapter(Context context, List<String> fruitList) {
            super(context, R.layout.activity_fastscroll_list_item, fruitList);

            this.fruits = fruitList;
            mapIndex = new LinkedHashMap<String, Integer>();

            for (int x = 0; x < fruits.size(); x++) {
                String fruit = fruits.get(x);
                String ch = fruit.substring(0, 1);
                ch = ch.toUpperCase(Locale.US);

                // HashMap will prevent duplicates
                mapIndex.put(ch, x);
            }

            Set<String> sectionLetters = mapIndex.keySet();

            // create a list from the set to sort
            ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);

            Log.d("sectionList", sectionList.toString());
            Collections.sort(sectionList);

            sections = new String[sectionList.size()];

            sectionList.toArray(sections);
        }

        public int getPositionForSection(int section) {
            Log.d("section", "" + section);
            return mapIndex.get(sections[section]);
        }

        public int getSectionForPosition(int position) {
            Log.d("position", "" + position);
            return 0;
        }

        public Object[] getSections() {
            return sections;
        }
    }
}


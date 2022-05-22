//레시피 출력 부분
package com.hanul.alcoholic;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Iterator;
import java.util.LinkedHashMap;

public class API_Recipe extends AppCompatActivity {
    String jsonData;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_recipe);
        Bundle bundle=getIntent().getExtras();
        getSupportActionBar().setTitle(bundle.getString("Drink")+" Recipe");
        String text=bundle.getString("Drink");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String urlAddress = API_Search_Name.address + API_Search_Name.key + "/search.php?s=" + text;

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
                    jsonData = buffer.toString();
                    reader.close();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
///////////////////////////////////////////

                String strDrink = null;
                String strTags = null;
                String strCategory = null;
                String strAlcoholic = null;
                String strGlass = null;
                String strInstructions = null;
                String strDrinkThumb = null;
                String[] strIngredient = new String[15];
                String[] strMeasure = new String[15];
                ArrayList<LinkedHashMap<String,String>> mhashmap=new ArrayList<>();
                LinkedHashMap<String,String> hashMap=new LinkedHashMap<>();
                try {
                    JSONArray jarray = new JSONObject(jsonData).getJSONArray("drinks");
                    for (int i = 0; i < 1; i++) {
                        JSONObject jObject = jarray.getJSONObject(i);
                        hashMap.put("strDrink",jObject.optString("strDrink"));
                        hashMap.put("strTags",jObject.optString("strTags"));
                        hashMap.put("strCategory",jObject.optString("strCategory"));
                        hashMap.put("strAlcoholic",jObject.optString("strAlcoholic"));
                        hashMap.put("strGlass",jObject.optString("strGlass"));
                        hashMap.put("strInstructions",jObject.optString("strInstructions"));
                        hashMap.put("strDrinkThumb",jObject.optString("strDrinkThumb"));
                        for(int j=1;j<=strIngredient.length;j++){
                            hashMap.put("strIngredient"+j,jObject.optString("strIngredient"+j));
                        }
                        for(int j=1;j<=strMeasure.length;j++){
                            hashMap.put("strMeasure"+j,jObject.optString("strMeasure"+j));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = (TextView) findViewById(R.id.text);
                        for(Iterator linkitr=hashMap.values().iterator();linkitr.hasNext();){
                            textView.append(linkitr.next()+"\n");
                        }
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Button button = findViewById(R.id.back);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        }).start();
    }
}

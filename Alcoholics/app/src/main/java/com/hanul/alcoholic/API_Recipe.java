package com.hanul.alcoholic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class API_Recipe extends AppCompatActivity {
    String jsonData;
    Handler handler = new Handler();
    LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_recipe);
        Bundle bundle = getIntent().getExtras();
        getSupportActionBar().setTitle(bundle.getString("Drink") + " Recipe");
        String text = bundle.getString("Drink");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String urlAddress = API_Ingredient.address + API_Ingredient.key + "/search.php?s=" + text;

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

                String[] strIngredient = new String[15];
                String[] strMeasure = new String[15];

                try {
                    JSONArray jarray = new JSONObject(jsonData).getJSONArray("drinks");
                    for (int i = 0; i < 1; i++) {
                        JSONObject jObject = jarray.getJSONObject(i);
                        hashMap.put("strDrink", jObject.optString("strDrink"));
                        hashMap.put("strTags", jObject.optString("strTags"));
                        hashMap.put("strCategory", jObject.optString("strCategory"));
                        hashMap.put("strAlcoholic", jObject.optString("strAlcoholic"));
                        hashMap.put("strGlass", jObject.optString("strGlass"));
                        hashMap.put("strInstructions", jObject.optString("strInstructions"));
                        hashMap.put("strDrinkThumb", jObject.optString("strDrinkThumb"));
                        for (int j = 1; j <= strIngredient.length; j++) {
                            hashMap.put("strIngredient" + j, jObject.optString("strIngredient" + j));
                        }
                        for (int j = 1; j <= strMeasure.length; j++) {
                            hashMap.put("strMeasure" + j, jObject.optString("strMeasure" + j));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getTranslation();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = (TextView) findViewById(R.id.text);
                        for (Iterator linkitr = hashMap.values().iterator(); linkitr.hasNext(); ) {
                            textView.append(linkitr.next() + "\n");
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

    public void getTranslation() {

        String clientId = "VFcHILW7PKggGd9sO9Et";
        String clientSecret = "c634pRyxvp";

        try {
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=" + "en" + "&target=" + "ko" + "&text=" + hashMap.get("strInstructions");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            String s = response.toString();
            JSONObject js = new JSONObject(s);
            JSONObject jo = (JSONObject)js.get("message");
            JSONObject jstr = (JSONObject)jo.get("result");
            hashMap.put("strInstructions", jstr.optString("translatedText"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
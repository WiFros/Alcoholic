package com.hanul.alcoholic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanul.alcoholic.MainActivity;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class API_Recipe extends AppCompatActivity {
    String jsonData;
    Handler handler=new Handler();
    ImageView image;
    private Button favoriteBtn;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    ArrayList<String> favoriteInfo = new ArrayList<String>();
    LinkedHashMap<String,Object> hashMap=new LinkedHashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_recipe);
        Bundle bundle=getIntent().getExtras();
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


                String[] strIngredient = new String[15];
                String[] strMeasure = new String[15];


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
                        getTranslation();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String infoStr=new String();
                        String ingrStr=new String();
                        TextView name = (TextView) findViewById(R.id.nickname);
                        TextView info = (TextView) findViewById(R.id.timeline);
                        TextView glass = (TextView) findViewById(R.id.reply_area);
                        TextView ingredient = (TextView) findViewById(R.id.ingredient);
                        TextView recipe = (TextView) findViewById(R.id.post);

                        name.setText((String)hashMap.get("strDrink"));

                        infoStr+=hashMap.get("strCategory")+" / "+hashMap.get("strAlcoholic");
                        if(!hashMap.get("strTags").toString().trim().equals("null"))
                            infoStr=hashMap.get("strTags")+" / "+infoStr;
                        info.setText(infoStr);

                        ImageView imageView= findViewById(R.id.detailRecipeImg);
                        Glide.with(getApplicationContext()).load(hashMap.get("strDrinkThumb")).into(imageView);

                        glass.setText(hashMap.get("strGlass").toString());

                        recipe.setText(hashMap.get("strInstructions")+" 뿅★");

                        for(int i=1;i<=15;i++){
                            if(hashMap.get("strIngredient"+i).toString().trim().equals("null")) break;
                            else if(!hashMap.get("strMeasure"+i).toString().trim().equals("null"))
                                ingrStr+=hashMap.get("strIngredient"+i)+" "+hashMap.get("strMeasure"+i)+"\n";
                            else
                                ingrStr+=hashMap.get("strIngredient"+i)+"\n";
                        }
                        ingredient.setText(ingrStr.trim());
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ImageButton button = findViewById(R.id.btn_back);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        }).start();

        // 선택한 레시피의 nickname 즐겨찾기 리스트에 저장
        TextView cocktailName = findViewById(R.id.nickname);
        favoriteBtn = (Button)findViewById(R.id.favoriteBtn);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(API_Recipe.this, String.valueOf(cocktailName.getText()), Toast.LENGTH_SHORT).show();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                final FirebaseUser user = mAuth.getCurrentUser();
                databaseReference = firebaseDatabase.getReference("alcoholic");
                String recipe_key = databaseReference.child("Favorite").push().getKey();

                Map<String,Object> recipeValue = hashMap;
                Map<String,Object> recipeUpdates = new HashMap<>();
                //맵핑된 해시 테이블 오브젝트를 firebase에 업데이트
                recipeUpdates.put("Favorite/"+user.getUid()+"/"+recipeValue.get("strDrink"),recipeValue);
                databaseReference.updateChildren(recipeUpdates);
            }
        });
    }

    public void getTranslation() {

        String clientId = "";
        String clientSecret = "";

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

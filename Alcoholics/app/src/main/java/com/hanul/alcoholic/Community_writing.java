package com.hanul.alcoholic;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//activity
public class Community_writing extends AppCompatActivity {
    private String title;
    private String contents;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //버튼 클릭시 화면에서 입력한 내용 받아와서 파이어베이스에 올리기
        Button btn_upload = (Button) findViewById(R.id.button_upload);
        Button btn_back = (Button) findViewById(R.id.button_back);



    }
}

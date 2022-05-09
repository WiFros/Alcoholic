package com.hanul.alcoholic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class Community_PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_post);

        Button enter = (Button)findViewById(R.id.enter);
        Intent intent = getIntent(); //화면 전환 시 community_main 에서 인텐트 받아옴
        //받아오는 데이터 : 클릭한 리스트 정보
        //nickname, timeline, post, reply_list, 내용 받아와서 표시하기

        //버튼 클릭했을 때 textView에 있는 내용 입력하기 -- db 데이터 저장
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText reply_input = findViewById(R.id.reply_input);
                String text = reply_input.getText().toString();

                // intent에 넣는지 파이어베이스에 넣는지?
            }
        });

        // 각 뷰에 해당하는 정보를 intent에서 찾아서 setText 하는 과정 -- db에서 바로?
        TextView nickname = findViewById(R.id.nickname);
        TextView timeline = findViewById(R.id.timeline);
        TextView post = findViewById(R.id.post);
        TextView reply_list = findViewById(R.id.reply_list);

        nickname.setText(intent.getStringExtra("nickname"));
        timeline.setText(intent.getStringExtra("timeline"));
        post.setText(intent.getStringExtra("post"));
        reply_list.setText(intent.getStringExtra("reply_list"));



    }



}

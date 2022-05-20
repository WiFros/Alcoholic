package com.hanul.alcoholic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/* 바꿔야할 부분??
* 현재 bundle로 메인 community에서 값 받아옴 -> 선택된 값을 firebase에서 찾아서 나머지 데이터까지 띄워야함
*
* */
public class Community_post extends AppCompatActivity {
    private TextView nickname;
    private TextView timeline;
    private TextView post;
    private TextView reply_list;

    private String get_nickname;
    private String get_timeline;
    private String get_post;
    private String get_reply_list;

    private ImageButton imgButton;
    private Button btn_enter;
    private EditText reply;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_community_post);

        //bundle에 값이 있다면 각 key값 불러서 setText()
        //**** firebase에서 가져온 값으로 수정해야 함
        get_nickname = getIntent().getStringExtra("nickname");
        nickname = findViewById(R.id.nickname);
        nickname.setText(get_nickname);

        get_timeline = getIntent().getStringExtra("date");
        timeline = findViewById(R.id.timeline);
        timeline.setText(get_timeline);

        get_post = getIntent().getStringExtra("post");
        post = findViewById(R.id.post);
        post.setText(get_post);

        //replylist - firebase에서 값 가져오기
        get_reply_list = "";
        reply_list = findViewById(R.id.reply_list);
        reply_list.setText(get_reply_list);


        btn_enter = findViewById(R.id.enter);
        reply = findViewById(R.id.reply_input);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt;
                txt = reply.getText().toString();
                Toast.makeText(getApplicationContext(), txt + "댓글을 입력했습니다.", Toast.LENGTH_SHORT).show();
                //firebase에 댓글 내용 업데이트하기?

                //reply 리스트에 올린 내용 업데이트하고, 텍스트 비우기

            }
        });

    }


}

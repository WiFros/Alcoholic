package com.hanul.alcoholic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

/* 바꿔야할 부분??
* 현재 bundle로 메인 community에서 값 받아옴 -> 선택된 값을 firebase에서 찾아서 나머지 데이터까지 띄워야함
*
* */
public class Community_post extends AppCompatActivity {
    private TextView nickname;
    private TextView timeline;
    private TextView content;
    private TextView reply_list;

    private String get_nickname;
    private String get_timeline;
    private String get_post;
    private String get_reply_list;

    private Button btn_enter;
    private Button btn_report;
    private ImageButton btn_back;
    private EditText reply;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private final HashMap<String ,Object> data = new HashMap<>();
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_community_post);
        Intent intent = getIntent();
        String key  = intent.getExtras().getString("key");
        //firebase 경로 지정

        databaseReference = firebaseDatabase.getReference("alcoholic/Post");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 DB에 데이터를 받아옴
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //반복문으로 데이터 리스트 추출
                    Post post = snapshot.getValue(Post.class);
                    if(post.getKey().toString().equals(key)){
                        nickname = findViewById(R.id.nickname);
                        nickname.setText(post.getAuthor());

                        timeline = findViewById(R.id.timeline);
                        timeline.setText(post.getDate());

                        content = findViewById(R.id.post);
                        content.setText(post.getBody());

                        //replylist - firebase에서 값 가져오기
                        get_reply_list = "";
                        reply_list = findViewById(R.id.reply_list);
                        reply_list.setText(get_reply_list);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오던 중 에러 발생 시
                Toast.makeText(getApplicationContext(), "데이터 수신 에러", Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println("테스트2 : "+String.valueOf(data.get("author")));

        btn_enter = findViewById(R.id.enter);
        reply = findViewById(R.id.reply_input);

        //댓글입력
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt;
                txt = reply.getText().toString();
                Toast.makeText(getApplicationContext(), txt + "댓글을 입력했습니다.", Toast.LENGTH_SHORT).show();
                //writeNewComment();
                //
            }
        });

        //신고기능
        btn_report = findViewById(R.id.btn_report);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "신고가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                //신고 기능?

            }
        });

        //뒤로가기
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });



    }


}





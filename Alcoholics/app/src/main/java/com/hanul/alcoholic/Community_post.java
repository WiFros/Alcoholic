package com.hanul.alcoholic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;
import com.hanul.alcoholic.Registe.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private TextView reply_area;

    //댓글 띄울 recycle view id : reply_recycleView

    private String get_nickname;
    private String currentUser;

    private Button btn_enter;
    private Button btn_report;
    private Button btn_comment_del;
    private ImageButton btn_back;
    private Button btn_del;
    private String key;
    private EditText reply;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Comment> arrayList;
    private ArrayList<Comment> currentUserComment;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = mAuth.getCurrentUser();

    private final HashMap<String ,Object> data = new HashMap<>();
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_community_post);
        Intent intent = getIntent();
        key  = intent.getExtras().getString("key");//post 의 키
        //firebase 경로 지정

        recyclerView = (RecyclerView) findViewById(R.id.comment_recycleView);//id 연결
        recyclerView.setHasFixedSize(true);//기존 성능강화
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);//유저객체 담기
        recyclerView.scrollToPosition(0);
        arrayList = new ArrayList<>();
        currentUserComment = new ArrayList<>();

        databaseReference = firebaseDatabase.getReference("alcoholic");
        databaseReference.
                child("USerAccount").
                child(user.getUid()).
                child("nickName").
                get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "유저 데이터 읽기에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            currentUser = String.valueOf(task.getResult().getValue());
                        }
                    }
                });

        databaseReference = firebaseDatabase.getReference("alcoholic/Post");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                //파이어베이스 DB에 데이터를 받아옴
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //반복문으로 데이터 리스트 추출
                    Post post = snapshot.getValue(Post.class);
                    if(post.getKey().toString().equals(key)){
                        nickname = findViewById(R.id.nickname);
                        nickname.setText(post.getAuthor());
                        get_nickname = post.getAuthor();

                        timeline = findViewById(R.id.timeline);
                        timeline.setText(post.getDate());

                        content = findViewById(R.id.post);
                        content.setText(post.getBody());

                        btn_del = findViewById(R.id.btn_delete);
                        if (!user.getUid().equals(post.getUid())) {//현재유저랑 글쓴이가 다르면
                            btn_del.setVisibility(View.INVISIBLE);
                            btn_del.setEnabled(false);
                        } else {
                            btn_del.setVisibility(View.VISIBLE); // 같으면
                            btn_del.setEnabled(true);
                        }

                        //replylist - firebase에서 값 가져오기
                        //Comment comment = snapshot.child(key).child("Comment").getValue(Comment.class);
                        for(DataSnapshot commentSnapshot : dataSnapshot.child(key).child("Comment").getChildren()){
                            if(commentSnapshot.hasChildren()) {
                                Comment comment = commentSnapshot.getValue(Comment.class);
                                //Toast.makeText(getApplicationContext(),comment.getBody() , Toast.LENGTH_SHORT).show();
                                arrayList.add(comment);
                            }
                            else {
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        //Toast.makeText(getApplicationContext(),snapshot.child(key).child("Comment") , Toast.LENGTH_SHORT).show();

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오던 중 에러 발생 시
                Toast.makeText(getApplicationContext(), "데이터 수신 에러", Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new CommentAdapter(arrayList,getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        System.out.println("테스트2 : "+String.valueOf(data.get("author")));

        btn_enter = findViewById(R.id.enter);
        reply = findViewById(R.id.reply_input);

        //댓글입력
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt;
                txt = reply.getText().toString();
                if (!TextUtils.isEmpty(txt)) {
                    Toast.makeText(getApplicationContext(), "댓글을 입력했습니다.", Toast.LENGTH_SHORT).show();
                    writeNewComment(key,txt,false);
                    reply.setText("");
                }

            }
        });
        //댓글 삭제 (좀...비효율적)

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
        //게시글 삭제기능
        btn_del = findViewById(R.id.btn_delete);
        databaseReference = firebaseDatabase.getReference("alcoholic/Post/"+key);

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder oDialog = new AlertDialog.Builder(Community_post.this);
                oDialog.setMessage("게시글을 삭제할까요?")
                        .setPositiveButton("아니오", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {}
                        })
                        .setNeutralButton("예", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                databaseReference.removeValue();
                                Toast.makeText(getApplicationContext(),"게시글이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        })
                        .setCancelable(false).show();

            }
        });


    }

    private void writeNewComment(String nowPost,String body,boolean mode) {
        databaseReference = firebaseDatabase.getReference("alcoholic");
        String comment_key = databaseReference.child("Post").child("Comment").push().getKey();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date time = new Date();
        String timeSting = format1.format(time);
        Comment comment = new Comment(comment_key,key,body,currentUser,timeSting,comment_key,comment_key,user.getUid());

        Map<String,Object> commentValue = comment.toMap();
        Map<String,Object> chileUpdates = new HashMap<>();
        //맵핑된 해시 테이블 오브젝트를 firebase에 업데이트
        chileUpdates.put("Post/"+key+"/Comment/"+comment_key,commentValue);
        chileUpdates.put("Post-comment/"+key+"/"+comment_key,commentValue);

        databaseReference.updateChildren(chileUpdates);
    }
}
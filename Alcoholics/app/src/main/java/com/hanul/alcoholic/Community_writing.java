package com.hanul.alcoholic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hanul.alcoholic.Registe.LoginActivity;
import com.hanul.alcoholic.Registe.UserAccount;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//activity
public class Community_writing extends AppCompatActivity {
    private static final int GET_GALLERY_IMAGE = 200;; //갤러리 선택용도?
    private String title;
    private String contents;
    private String writer;
    private int comment;
    private Date date;
    private ValueEventListener postListener;
    private TextView textViewTitle;
    private TextView textViewContent;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    Button btn_upload;
    Button btn_back;
    //ImageButton btn_img;
    //ImageButton btn_gal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_post);
        //버튼 클릭시 화면에서 입력한 내용 받아와서 파이어베이스에 올리기
        btn_upload = (Button) findViewById(R.id.button_upload);
        btn_back = (Button) findViewById(R.id.button_back);
        //btn_img = (ImageButton) findViewById(R.id.imageButton_picture);
        //btn_gal = (ImageButton) findViewById(R.id.imageButton_gallery);

        //작성자 이름 받아오기
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
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
                            writer = String.valueOf(task.getResult().getValue());
                            //Toast.makeText(getApplicationContext(), writer, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btn_upload.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {


                //Toast.makeText(getApplicationContext(), "글 업로드 됨", Toast.LENGTH_SHORT).show();
                //제목 입력
                textViewTitle = findViewById(R.id.editTextTitle);
                title = textViewTitle.getText().toString();
                //내용 입력
                textViewContent = findViewById(R.id.editTextContents);
                contents = textViewContent.getText().toString();
                //제목이나 글 안썼을 경우 예외처리
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(contents)) {
                    android.app.AlertDialog.Builder caution = new android.app.AlertDialog.Builder(Community_writing.this);
                    caution.setMessage("제목이나 내용을 확인하세요.");
                    caution.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    }).show();
                }
                else {
                    writeNewPost(user.getUid(),writer,title,contents);
                    finish();
                }
                // firebase에 글 저장하기


            }
        });

        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
/*

        btn_gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "이미지 업로드", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,GET_GALLERY_IMAGE); //될지 모르겠음
                //이미지 선택 후 실행될 이벤트 구현해야 함..
            }
        });
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "카메라 실행", Toast.LENGTH_SHORT).show();
                //이미지 선택 후 실행될 이벤트 구현해야 함..
            }
        });
*/



    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder oDialog = new AlertDialog.Builder(this);
        oDialog.setMessage("글 작성을 취소할까요?")
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
                        //Toast.makeText(getApplicationContext(), "글쓰기 취소됨", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setCancelable(false).show();

    }
    private void writeNewPost(String userId,String userName, String title,String body){
        String key = databaseReference.child("Post").push().getKey();
        Comment comment = new Comment();
        Post post = new Post(userId,userName,title,body);
        //작성 시간 저장
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date time = new Date();
        String timeSting = format1.format(time);
        post.setDate(timeSting);
        post.setKey(key);
        post.setComment(comment);
        post.setStarCount(0);
        post.setCommentCount(0);
        post.setProfile(null);
        //post.setComment(null);
        //입력 데이터를 해시맵에 맵핑
        Map<String,Object> postValue = post.toMap();
        Map<String,Object> chileUpdates = new HashMap<>();
        //맵핑된 해시 테이블 오브젝트를 firebase에 업데이트
        chileUpdates.put("/Post/"+key,postValue);
        chileUpdates.put("/User-post/"+userId+"/"+key,postValue);
        databaseReference.updateChildren(chileUpdates);
    }
}

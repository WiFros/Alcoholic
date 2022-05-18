package com.hanul.alcoholic;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//activity
public class Community_writing extends AppCompatActivity {
    private static final int GET_GALLERY_IMAGE = 200;; //갤러리 선택용도?
    private String title;
    private String contents;

    private TextView textViewTitle;
    private TextView textViewContent;

    Button btn_upload;
    Button btn_back;
    ImageButton btn_img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //버튼 클릭시 화면에서 입력한 내용 받아와서 파이어베이스에 올리기
        btn_upload = (Button) findViewById(R.id.button_upload);
        btn_back = (Button) findViewById(R.id.button_back);
        btn_img = (ImageButton) findViewById(R.id.imageButton_picture);

        btn_upload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "글 업로드 됨", Toast.LENGTH_SHORT).show();
                textViewTitle = findViewById(R.id.editTextTitle);
                title = textViewTitle.getText().toString();
                textViewContent = findViewById(R.id.editTextContents);
                contents = textViewContent.getText().toString();
                // firebase에 글 저장하기

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "글쓰기 취소됨", Toast.LENGTH_SHORT).show();
                //pop-up 메시지로 다시 확인하는 기능 넣기 - 추가적인 부분
                onBackPressed();
            }
        });

        btn_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "이미지 업로드", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,GET_GALLERY_IMAGE); //될지 모르겠음
                //이미지 선택 후 실행될 이벤트 구현해야 함..
            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

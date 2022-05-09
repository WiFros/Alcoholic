package com.hanul.alcoholic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

public class Community_MainActivity extends AppCompatActivity {
    ListView listView;
    MyListAdapter myListAdapter;
    ArrayList<List_item> list_itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main);

        Intent intent = getIntent(); //화면 전환 시 인텐트 받아옴
        // 받아오는 데이터 : 아이디?

        /* 화면에 표시될 리스트 뷰 - id = main_list*/
        listView = (ListView)findViewById(R.id.main_list);

        list_itemArrayList = new ArrayList<List_item>();
        // list에 들어갈 내용 파이어베이스에서 불러오기
        list_itemArrayList.add(
                new List_item("보라돌이", "제목1", new Date(System.currentTimeMillis()), "내용1", R.drawable.ic_launcher_foreground));
        list_itemArrayList.add(
                new List_item("뚜비", "제목2", new Date(System.currentTimeMillis()), "내용2", R.mipmap.ic_launcher));
        list_itemArrayList.add(
                new List_item("나나", "제목3", new Date(System.currentTimeMillis()), "내용3", R.mipmap.ic_launcher));
        list_itemArrayList.add(
                new List_item("뽀", "제목4", new Date(System.currentTimeMillis()), "내용4", R.mipmap.ic_launcher));
        list_itemArrayList.add(
                new List_item("햇님", "제목5", new Date(System.currentTimeMillis()), "내용5", R.mipmap.ic_launcher));

        myListAdapter = new MyListAdapter(Community_MainActivity.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);
    }

}

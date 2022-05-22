package com.hanul.alcoholic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* 들어가야 할 내용
 * 1. Firebase에서 content/nickname/timeline/comment 갯수 불러와서 setText()
 * 2. 선택한 listView 항목 - Community_post로 이동해서 fragment 띄우기
 *
 * */
public class Community_main extends Fragment {
    private Button btn_search;
    private EditText editText_search;
    private ListView listView;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Post> arrayList;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_board, container, false);
        btn_search = (Button)view.findViewById(R.id.btn_search);
        listView = (ListView)view.findViewById(android.R.id.list);
        editText_search = (EditText)view.findViewById(R.id.editText_search);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), Community_writing.class);
                startActivity(intent);
            }
        });
        //데이터 입력받을 adapter 생성
        //fragment에서 this사용 안됨 -> Activity의 참조 획득이 가능한 getActivity() 함수 사용
        //ListView 아이템에 표시될 사용자 데이터 정의
        //***** listItems에 들어갈 내용 : 현재 임시로 적어둠 -> firebase에서 데이터 가져오기 (위에 Post 클래스 사용?)


        recyclerView = (RecyclerView)view.findViewById(R.id.board_recycleView);//id 연결
        recyclerView.setHasFixedSize(true);//기존 성능강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);//유저객체 담기
        recyclerView.scrollToPosition(0);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance(); // 디비 연걸
        databaseReference = database.getReference("alcoholic/Post"); //디비 테이블 명 연결
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 DB에 데이터를 받아옴
                arrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //반복문으로 데이터 리스트 추출
                    Post post = snapshot.getValue(Post.class);
                    arrayList.add(post); //담은 데이터를 배열 리스트에 넣고 라이클뷰로 보낼 준비
                    System.out.println("리스트 : " + post.getDate());
                }
                adapter.notifyDataSetChanged();//리스트 저장 및 새로고침
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오던 중 에러 발생 시
                Toast.makeText(getContext(), "데이터 수신 에러", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new BoardAdapter(arrayList,getContext());
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // editText에 입력된 값 받아와서 변수 search_query에 저장(done)
                // firebase에 search_query 검색해 리스트뷰에 띄우는 기능 구현 필요
                // editText id : editText_search
                String search_query = editText_search.getText().toString();
                Toast.makeText(getContext(), "button enter", Toast.LENGTH_SHORT).show();

            }

        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}

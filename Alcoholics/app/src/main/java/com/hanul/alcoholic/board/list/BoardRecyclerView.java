package com.hanul.alcoholic.board.list;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hanul.alcoholic.R;

import java.util.ArrayList;

public class BoardRecyclerView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<BoardUser> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_main);

        recyclerView = findViewById(R.id.board_recycleView);//id 연결
        recyclerView.setHasFixedSize(true);//기존 성능강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);//유저객체 담기
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance(); // 디비 연걸

        databaseReference = database.getReference("alcoholic/User"); //디비 테이블 명 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 DB에 데이터를 받아옴
                arrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    BoardUser user = snapshot.getValue(BoardUser.class);
                    arrayList.add(user); //담은 데이터를 배열 리스트에 넣고 라이클뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오던 중 에러 발생 시
                Log.e("BoardRecyclerViee",String.valueOf(error.toException()));
            }
        });
        adapter = new BoardAdapter(arrayList,this);
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결

    }
}

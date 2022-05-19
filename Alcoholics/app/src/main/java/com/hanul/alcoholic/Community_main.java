package com.hanul.alcoholic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

/* 리스트 뷰에 들어갈 내용 입력받기 위해 클래스 만들어둠 */
class Post {
    private String name;
    private String nickname;
    private String content;
    private int comments;
    private String date;

    Post(String name,String nickname,String content, int comments,String date){
        this.name = name;
        this.nickname = nickname;
        this.content = content;
        this.comments = comments;
        this.date = date;
    }
    Post(String nickname, String content, int comments, String date) {
        this.nickname = nickname;
        this.content = content;
        this.comments = comments;
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }
    public String getContent() {
        return content;
    }
    public int getComments() {
        return comments;
    }
    public String getDate() {
        return date;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
/* 들어가야 할 내용
* 1. Firebase에서 content/nickname/timeline/comment 갯수 불러와서 setText()
* 2. 선택한 listView 항목 - Community_post로 이동해서 fragment 띄우기
*
* */
public class Community_main extends Fragment {
    private Button btn_search;
    private EditText editText_search;
    private ListView listView;

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
        String[] listItems = {"sample one", "sample two", "sample three"};
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(listViewAdapter);
        setHasOptionsMenu(true);

        //listView 클릭했을 때 - 클릭한 게시글로 fragment 이동
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle data = new Bundle();
                //Post 클래스의 내용 활용하기 sample -- **** firebase 값 가져오기
                //Bundle 통해 값 전달하기
                Post post = new Post("nick", "sample", 0, "20220509");
                data.putString("nickname", post.getNickname());
                data.putString("post", post.getContent());
                data.putInt("comments", post.getComments());
                data.putString("date", post.getDate());

                //값을 넘길 fragment 선언하고 전달
                //FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.?);

                //Community_post chosen_post = new Community_post();
                //chosen_post.setArguments(data);
                //transaction.replace(R.id.frameLayout, chosen_post);
                //transaction.addToBackStack(null);
                //transaction.commit();

                Intent intent = new Intent(getContext().getApplicationContext(), Community_post.class);
                intent.putExtra("nickname", data.getString("nickname"));
                intent.putExtra("post", data.getString("post"));
                intent.putExtra("date", data.getString("date"));
                startActivity(intent);

                //for test
                Toast.makeText(getContext(), data.getString("post")+" success", Toast.LENGTH_SHORT).show();

            }
        });


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

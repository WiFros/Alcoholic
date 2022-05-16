package com.hanul.alcoholic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/* 리스트 뷰에 들어갈 내용 입력받기 위해 클래스 만들어둠 */
class Post {
    private String nickname;
    private String content;
    private int comments;
    private String date;

    Post() {}
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_board, container, false);
        //ListView 아이템에 표시될 사용자 데이터 정의
        //***** listItems에 들어갈 내용 : firebase에서 데이터 가져오기 (위에 Post 클래스 사용?)
        String[] listItems = {"sample one", "sample two", "sample three"};


        ListView listView = (ListView)view.findViewById(R.id.main_list);
        //데이터 입력받을 adapter 생성
        //fragment에서 this사용 안됨 -> Activity의 참조 획득이 가능한 getActivity() 함수 사용
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
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Community_post chosen_post = new Community_post();
                chosen_post.setArguments(data);
                transaction.replace(R.id.postContext, chosen_post);
                transaction.commit();


            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}

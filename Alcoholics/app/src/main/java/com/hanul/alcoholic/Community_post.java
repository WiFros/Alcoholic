package com.hanul.alcoholic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/* 바꿔야할 부분??
* 현재 bundle로 메인 community에서 값 받아오는데- firebase에서 값 가져올건지?
*
* */
public class Community_post extends Fragment {
    private View view;

    private TextView nickname;
    private TextView timeline;
    private TextView post;
    private TextView reply_list;

    private String get_nickname;
    private String get_timeline;
    private String get_post;
    private String get_reply_list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_community_post, container, false);
        if (getArguments() != null) {
            //bundle에 값이 있다면 각 key값 불러서 setText()
            get_nickname = getArguments().getString("nickname");
            nickname = view.findViewById(R.id.nickname);
            nickname.setText(get_nickname);

            get_timeline = getArguments().getString("date");
            timeline = view.findViewById(R.id.timeline);
            timeline.setText(get_timeline);

            get_post = getArguments().getString("post");
            post = view.findViewById(R.id.post);
            post.setText(get_post);

            //replylist - firebase에서 값 가져오기
            get_reply_list = "";
            reply_list = view.findViewById(R.id.reply_list);
            reply_list.setText(get_reply_list);

        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}

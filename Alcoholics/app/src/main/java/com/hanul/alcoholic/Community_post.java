package com.hanul.alcoholic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/* 바꿔야할 부분??
* 현재 bundle로 메인 community에서 값 받아옴 -> 선택된 값을 firebase에서 찾아서 나머지 데이터까지 띄워야함
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

    private ImageButton imgButton;
    private Button btn_enter;
    private EditText reply;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_community_post, container, false);
        if (getArguments() != null) {
            //bundle에 값이 있다면 각 key값 불러서 setText()
            //**** firebase에서 가져온 값으로 수정해야 함
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

            imgButton = view.findViewById(R.id.imageButton);
            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "게시글에 공감하셨습니다.", Toast.LENGTH_SHORT).show();
                    //갤러리에서 이미지 가져오기 - 액티비티로 구현해야 하거나 삭제하거나 ><
                    //게시글 좋아요로 버튼 변경 - 팝업메시지 추가하기

                }
            });

            btn_enter = view.findViewById(R.id.enter);
            reply = view.findViewById(R.id.reply_input);
            btn_enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String txt;
                    txt = reply.getText().toString();
                    Toast.makeText(getContext(), txt + "댓글을 입력했습니다.", Toast.LENGTH_SHORT).show();
                    //firebase에 댓글 내용 업데이트하기?

                    //reply 리스트에 올린 내용 업데이트하고, 텍스트 비우기

                }
            });

        }


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

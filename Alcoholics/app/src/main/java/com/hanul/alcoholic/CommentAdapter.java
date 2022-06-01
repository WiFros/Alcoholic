package com.hanul.alcoholic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CustomViewHolder> {

    private ArrayList<Comment> arrayList;
    private Context context;
    private String uid;
    private int pos;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    public CommentAdapter(ArrayList<Comment> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_comment_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String current = arrayList.get(position).getUid();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("alcoholic/Post/"+arrayList.get(position).getBoardId()+"/Comment/"+current);
        holder.cm_author.setText(arrayList.get(position).getAuthor());
        holder.cm_body.setText(arrayList.get(position).getBody());
        holder.cm_date.setText(arrayList.get(position).getDate());
        if (!user.getUid().equals(arrayList.get(position).getAuthorId())) {//현재유저랑 글쓴이가 다르면
            holder.btn_del.setVisibility(View.INVISIBLE);
            holder.btn_del.setEnabled(false);
        } else {
            holder.btn_del.setVisibility(View.VISIBLE); // 같으면
            holder.btn_del.setEnabled(true);
        }
        pos = holder.getAbsoluteAdapterPosition();
        final CustomViewHolder customViewHolder = (CustomViewHolder) holder;
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 아이템 클릭했을때
                databaseReference.removeValue();
            }
        });
    }
    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView cm_body;
        TextView cm_author;
        TextView cm_date;
        Button btn_del;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cm_author = itemView.findViewById(R.id.cm_author);
            this.cm_body = itemView.findViewById(R.id.cm_body);
            this.cm_date = itemView.findViewById(R.id.cm_date);
            this.btn_del = itemView.findViewById(R.id.btn_comment_del);
        }
    }
}

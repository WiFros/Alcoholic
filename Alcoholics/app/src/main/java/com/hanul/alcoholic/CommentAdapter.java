package com.hanul.alcoholic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CustomViewHolder> {

    private ArrayList<Comment> arrayList;
    private Context context;
    private String uid;
    private int pos;
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
        holder.cm_author.setText(arrayList.get(position).getAuthor());
        holder.cm_body.setText(arrayList.get(position).getBody());
        holder.cm_date.setText(arrayList.get(position).getDate());
        pos = holder.getAbsoluteAdapterPosition();
        final CustomViewHolder customViewHolder = (CustomViewHolder) holder;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 아이템 클릭했을때
                //Context context = view.getContext();
                //Intent intent = new Intent(view.getContext(),Community_post.class);
                //intent.putExtra("key",arrayList.get(customViewHolder.getAdapterPosition()).getKey());
                //context.startActivity(intent);
                //Toast.makeText(context, arrayList.get(customViewHolder.getAdapterPosition()).getKey() + "게시물 입니다.", Toast.LENGTH_SHORT).show();

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
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cm_author = itemView.findViewById(R.id.cm_author);
            this.cm_body = itemView.findViewById(R.id.cm_body);
            this.cm_date = itemView.findViewById(R.id.cm_date);
        }
    }
}

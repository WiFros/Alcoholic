package com.hanul.alcoholic;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.CustomViewHolder> {

    private ArrayList<Post> arrayList;
    private Context context;
    private String uid;
    private int pos;
    public BoardAdapter(ArrayList<Post> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_title.setText(arrayList.get(position).getAuthor());
        holder.tv_author.setText(arrayList.get(position).getTitle());
        holder.tv_date.setText(arrayList.get(position).getDate());
        pos = holder.getAbsoluteAdapterPosition();
        final CustomViewHolder customViewHolder = (CustomViewHolder) holder;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 아이템 클릭했을때
                Context context = view.getContext();
                Intent intent = new Intent(view.getContext(),Community_post.class);
                intent.putExtra("key",arrayList.get(customViewHolder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_author;
        TextView tv_date;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_author = itemView.findViewById(R.id.tv_author);
            this.tv_date = itemView.findViewById(R.id.tv_date);
        }
    }
}

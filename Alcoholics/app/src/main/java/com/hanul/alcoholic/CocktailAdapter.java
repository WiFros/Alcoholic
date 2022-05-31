package com.hanul.alcoholic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.CustomViewHolder> {

    private ArrayList<Cocktail> arrayList;
    private Context context;
    private String uid;
    private int pos;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    public CocktailAdapter(ArrayList<Cocktail> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_favorite_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_cockname.setText(arrayList.get(position).getStrDrink());

        Glide.with(holder.itemView).load(arrayList.get(position).getStrDrinkThumb()).into(holder.im_profile);
        pos = holder.getAbsoluteAdapterPosition();
        final CustomViewHolder customViewHolder = (CustomViewHolder) holder;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 아이템 클릭했을때
                //워하는 액티비티로 intent에 값도 같이 넣어서 액티비티 호출 가능
                //Context context = view.getContext();
                //Intent intent = new Intent(view.getContext(),Community_post.class);
                //intent.putExtra("key",arrayList.get(customViewHolder.getAdapterPosition()).getKey());
                //context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView im_profile;
        TextView tv_cockname;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.im_profile = itemView.findViewById(R.id.im_favorite_profile);
            this.tv_cockname = itemView.findViewById(R.id.cm_cock);

        }
    }
}

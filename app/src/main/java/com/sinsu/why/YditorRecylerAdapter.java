package com.sinsu.why;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class YditorRecylerAdapter extends RecyclerView.Adapter<YditorRecylerAdapter.ViewHolder> {

    private ArrayList<YditorPostModel> mData;

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout yditorItemView;
        ImageView yditorImg;
        TextView yditorTitle, yditorName, yditorDes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            yditorItemView = itemView.findViewById(R.id.yditorItemView);

            yditorImg = itemView.findViewById(R.id.yditorImg);
            yditorTitle = itemView.findViewById(R.id.yditorTitle);
            yditorName = itemView.findViewById(R.id.yditorName);
            yditorDes = itemView.findViewById(R.id.yditorDes);
        }

    }

    YditorRecylerAdapter(ArrayList<YditorPostModel> list){
        mData = list;
    }

    @NonNull
    @Override
    public YditorRecylerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.yditor_item, parent, false);
        YditorRecylerAdapter.ViewHolder vh = new YditorRecylerAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull YditorRecylerAdapter.ViewHolder holder, int position) {
        String imgRes = mData.get(position).imgRes;
        String title = mData.get(position).title;
        String name = mData.get(position).name;
        String des = mData.get(position).des;

        Glide.with(holder.yditorItemView)
                .load(imgRes)
                .into(holder.yditorImg);

        holder.yditorTitle.setText(title);
        holder.yditorName.setText(name);
        holder.yditorDes.setText(des);
    }

    @Override
    public int getItemCount() {return (mData != null ? mData.size() : 0);}
}

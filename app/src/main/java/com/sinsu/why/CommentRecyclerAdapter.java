package com.sinsu.why;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<CommentModel> list;

    String userName, content;

    String userProfileImg;

    public CommentRecyclerAdapter(Context context, List<CommentModel> list) {
        this.context = context;
        this.list = list;
    }


    //ViewHolder 생성
    // row layout을 화면에 보여주고 holder에 연결
    @NonNull
    @Override
    public CommentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_custom_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRecyclerAdapter.ViewHolder holder, int position) {
        int itemPosition = position;

        userProfileImg = list.get(itemPosition).getUserProfileImg();
        content = list.get(itemPosition).getComment();
        userName = list.get(itemPosition).getUserName();

        holder.contentText.setText(content);
        Glide.with(holder.commentView)
                .load(userProfileImg)
                .circleCrop()
                .into(holder.userProfileImgView);
        holder.userNameDes.setContentDescription(userName);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() :0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout commentView;

        public ImageView userNameDes; // 게시물을 저장한 유저이름을 가져오기 위한 변수. 좋아요 버튼임

        public TextView contentText;

        public ImageView userProfileImgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commentView = itemView.findViewById(R.id.commentView);

            contentText = itemView.findViewById(R.id.commentContentText);
            userProfileImgView = itemView.findViewById(R.id.commentProfileImg);

            userNameDes = itemView.findViewById(R.id.commentHeartImg);
        }
    }

}

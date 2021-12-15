package com.sinsu.why;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<CommentModel> list;

    String userName, content, commentId;

    String userProfileImg;
    private Date uploadTime;

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
        commentId = list.get(itemPosition).getCommentID();

        uploadTime = list.get(itemPosition).getUploadTime();

        Long now = SystemClock.elapsedRealtime();
        Date mDate = new Date(now);
        Long timeGap = mDate.getTime() - uploadTime.getTime();

        String time;

        Double gapResult = ((timeGap.doubleValue()/ (1000 * 60 * 60)));

        if (gapResult % 24 == 0) {
            time = uploadTime.getMonth() + "/" + uploadTime.getDay();
        } else if (gapResult % 24 >= 1){
            time = ((int) (gapResult % 24)) + "시간 전";
        } else if (gapResult * 60 >= 1) {
            time = ((int) ((gapResult * 60) % 60)) + "분 전";
        } else {
            time = "방금 전";
        }
        
        holder.contentText.setText(content);
        Glide.with(holder.commentView)
                .load(userProfileImg)
                .circleCrop()
                .into(holder.userProfileImgView);
        holder.userNameDes.setContentDescription(userName);
        holder.commentUploadTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() :0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout commentView;

        public ImageView userNameDes; // 게시물을 저장한 유저이름을 가져오기 위한 변수. 좋아요 버튼임

        public TextView contentText, commentUploadTime;

        public ImageView userProfileImgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commentView = itemView.findViewById(R.id.commentView);

            contentText = itemView.findViewById(R.id.commentContentText);
            userProfileImgView = itemView.findViewById(R.id.commentProfileImg);

            commentUploadTime = itemView.findViewById(R.id.uploadTimeText);

            userNameDes = itemView.findViewById(R.id.commentHeartImg);

            userNameDes.setContentDescription(userName);

            commentView.setOnLongClickListener(v -> {
                if (userName != null && userNameDes.getContentDescription().equals(AppManager.getCurrentUserName())) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Dialog);
                    dialog.setMessage("답변을 삭제하시겠습니까?")
                            .setTitle("답변 삭제")
                            .setPositiveButton("아니오", null)
                            .setNegativeButton("예",
                                    (dialog1, which) -> {
                                        DatabaseReference db = AppManager.getDatabase().getReference("Contents")
                                                .child("Content")
                                                .child(Comment.title)
                                                .child("Comments")
                                                .child(commentId);
                                        db.removeValue();
                                        Toast.makeText(context, "답변을 삭제했습니다!", Toast.LENGTH_SHORT).show();
                                        Comment.adapter.notifyDataSetChanged();
                                    })
                            .show();
                } else {
                    Toast.makeText(context, "댓글 삭제 권한이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                return false;
            });

        }

    }
}

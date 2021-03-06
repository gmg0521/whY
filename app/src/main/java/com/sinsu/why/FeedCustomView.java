package com.sinsu.why;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FeedCustomView extends RecyclerView.Adapter<FeedCustomView.ViewHolder> {

    private Context context;
    private List<PostModel> list;

    String userName, title, content, heartCount;

    Date uploadTime;

    SimpleDateFormat uploadTimeFormat = new SimpleDateFormat("hh:mm:ss");

    String userProfileImg;

    public FeedCustomView(Context context, List<PostModel> list) {
        this.context = context;
        this.list = list;
    }


    //ViewHolder 생성
    // row layout을 화면에 보여주고 holder에 연결
    @NonNull
    @Override
    public FeedCustomView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_custom_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedCustomView.ViewHolder holder, int position) {
        int itemPosition = position;

        heartCount = String.valueOf(list.get(itemPosition).getHeartCount());
        userProfileImg = list.get(itemPosition).getUserProfileImg();
        title = list.get(itemPosition).getTitle();
        content = list.get(itemPosition).getContent().split(" ")[0];
        userName = list.get(itemPosition).getUserName();
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

        holder.titleText.setText(title);
        holder.contentText.setText(content);
        holder.tvHeartCount.setText(heartCount);
        Glide.with(holder.postView)
                .load(userProfileImg)
                .circleCrop()
                .into(holder.userProfileImgView);
        holder.userNameDes.setContentDescription(userName);
        holder.uploadTimeText.setText(time);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() :0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout postView;

        public ImageView userNameDes; // 게시물을 저장한 유저이름을 가져오기 위한 변수. 좋아요 버튼임

        public TextView titleText;
        public TextView contentText;
        public TextView uploadTimeText;

        public TextView tvHeartCount;

        public ImageView userProfileImgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postView = itemView.findViewById(R.id.commentView);

            titleText = itemView.findViewById(R.id.feedTitleText);
            contentText = itemView.findViewById(R.id.commentContentText);
            userProfileImgView = itemView.findViewById(R.id.commentProfileImg);
            tvHeartCount = itemView.findViewById(R.id.heartCountText);
            uploadTimeText = itemView.findViewById(R.id.uploadTimeText);

            userNameDes = itemView.findViewById(R.id.commentHeartImg);

            postView.setOnTouchListener((v, event) -> {

                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        itemView.setAlpha(0.4f);
                        break;
                    case MotionEvent.ACTION_UP:
                        itemView.setAlpha(1.0f);
                        Intent intent = new Intent(AppManager.ApplicationContext(), Content.class);
                        intent.putExtra("userName", userNameDes.getContentDescription());
                        intent.putExtra("title", titleText.getText());
                        AppManager.ApplicationContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        itemView.setAlpha(1.0f);
                        break;
                }

                return true;
            });
        }
    }

}

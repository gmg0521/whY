package com.sinsu.why;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FeedCustomView extends RecyclerView.Adapter<FeedCustomView.ViewHolder> {

    private Context context;
    private List<PostModel> list = new ArrayList<>();

    public FeedCustomView(Context context, List<PostModel> list) {
        this.context = context;
        this.list = list;
    }


    //ViewHolder 생성
    // row layout을 화면에 보여주고 holder에 연결
    @NonNull
    @Override
    public FeedCustomView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_feed_hot, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedCustomView.ViewHolder holder, int position) {
        int itemPosition = position;
        holder.titleText.setText(list.get(itemPosition).getTitle());
        holder.desText.setText(list.get(itemPosition).getContents());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleText;
        public TextView desText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.feedTitleText);
            desText = itemView.findViewById(R.id.feedDesText);

            itemView.setOnTouchListener((v, event) -> {

                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        itemView.setAlpha(0.4f);
                        break;
                    case MotionEvent.ACTION_UP:
                        itemView.setAlpha(1.0f);
                        Intent intent = new Intent(KakaoManager.ApplicationContext(), Content.class);
                        KakaoManager.ApplicationContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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

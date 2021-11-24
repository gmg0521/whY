package com.sinsu.why;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class FeedCustomView extends LinearLayout {

    ImageView profileImage;
    TextView titleText, desText, uploadTimeText, heartCountText;

    public FeedCustomView(Context context) {
        super(context);
        initView();
    }
    public FeedCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public FeedCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyleAttr);
    }

    private void initView() {
        inflate(getContext(), R.layout.feed_custom_view, this);

        profileImage = (ImageView) findViewById(R.id.feedProfileImg);
        titleText = (TextView) findViewById(R.id.feedTitleText);
        desText = (TextView) findViewById(R.id.feedDesText);
        uploadTimeText = (TextView) findViewById(R.id.feedUploadTimeText);
        heartCountText = (TextView) findViewById(R.id.feedHeartCountText);

    }

    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FeedCustomView);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FeedCustomView, defStyleAttr, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        int profileImgResId = typedArray.getResourceId(R.styleable.FeedCustomView_profileImg, R.drawable.ic_launcher_background);
        profileImage.setImageResource(profileImgResId);

        String title = typedArray.getString(R.styleable.FeedCustomView_titleText);
        titleText.setText(title);

        String Des = typedArray.getString(R.styleable.FeedCustomView_DesText);
        desText.setText(Des);

        String uploadTime = typedArray.getString(R.styleable.FeedCustomView_upLoadTimeText);
        uploadTimeText.setText(uploadTime);

        String heartCount = typedArray.getString(R.styleable.FeedCustomView_heartCount);
        heartCountText.setText(heartCount);

        typedArray.recycle();

    }

    public void setProfileImage(int profileImage_res_id) {
        profileImage.setImageResource(profileImage_res_id);
    }

    public void setTitleText(String title) {
        titleText.setText(title);
    }

    public void setDesText(String des) {
        desText.setText(des);
    }

    public void setUploadTimeText(String uploadTime) {
        uploadTimeText.setText(uploadTime);
    }

    public void setHeartCountText(String heartCount) {
        heartCountText.setText(heartCount);
    }
}

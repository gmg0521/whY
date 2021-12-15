package com.sinsu.why;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Question extends Fragment {

    PostModel postModel = new PostModel();

    Feed feed;

    ViewGroup viewGroup;

    EditText title, contents;

    Button questionUpload;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_question, container, false);

        feed = (Feed) getActivity();

        Intent intent = feed.getIntent();

        title = viewGroup.findViewById(R.id.questionTitle);
        contents = viewGroup.findViewById(R.id.questionEdt);

        questionUpload = viewGroup.findViewById(R.id.btnAnswerUpload);

        if (intent.getBooleanExtra("edit", false)){
            title.setText(intent.getStringExtra("title"));
            contents.setText(intent.getStringExtra("content"));
        }

        if (AppManager.getmAuth().getCurrentUser() != null){
            postModel.setUserName((AppManager.getmAuth().getCurrentUser().getDisplayName()));
            postModel.setUserProfileImg(AppManager.getmAuth().getCurrentUser().getPhotoUrl().toString());
        } else {
            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                @Override
                public Unit invoke(User user, Throwable throwable) {
                    if (user != null){
                        postModel.setUserName(user.getKakaoAccount().getProfile().getNickname());
                        postModel.setUserProfileImg(user.getKakaoAccount().getProfile().getProfileImageUrl());
                    } else {
                        postModel.setUserName("박예섬");
                        postModel.setUserProfileImg("https://k.kakaocdn.net/dn/Tjz3p/btrmzKVixR9/UdvCai3hV7jKVKwk5B4Cs1/img_640x640.jpg");
                    }
                    return null;
                }
            });
        }

        questionUpload.setOnClickListener(v -> Upload());

        return viewGroup;
    }

    private void Upload() {

        String t = title.getText().toString();
        String c = contents.getText().toString();

        postModel.setTitle(t);
        postModel.setContent(c);
        postModel.setHeartCount(0);

        Long now = SystemClock.elapsedRealtime();
        Date mDate = new Date(now);
        postModel.setUploadTime(mDate);

        DatabaseReference databaseReference = AppManager.getDatabase().getReference("Contents")
                .child("Content")
                .child(postModel.getTitle());

        databaseReference.setValue(postModel);


        Toast.makeText(AppManager.ApplicationContext(), "글을 저장하였습니다", Toast.LENGTH_SHORT).show();

        feed.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new MainFeed()).commit();
        feed.bottomNavigationView.setSelectedItemId(R.id.navigation_feed);
    }
}
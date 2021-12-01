package com.sinsu.why;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MainFeedHot extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<PostModel> list;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main_feed_hot, container, false);

        recyclerView = viewGroup.findViewById(R.id.feedHotContainer);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(viewGroup.getContext());
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        databaseReference = AppManager.getDatabase().getReference("User");

        ArrayList<String> userList = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot UserList) {
                for (DataSnapshot user: UserList.getChildren()) {
                    userList.add(user.getKey());
                }
                list.clear();
                for (String user : userList) {
                    databaseReference = databaseReference.child(user).child("contents");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                PostModel postModel = snapshot.getValue(PostModel.class);
                                list.add(postModel);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new FeedCustomView(viewGroup.getContext(), list);
        recyclerView.setAdapter(adapter);

        Log.e("Hot", "MainHotFeed");
        return viewGroup;
    }
}
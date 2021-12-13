package com.sinsu.why;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainFeedHot extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<PostModel> list;
    private RecyclerView.LayoutManager layoutManager;

    private MainFeed mainFeed = new MainFeed();

    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main_feed_hot, container, false);

        recyclerView = viewGroup.findViewById(R.id.feedHotContainer);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(viewGroup.getContext());
        recyclerView.setLayoutManager(layoutManager);

        itemRefresh();

        adapter = new FeedCustomView(viewGroup.getContext(), list);
        recyclerView.setAdapter(adapter);

        return viewGroup;

    }

    private void itemRefresh() {

        list = new ArrayList<>();

        databaseReference = AppManager.getDatabase().getReference("Contents")
                .child("Content");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PostModel postModel = snapshot.getValue(PostModel.class);
                    list.add(postModel);
                }
                Collections.sort(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
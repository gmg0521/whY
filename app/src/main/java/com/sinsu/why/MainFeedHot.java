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

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainFeedHot extends Fragment {

    private RecyclerView recyclerView;
    private FeedCustomView adapter;
    private ArrayList<PostModel> list = new ArrayList<>();

    private int collectionSize = 0;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main_feed_hot, container, false);

        recyclerView = viewGroup.findViewById(R.id.feedHotContainer);

        db.collection("contents").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                collectionSize = task.getResult().size();
            } else {
                Log.d("MainFeedHot", "Error getting documents!", task.getException());
            }
        });

        for (int i = 0; i < collectionSize; i++) {
            DocumentReference documentReference = db.collection("contents").document("gmg0521 " + 1 + " 번 글");
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                PostModel postModel = documentSnapshot.toObject(PostModel.class);
                list.add(postModel);
            });
        }

        recyclerView.setHasFixedSize(true);
        adapter = new FeedCustomView(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        Log.e("Hot", "MainHotFeed");
        return viewGroup;
    }
}
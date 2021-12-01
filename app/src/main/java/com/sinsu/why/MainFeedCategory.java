package com.sinsu.why;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

import java.util.ArrayList;

public class MainFeedCategory extends Fragment {

    Spinner spinner;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<PostModel> list;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase db;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main_feed_category, container, false);

        spinner = viewGroup.findViewById(R.id.categorySpinner);
        String[] item = getResources().getStringArray(R.array.category);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(AppManager.ApplicationContext(),
                android.R.layout.simple_spinner_item,
                item);

        spinner.setAdapter(adapter1);

        recyclerView = viewGroup.findViewById(R.id.feedCategoryContainer);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(viewGroup.getContext());
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        db = FirebaseDatabase.getInstance("https://swproject-309605-default-rtdb.asia-southeast1.firebasedatabase.app/");

        databaseReference = db.getReference("User").child("gmg0521").child("contents");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
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

        adapter = new FeedCustomView(viewGroup.getContext(), list);
        recyclerView.setAdapter(adapter);

        Log.e("Hot", "MainCategoryFeed");
        return viewGroup;
    }
}
package com.sinsu.why;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (item[position]){
                    default:
                        sortByTime(list);
                        break;
                }
            }
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sortByTime(list);
            }

        });

        recyclerView = viewGroup.findViewById(R.id.feedCategoryContainer);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(viewGroup.getContext());
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        databaseReference = AppManager.getDatabase().getReference("Contents")
                .child("Content");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PostModel postModel = snapshot.getValue(PostModel.class);
                    list.add(postModel);
                }
                sortByTime(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new FeedCustomView(viewGroup.getContext(), list);
        recyclerView.setAdapter(adapter);

        return viewGroup;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    List sortByTime(ArrayList<PostModel> list) {
        list.sort(Comparator.comparing(o -> o.uploadTime));
        Collections.reverse(list);
        adapter.notifyDataSetChanged();

        return list;
    }
}

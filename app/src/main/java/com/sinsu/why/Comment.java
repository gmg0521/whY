package com.sinsu.why;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Comment extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<CommentModel> list;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference databaseReference;

    public static String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        recyclerView = findViewById(R.id.commentRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        databaseReference = AppManager.getDatabase().getReference("Contents")
                .child("Content")
                .child(title)
                .child("Comments");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    CommentModel commentModel = dataSnapshot.getValue(CommentModel.class);
                    list.add(commentModel);
                }
                if (list.size() == 0) {
                    CommentModel nomodel = new CommentModel();
                    nomodel.setUserProfileImg(null);
                    nomodel.setComment("아직 답변이 없어요ㅜㅜ");
                    nomodel.setUserName("");
                    nomodel.setCommentID(null);
                    list.add(nomodel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new CommentRecyclerAdapter(Comment.this, list);
        recyclerView.setAdapter(adapter);

    }
}
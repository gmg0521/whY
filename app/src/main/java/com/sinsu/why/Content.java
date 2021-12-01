package com.sinsu.why;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Content extends AppCompatActivity {

    private static final String TAG = "CONTENT";
    DatabaseReference databaseReference;

    PostModel postModel = new PostModel();

    TextView contentTitle, contentDes;

    Button btnContentComments, btnContentAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        String userName, title;

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        title = intent.getStringExtra("title");

        contentTitle = findViewById(R.id.contentTitle);
        contentDes = findViewById(R.id.contentDes);

        btnContentAnswer = findViewById(R.id.btnContentAnswer);
        btnContentComments = findViewById(R.id.btnContentComments);

        databaseReference = AppManager.getDatabase().getReference("User").child(userName).child("contents").child(title);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PostModel postModel = dataSnapshot.getValue(PostModel.class);
                contentTitle.setText(postModel.title);
                contentDes.setText(postModel.content);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btnContentAnswer.setOnClickListener(v -> {
            Intent intent1 = new Intent(Content.this, Answer.class);
            intent1.putExtra("title", title);
            startActivity(intent1);
        });
    }
}
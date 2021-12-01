package com.sinsu.why;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Answer extends AppCompatActivity {

    TextView tvAnswerQuestion;

    EditText answerEdt;

    Button btnAnswerUpload;

    DatabaseReference databaseReference;

    int commentId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        tvAnswerQuestion = findViewById(R.id.tvAnswerQuestion);
        answerEdt = findViewById(R.id.answerEdt);
        btnAnswerUpload = findViewById(R.id.btnAnswerUpload);

        databaseReference = AppManager.getDatabase().getReference("User").child(AppManager.getCurrentUserName()).child("comments").child(title);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentId = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnAnswerUpload.setOnClickListener(v -> {
            databaseReference.child(String.valueOf(commentId)).setValue(answerEdt.getText().toString());
        });

    }
}

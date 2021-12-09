package com.sinsu.why;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Answer extends AppCompatActivity {

    TextView tvAnswerQuestion;

    EditText answerEdt;

    Button btnAnswerUpload;

    DatabaseReference databaseReference;

    String userProfileImg;

    int commentId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        tvAnswerQuestion = findViewById(R.id.tvAnswerQuestion);
        answerEdt = (EditText) findViewById(R.id.answerEditText);
        btnAnswerUpload = findViewById(R.id.btnAnswerUpload);

        databaseReference = AppManager.getDatabase().getReference("Contents").child("Content").child(title).child("Comment");

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
            CommentModel comment = new CommentModel();
            comment.setUserName(AppManager.getCurrentUserName());
            comment.setComment(answerEdt.getText().toString());

            DatabaseReference mDb = AppManager.getDatabase().getReference("User")
                    .child(AppManager.getCurrentUserName())
                    .child("userProfileImg");
            mDb.get().addOnCompleteListener(task -> {
                if (!task.isSuccessful())
                    Toast.makeText(getApplicationContext(), "사용자 이미지를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                else
                    userProfileImg = task.getResult().getValue().toString();
            });

            comment.setUserProfileImg(userProfileImg);

            databaseReference = AppManager.getDatabase().getReference("Contents")
                    .child("Content").child(title).child("Comment");

            databaseReference.child(String.valueOf(commentId)).setValue(comment);
            onBackPressed();
            Toast.makeText(AppManager.ApplicationContext(), "답변이 등록 되었습니다!", Toast.LENGTH_SHORT).show();
        });

    }
}

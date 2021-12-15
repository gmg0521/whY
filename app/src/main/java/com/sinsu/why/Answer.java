package com.sinsu.why;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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

import java.util.Date;

public class Answer extends AppCompatActivity {

    TextView tvAnswerQuestion;

    EditText answerEdt;

    Button btnAnswerUpload;

    DatabaseReference databaseReference;

    String userProfileImg;

    String commentId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        tvAnswerQuestion = findViewById(R.id.tvAnswerQuestion);
        answerEdt = findViewById(R.id.answerEditText);
        btnAnswerUpload = findViewById(R.id.btnAnswerUpload);

        databaseReference = AppManager.getDatabase().getReference("Contents").child("Content").child(title).child("Comments");

        tvAnswerQuestion.setText(title);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentId = String.valueOf(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnAnswerUpload.setOnClickListener(v -> {
            DatabaseReference mDb = AppManager.getDatabase().getReference("User")
                    .child(AppManager.getCurrentUserName())
                    .child("userProfileImg");

            mDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CommentModel comment = new CommentModel();
                    comment.setUserName(AppManager.getCurrentUserName());
                    comment.setComment(answerEdt.getText().toString());
                    comment.setCommentID(commentId);
                    userProfileImg = String.valueOf(snapshot.getValue());
                    comment.setUserProfileImg(userProfileImg);

                    Long now = SystemClock.elapsedRealtime();
                    Date mDate = new Date(now);
                    comment.setUploadTime(mDate);

                    databaseReference = AppManager.getDatabase().getReference("Contents")
                            .child("Content").child(title).child("Comments");

                    databaseReference.child(commentId).setValue(comment);
                    onBackPressed();
                    Toast.makeText(AppManager.ApplicationContext(), "답변이 등록 되었습니다!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

    }
}

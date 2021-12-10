package com.sinsu.why;

import static com.sinsu.why.AppManager.getCurrentUserName;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Content extends AppCompatActivity {

    private static final String TAG = "CONTENT";
    DatabaseReference databaseReference;

    TextView contentTitle, contentDes;

    Feed feed;

    Button btnContentComments, btnContentAnswer, btnContentEdit, btnContentDel;

    ImageButton btnHeartCount;

    Boolean isUserIn = false;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        String userName, title;

        FragmentManager fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        title = intent.getStringExtra("title");

        contentTitle = findViewById(R.id.contentTitle);
        contentDes = findViewById(R.id.contentDes);

        btnContentAnswer = findViewById(R.id.btnContentAnswer);
        btnContentComments = findViewById(R.id.btnContentComments);
        btnContentEdit = findViewById(R.id.btnContentEdit);
        btnContentDel = findViewById(R.id.btnContentDel);

        btnHeartCount = findViewById(R.id.contentHeart);

        databaseReference = AppManager.getDatabase().getReference("Contents").child("Content").child(title);
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

        btnContentComments.setOnClickListener(v -> {
            Intent intent1 = new Intent(Content.this, Comment.class);
            intent1.putExtra("title", title);
            startActivity(intent1);
        });

        btnContentEdit.setOnClickListener(v -> {
            if (getCurrentUserName().equals(userName)) {
                Intent intent1 = new Intent(Content.this, Feed.class);
                intent1.putExtra("title", title);
                intent1.putExtra("content", contentDes.getText().toString());
                intent1.putExtra("edit", true);
                startActivity(intent1);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "수정 권한이 없습니다.", Toast.LENGTH_SHORT).show();
        });

        btnContentDel.setOnClickListener(v -> {
            if (getCurrentUserName().equals(userName)) {
                databaseReference.removeValue();
                onBackPressed();
                Toast.makeText(getApplicationContext(), "질문이 삭제 되었습니다!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "삭제 권한이 없습니다.", Toast.LENGTH_SHORT).show();
        });

        btnHeartCount.setOnClickListener(v -> heart(title));
    }

    private void heart(String title) {

        ArrayList<String> userList = new ArrayList<>();

        databaseReference = AppManager.getDatabase().getReference("Contents").child("Content").child(title).child("heartList");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    userList.add(dataSnapshot.getValue().toString());
                }
                if (userList.size() == 0) {
                    addHeart();
                } else {
                    for (String user : userList) {
                        if (user.equals(AppManager.getCurrentUserName())) {
                            isUserIn = true;
                            Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), android.R.drawable.btn_star_big_off);
                            btnHeartCount.setImageBitmap(icon);
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                    for (DataSnapshot data : snapshot1.getChildren()) {
                                        if (data.getValue().toString().equals(user))
                                            databaseReference.child(data.getKey()).removeValue();
                                    }
                                    databaseReference.getParent().addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                            PostModel postModel = snapshot2.getValue(PostModel.class);
                                            databaseReference.getParent().child("heartCount").setValue(postModel.heartCount - 1);
                                            Toast.makeText(getApplicationContext(), "좋아요가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        if (!isUserIn) addHeart();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void addHeart() {
        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), android.R.drawable.btn_star_big_on);
        btnHeartCount.setImageBitmap(icon);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(String.valueOf(snapshot.getChildrenCount())).setValue(getCurrentUserName());
                databaseReference.getParent().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        PostModel postModel = snapshot.getValue(PostModel.class);
                        databaseReference.getParent().child("heartCount").setValue(postModel.heartCount+1);
                        Toast.makeText(getApplicationContext(), "좋아요!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
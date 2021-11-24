package com.sinsu.why;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Content extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    PostModel postModel = new PostModel();

    TextView contentTitle, contentDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        contentTitle = findViewById(R.id.contentTitle);
        contentDes = findViewById(R.id.contentDes);

        DocumentReference documentReference = db.collection("contents").document("gmg0521 " + 1 + " 번 글");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                postModel = documentSnapshot.toObject(PostModel.class);
                contentTitle.setText(postModel.title);
                contentDes.setText(postModel.contents);
            }
        });
    }
}
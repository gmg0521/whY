package com.sinsu.why;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Question extends Fragment {

    PostModel postModel = new PostModel();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ViewGroup viewGroup;

    EditText title, contents;

    Button questionUpload;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_question, container, false);

        title = viewGroup.findViewById(R.id.questionTitle);
        contents = viewGroup.findViewById(R.id.questionEdt);

        questionUpload = viewGroup.findViewById(R.id.btnQuestionUpload);

        questionUpload.setOnClickListener(v -> {
            String t = title.getText().toString();
            String c = contents.getText().toString();

            postModel.userId = "gmg0521";
            postModel.userName = "까마귀";
            postModel.title = t;
            postModel.contents = c;
            postModel.heartCount = 1024;

            db.collection("contents")
                    .document(postModel.userId +" " + 1 + " 번 글")
                    .set(postModel)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(KakaoManager.ApplicationContext(), "글을 등록하였습니다!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(KakaoManager.ApplicationContext(), Content.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(KakaoManager.ApplicationContext(), "오류가 발생하였습니다", Toast.LENGTH_SHORT).show());

        });

        return viewGroup;
    }
}
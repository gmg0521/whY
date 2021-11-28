package com.sinsu.why;

import static com.sinsu.why.ContentAdd.addNewiew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

import java.util.HashMap;
import java.util.Map;

public class MainFeedHot extends Fragment {

    LinearLayout con;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main_feed_hot, container, false);

        con = viewGroup.findViewById(R.id.feedHotContainer);


        for (int i = 0; i < 10; i++) {
            addNewiew(con, "TooHot", "반가워요", "now", 1024);
        }

        Map<String, Object> user = new HashMap<>();
        user.put("title", "Alan");
        user.put("des", "Lovelace");
        user.put("uploadTime", 1815);
        user.put("heartCount", 1024);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference -> {

                })
                .addOnFailureListener(e -> Toast.makeText(KakaoManager.ApplicationContext(), "글 저장 실패.", Toast.LENGTH_LONG).show());


        return viewGroup;
    }

}
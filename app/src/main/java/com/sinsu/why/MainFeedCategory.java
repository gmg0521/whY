package com.sinsu.why;

import static com.sinsu.why.ContentAdd.addNewiew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainFeedCategory extends Fragment {

    LinearLayout con;
    Spinner spinner;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main_feed_category, container, false);

        con = viewGroup.findViewById(R.id.feedCategoryContainer);
        spinner = viewGroup.findViewById(R.id.categorySpinner);
        String[] item = getResources().getStringArray(R.array.category);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(KakaoManager.ApplicationContext(),
                android.R.layout.simple_spinner_item,
                item);

        spinner.setAdapter(adapter);

        for (int i = 0; i < 10; i++) {
            addNewiew(con, "hi", "반가워요", "now", 1024);
        }

        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        Object title = "기본";
                        Object des = "기본";
                        Object uploadTime = "기본";
                        Object heartCount = 1024;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            title = document.getData().get("title");
                            des = document.getData().get("des");
                            uploadTime = document.getData().get("uploadTime");
                            heartCount = document.getData().get("heartCount");
                        }
                        addNewiew(con, title, des, uploadTime, heartCount);
                    } else {
                        Toast.makeText(KakaoManager.ApplicationContext(), "띠용..." + task.getException(), Toast.LENGTH_LONG).show();
                    }
                });

        return viewGroup;
    }
}
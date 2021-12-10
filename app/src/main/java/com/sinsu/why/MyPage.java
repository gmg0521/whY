package com.sinsu.why;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.kakao.sdk.user.UserApiClient;

public class MyPage extends Fragment{

    static final String[] LIST_MENU = {"내 질문", "내 답변", "내가 누른 좋아요", "회원 정보", "테마 변경"};

    ViewGroup viewGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_my_page, container, false);

        ArrayAdapter adapter = new ArrayAdapter(AppManager.ApplicationContext(), R.layout.listview, LIST_MENU);
        ListView listView = viewGroup.findViewById(R.id.mypageListview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String strText = (String) parent.getItemAtPosition(position);

            switch (strText){
                case "내 질문":
                    break;
                case "내 답변":
                    break;
                case "내가 누른 좋아요":
                    break;
                case "회원 정보":
                    revokeAccess();
                    AppManager.setLogined(false);
                    Intent backMain = new Intent(viewGroup.getContext(), MainActivity.class);
                    startActivity(backMain);
                    break;
                case "테마 변경":
                    break;
            }

        });

        return viewGroup;
    }
    private void revokeAccess() {
        UserApiClient.getInstance().unlink(throwable -> null);
        FirebaseAuth.getInstance().signOut();
        AppManager.googleSignInClient.revokeAccess();
        DatabaseReference db = AppManager.getDatabase().getReference("User").child(AppManager.getCurrentUserName());
        db.removeValue();
        Toast.makeText(AppManager.ApplicationContext(), "로그아웃 하였습니다.", Toast.LENGTH_SHORT).show();
    }

}
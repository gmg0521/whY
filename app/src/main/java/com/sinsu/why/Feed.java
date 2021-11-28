package com.sinsu.why;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.sdk.user.UserApiClient;

public class Feed extends AppCompatActivity {

    String strNickname, strEmail;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MainFeed mainFeed = new MainFeed();
    private Question question = new Question();
    private Yditor yditor = new Yditor();
    private MyPage myPage = new MyPage();

    private BottomNavigationView bottomNavigationView;

    @SuppressLint({"ResourceType", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Intent intent = getIntent();
        strNickname = intent.getStringExtra("name");
        strEmail = intent.getStringExtra("email");

        TextView kakaoNickname = findViewById(R.id.kakaoNick);
        TextView kakaoEmail = findViewById(R.id.kakaoEmail);
        
        kakaoNickname.setText(strNickname);
        kakaoEmail.setText(strEmail);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mainFeed).commit();
        bottomNavigationView.setSelectedItemId(R.id.navigation_feed);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction =fragmentManager.beginTransaction();
            switch (item.getItemId()){
                case R.id.navigation_feed:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mainFeed).commit();
                    break;
                case R.id.navigation_question:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, question).commit();
                    break;
                case R.id.navigation_yditor:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, yditor).commit();
                    break;
                case R.id.navigation_mypage:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, myPage).commit();
                    break;
            }

            return true;
        });

        findViewById(R.id.feedFaqButton).setOnClickListener(v -> UserApiClient.getInstance().unlink(throwable -> {
            Toast.makeText(getApplicationContext(), "로그아웃 하였습니다!", Toast.LENGTH_SHORT).show();
            Intent backMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backMain);
            return null;
        }));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(KakaoManager.ApplicationContext(), "로그아웃 하였습니다.", Toast.LENGTH_SHORT).show();

    }
}
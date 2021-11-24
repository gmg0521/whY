package com.sinsu.why;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Feed extends AppCompatActivity {

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

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mainFeed).commit();
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, yditor).commit();;
                    break;
                case R.id.navigation_mypage:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, myPage).commit();
                    break;
            }

            return true;
        });

    }
}
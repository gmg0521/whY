package com.sinsu.why;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.kakao.sdk.user.UserApiClient;

public class Feed extends AppCompatActivity {

    String strNickname;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MainFeed mainFeed = new MainFeed();
    private Question question = new Question();
    private Yditor yditor = new Yditor();
    private MyPage myPage = new MyPage();

    public BottomNavigationView bottomNavigationView;

    @SuppressLint({"ResourceType", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        findViewById(R.id.feedFaqButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), FAQ.class);
            startActivity(intent);
        });

        Intent intent = getIntent();
        strNickname = intent.getStringExtra("name");
        Boolean editable = intent.getBooleanExtra("edit", false);

        TextView kakaoNickname = findViewById(R.id.kakaoNick);
        
        kakaoNickname.setText(strNickname);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        if (editable) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, question).commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_question);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mainFeed).commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_feed);
        }

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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
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

public class Feed extends AppCompatActivity implements View.OnClickListener {

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

        Intent intent = getIntent();
        strNickname = intent.getStringExtra("name");

        TextView kakaoNickname = findViewById(R.id.kakaoNick);
        
        kakaoNickname.setText(strNickname);

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

        findViewById(R.id.feedFaqButton).setOnClickListener(this);

    }

    private void revokeAccess() {
        UserApiClient.getInstance().unlink(throwable -> {
            return null;
        });
        FirebaseAuth.getInstance().signOut();
        AppManager.googleSignInClient.revokeAccess();
        DatabaseReference db = AppManager.getDatabase().getReference("User").child(AppManager.getCurrentUserName());
        db.removeValue();
        Toast.makeText(AppManager.ApplicationContext(), "로그아웃 하였습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedFaqButton:
                revokeAccess();
                AppManager.setLogined(false);
                finishAffinity();
                Intent backMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backMain);
                break;
        }
    }
}
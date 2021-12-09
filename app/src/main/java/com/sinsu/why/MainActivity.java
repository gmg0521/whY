package com.sinsu.why;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.kakao.sdk.user.UserApiClient;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(() -> {

            if (!isLogined()){
                intent = new Intent(MainActivity.this, LoginMain.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }

    @NonNull
    private Boolean isLogined() {
        UserApiClient.getInstance().me((user, throwable) -> {
            if (user != null) {
                AppManager.setCurrentUserName(user.getKakaoAccount().getProfile().getNickname());
                AppManager.setLogined(true);
                Toast.makeText(MainActivity.this, "환영합니다!", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, Feed.class);
                intent.putExtra("name", user.getKakaoAccount().getProfile().getNickname());
                intent.putExtra("email", user.getKakaoAccount().getEmail());
                startActivity(intent);
                finish();
            }
            return null;
        }
       );

        if (mAuth.getCurrentUser() != null) {
            AppManager.setCurrentUserName(mAuth.getCurrentUser().getDisplayName());
            AppManager.setLogined(true);
            Intent intent = new Intent(getApplication(), Feed.class);
            intent.putExtra("name", mAuth.getCurrentUser().getDisplayName());
            intent.putExtra("email", mAuth.getCurrentUser().getEmail());
            startActivity(intent);
            finish();
        }
        return AppManager.isLogined();
    }
}
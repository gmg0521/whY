package com.sinsu.why;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.user.UserApiClient;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> UserApiClient.getInstance().me((user, throwable) -> {
            if (user != null) {
                Toast.makeText(MainActivity.this, "환영합니다!", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, Feed.class);
                intent.putExtra("name", user.getKakaoAccount().getProfile().getNickname());
                intent.putExtra("email",user.getKakaoAccount().getEmail());
                startActivity(intent);
            } else {
                intent = new Intent(MainActivity.this, LoginMain.class);
                startActivity(intent);
            }
            return null;
        }), 3000);

    }
}
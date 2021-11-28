package com.sinsu.why;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class CreateAccount extends AppCompatActivity {

    Button btnKakaoLogin;
    
    Button btnCABack, btnCACreate;
    private Function2<OAuthToken, Throwable, Unit> addCallback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            if (oAuthToken != null) {
                login();
            }
            if (throwable != null) {
                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다... 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    };

    private void login() {
        Toast.makeText(getApplicationContext(), "환영합니다!", Toast.LENGTH_SHORT).show();
        UserApiClient.getInstance().me((user, throwable1) -> {
            if (user != null){
                Intent intent = new Intent(getApplicationContext(), Feed.class);
                intent.putExtra("name", user.getKakaoAccount().getProfile().getNickname());
                intent.putExtra("email", user.getKakaoAccount().getEmail());
                startActivity(intent);
            } else {
              if (throwable1 != null){
                  Toast.makeText(CreateAccount.this, "사옹자 정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
              }
            }
            return null;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        
        btnKakaoLogin = (Button) findViewById(R.id.btnKakaoReg);
        
        btnKakaoLogin.setOnClickListener(v -> {
            if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(CreateAccount.this)) {
                UserApiClient.getInstance().loginWithKakaoTalk(CreateAccount.this, addCallback);
            } else {
                UserApiClient.getInstance().loginWithKakaoAccount(CreateAccount.this, addCallback);
            }
        });

        


        btnCABack = (Button) findViewById(R.id.btnCABack);
        btnCACreate = (Button) findViewById(R.id.btnCACreate);

        btnCABack.setOnClickListener(v -> finish());
        btnCACreate.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccount.this, Feed.class);
            intent.putExtra("name", "Temp");
            intent.putExtra("email","temp@temp.com");
            startActivity(intent);
        });

    }
}
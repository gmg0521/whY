package com.sinsu.why;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LoginMain extends AppCompatActivity {

    Button btnLogin, btnCA;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnCA = findViewById(R.id.btnCA);

        btnLogin.setOnClickListener(v -> {
            intent = new Intent(LoginMain.this, Login.class);
            startActivity(intent);
        });

        btnCA.setOnClickListener(v -> {
            intent = new Intent(LoginMain.this, CreateAccount.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finishAndRemoveTask();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
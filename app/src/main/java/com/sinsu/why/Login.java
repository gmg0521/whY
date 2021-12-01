package com.sinsu.why;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button btnEmailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEmailLogin  = findViewById(R.id.btnEmailLogin);

        btnEmailLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Feed.class);
            intent.putExtra("name", "Temp");
            intent.putExtra("email","temp@temp.com");
            startActivity(intent);
            finish();
        });
    }
}
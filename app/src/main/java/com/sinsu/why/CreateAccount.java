package com.sinsu.why;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class CreateAccount extends AppCompatActivity {

    Button btnCABack, btnCACreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        btnCABack = (Button) findViewById(R.id.btnCABack);
        btnCACreate = (Button) findViewById(R.id.btnCACreate);

        btnCABack.setOnClickListener(v -> onBackPressed());
        btnCACreate.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccount.this, Feed.class);
            startActivity(intent);
        });

    }
}
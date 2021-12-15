package com.sinsu.why;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class YditorContent extends AppCompatActivity {

    ConstraintLayout container;

    ImageView yditorimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yditor_content);

        container = findViewById(R.id.container);

        yditorimg = findViewById(R.id.yditorContentImg);

        Glide.with(container)
                .load("https://t1.daumcdn.net/thumb/R200x0/?fname=http%3A%2F%2Ft1.daumcdn.net%2Foven%2F101039377%2FpJmxITOZ1q4FU2dF8RaE723cEaO1H8Cu")
                .into(yditorimg);

    }
}
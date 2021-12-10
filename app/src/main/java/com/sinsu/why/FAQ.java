package com.sinsu.why;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FAQ extends AppCompatActivity {

    static final String[] LIST_MENU = {"자주 묻는 질문", "문의하기", "내 문의"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.listview, LIST_MENU);
        ListView listView = findViewById(R.id.faqListView);
        listView.setAdapter(adapter);
    }
}
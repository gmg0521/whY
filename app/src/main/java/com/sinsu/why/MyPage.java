package com.sinsu.why;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MyPage extends Fragment{

    static final String[] LIST_MENU = {"내 질문", "내 답변", "내가 누른 좋아요", "회원 정보", "테마 변경"};

    ViewGroup viewGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_my_page, container, false);

        ArrayAdapter adapter = new ArrayAdapter(AppManager.ApplicationContext(), R.layout.listview, LIST_MENU);
        ListView listView = viewGroup.findViewById(R.id.mypageListview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strText = (String) parent.getItemAtPosition(position);

                switch (strText){
                    case "내 질문":
                        break;
                    case "내 답변":
                        break;
                    case "내가 누른 좋아요":
                        break;
                    case "회원 정보":
                        break;
                    case "테마 변경":
                        break;
                }

            }
        });

        return viewGroup;
    }

}
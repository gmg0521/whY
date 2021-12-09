package com.sinsu.why;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Yditor extends Fragment {

    ViewGroup viewGroup;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<YditorPostModel> yditorPostModels;
    RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_yditor, container, false);

        recyclerView = viewGroup.findViewById(R.id.yditorRecyclerView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(viewGroup.getContext());
        recyclerView.setLayoutManager(layoutManager);

        yditorPostModels = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            YditorPostModel yditorPostModel = new YditorPostModel();
            yditorPostModel.setImgRes("https://s.pstatic.net/shopping.phinf/20211206_10/fb11ffbe-99dc-4e14-bfe6-5ec0c2d87bd6.jpg?type=f214_292");
            yditorPostModel.setTitle("주간 whY");
            yditorPostModel.setName("와이디터 A");
            yditorPostModel.setDes("이번주 여러분의 whY는 무엇인가요?");

            yditorPostModels.add(yditorPostModel);
        }

        adapter = new YditorRecyclerAdapter(yditorPostModels);
        recyclerView.setAdapter(adapter);

        return viewGroup;
    }
}
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
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Yditor extends Fragment {

    ViewGroup viewGroup;

    Button subscribe;

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

        subscribe = viewGroup.findViewById(R.id.subscribe);

        subscribe.setOnClickListener(v -> {
            Toast.makeText(AppManager.ApplicationContext(), "준비중입니다!", Toast.LENGTH_SHORT).show();
        });

        Thread thread = new Thread(() -> {
            yditorPostModels = new ArrayList<>();

            YditorPostModel yditorPostModel = new YditorPostModel();
            yditorPostModel.setImgRes("https://s.pstatic.net/shopping.phinf/20211206_10/fb11ffbe-99dc-4e14-bfe6-5ec0c2d87bd6.jpg?type=f214_292");
            yditorPostModel.setTitle("주간 whY");
            yditorPostModel.setName("와이디터 A");
            yditorPostModel.setDes("이번주 여러분의 whY는 무엇인가요?");

            yditorPostModels.add(yditorPostModel);

            YditorPostModel yditorPostModel2 = new YditorPostModel();
            yditorPostModel2.setImgRes("https://sticker.smilecat.com/stickers/get_/get_jv11349126_200204_st_yh_01/thumb800.png");
            yditorPostModel2.setTitle("지구는 과연 둥굴까요?");
            yditorPostModel2.setName("와이디터 B");
            yditorPostModel2.setDes("사실 지구는 둥근게 아니라 동글동글");

            yditorPostModels.add(yditorPostModel2);

            YditorPostModel yditorPostModel3 = new YditorPostModel();
            yditorPostModel3.setImgRes("https://sticker.smilecat.com/stickers/uto_/uto_21014816_210929_st_sh_54/thumb800.png");
            yditorPostModel3.setTitle("화성에는 정말 강아지가 살까요?");
            yditorPostModel3.setName("와이디터 C");
            yditorPostModel3.setDes("사실 화성에는 강아지가 아니라 그");

            yditorPostModels.add(yditorPostModel3);

            YditorPostModel yditorPostModel4 = new YditorPostModel();
            yditorPostModel4.setImgRes("https://sticker.smilecat.com/stickers/get_/get_jv11974692_200108_st_sh_03/thumb800.png");
            yditorPostModel4.setTitle("어~느새~ 부터");
            yditorPostModel4.setName("와이디터 A");
            yditorPostModel4.setDes("힙합은 안 멋져~");

            yditorPostModels.add(yditorPostModel4);
        });

        thread.run();

        adapter = new YditorRecyclerAdapter(yditorPostModels);
        recyclerView.setAdapter(adapter);

        return viewGroup;
    }
}
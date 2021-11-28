package com.sinsu.why;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ContentAdd {

    public static void addNewiew(LinearLayout con, Object title, Object des, Object upt, Object count) {

        int vID;

        FeedCustomView nLayout = new FeedCustomView(KakaoManager.ApplicationContext());

        nLayout.setId(nLayout.generateViewId());

        vID = nLayout.getId();

        nLayout.setProfileImage(R.drawable.ic_launcher_foreground);
        nLayout.setTitleText(title.toString());
        nLayout.setDesText(des.toString());
        nLayout.setUploadTimeText(upt.toString());
        nLayout.setHeartCountText(count.toString());

        nLayout.setOnTouchListener((v, event) -> {

            int action = event.getAction();

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    nLayout.setAlpha(0.4f);
                    break;
                case MotionEvent.ACTION_UP:
                    nLayout.setAlpha(1.0f);
                    Intent intent = new Intent(KakaoManager.ApplicationContext(), Content.class);
                    KakaoManager.ApplicationContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case MotionEvent.ACTION_CANCEL:
                    nLayout.setAlpha(1.0f);
                    break;
            }

            return true;
        });

        con.addView(nLayout);

    }

}

package com.sinsu.why;

import android.app.Application;
import android.content.Context;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoManager extends Application
{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "9fdf24a2755c8133df4d72883597cebc");

        KakaoManager.context = getApplicationContext();
    }

    public static Context ApplicationContext() {
        return KakaoManager.context;
    }
}
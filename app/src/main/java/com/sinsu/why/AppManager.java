package com.sinsu.why;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.sdk.common.KakaoSdk;

public class AppManager extends Application
{
    private static boolean logined = false;

    public static GoogleSignInClient googleSignInClient;

    private static Context context;

    private static FirebaseAuth mAuth;
    private static FirebaseDatabase database;

    private static String currentUserName;

    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "9fdf24a2755c8133df4d72883597cebc");

        AppManager.context = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance("https://swproject-309605-default-rtdb.asia-southeast1.firebasedatabase.app/");

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public static void setmAuth(FirebaseAuth mAuth) {
        AppManager.mAuth = mAuth;
    }


    public static FirebaseDatabase getDatabase() {
        return database;
    }

    public static void setDatabase(FirebaseDatabase database) {
        AppManager.database = database;
    }


    public static boolean isLogined() {
        return logined;
    }

    public static void setLogined(boolean logined) {
        AppManager.logined = logined;
    }

    public static Context ApplicationContext() {
        return AppManager.context;
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void setCurrentUserName(String currentUserName) {
        AppManager.currentUserName = currentUserName;
    }
}
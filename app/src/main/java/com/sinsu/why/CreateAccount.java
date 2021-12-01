package com.sinsu.why;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class CreateAccount extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private SignInButton btnGooglLogin;

    Button btnKakaoLogin;
    
    Button btnCABack, btnCACreate;

    //카카오 로그인
    private Function2<OAuthToken, Throwable, Unit> addCallback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            if (oAuthToken != null) {
                kakaoLogin();
            }
            if (throwable != null) {
                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다... 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    };

    private void kakaoLogin() {
        Toast.makeText(getApplicationContext(), "환영합니다!", Toast.LENGTH_SHORT).show();
        UserApiClient.getInstance().me((user, throwable1) -> {
            if (user != null){
                AppManager.setCurrentUserName(user.getKakaoAccount().getProfile().getNickname());

                Intent intent = new Intent(getApplicationContext(), Feed.class);
                intent.putExtra("name", AppManager.getCurrentUserName());
                intent.putExtra("email", user.getKakaoAccount().getEmail());
                startActivity(intent);
                finish();
            } else {
              if (throwable1 != null){
                  Toast.makeText(CreateAccount.this, "사옹자 정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
              }
            }
            return null;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        if (AppManager.getmAuth().getCurrentUser() != null) {
            Intent intent = new Intent(getApplication(), Feed.class);
            intent.putExtra("name", AppManager.getmAuth().getCurrentUser().getDisplayName());
            intent.putExtra("email", AppManager.getmAuth().getCurrentUser().getEmail());
            startActivity(intent);
            finish();
        }
        
        btnKakaoLogin = (Button) findViewById(R.id.btnKakaoReg);
        btnGooglLogin = findViewById(R.id.btnGoogleReg);
        
        btnKakaoLogin.setOnClickListener(v -> {
            if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(CreateAccount.this)) {
                UserApiClient.getInstance().loginWithKakaoTalk(CreateAccount.this, addCallback);
            } else {
                UserApiClient.getInstance().loginWithKakaoAccount(CreateAccount.this, addCallback);
            }
        });

        btnGooglLogin.setOnClickListener(v -> googleLogin());

        btnCABack = (Button) findViewById(R.id.btnCABack);
        btnCACreate = (Button) findViewById(R.id.btnCACreate);

        btnCABack.setOnClickListener(v -> finish());
        btnCACreate.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccount.this, Feed.class);
            intent.putExtra("name", "Temp");
            intent.putExtra("email","temp@temp.com");
            startActivity(intent);
            finish();
        });

    }

    private void googleLogin() {
        Intent signInIntent = AppManager.googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //로그인 성공시
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        AppManager.getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // 로그인 성공시 "UI 업데이트"
                            Snackbar.make(findViewById(R.id.createLayout),"Authentication Successed", Snackbar.LENGTH_SHORT).show();
                            FirebaseUser user = AppManager.getmAuth().getCurrentUser();
                            updateUI(user);
                        } else {
                            Snackbar.make(findViewById(R.id.createLayout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            AppManager.setCurrentUserName(user.getDisplayName());

            Intent intent = new Intent(getApplication(), Feed.class);
            intent.putExtra("name", AppManager.getCurrentUserName());
            intent.putExtra("email", AppManager.getmAuth().getCurrentUser().getEmail());
            startActivity(intent);
            finish();
        }
    }
}
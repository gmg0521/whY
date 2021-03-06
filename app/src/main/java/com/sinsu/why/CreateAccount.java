package com.sinsu.why;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class CreateAccount extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private SignInButton btnGooglLogin;

    Button btnKakaoLogin;
    
    Button btnCABack, btnCACreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        if (AppManager.getmAuth().getCurrentUser() != null) {
            login(AppManager.getmAuth().getCurrentUser(), null);
        } else {
            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                @Override
                public Unit invoke(User user, Throwable throwable) {
                    if (user != null)
                        login(null , user);
                    return null;
                }
            });
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

    //????????? ?????????
    private Function2<OAuthToken, Throwable, Unit> addCallback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            if (oAuthToken != null) {
                kakaoLogin();
            }
            if (throwable != null) {
                Toast.makeText(getApplicationContext(), "???????????? ?????????????????????... ?????? ??????????????????", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    };

    private void kakaoLogin() {
        Toast.makeText(getApplicationContext(), "???????????????!", Toast.LENGTH_SHORT).show();
        UserApiClient.getInstance().me((user, throwable1) -> {
            if (user != null){
                login(null, user);
            } else {
                if (throwable1 != null){
                    Toast.makeText(CreateAccount.this, "????????? ????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                }
            }
            return null;
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
                //????????? ?????????
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
                            // ????????? ????????? "UI ????????????"
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
            login(user, null);
        }
    }

    void login(@Nullable FirebaseUser googleUser, @Nullable User kakaoUser){

        UserModel user = new UserModel();

        if (googleUser != null) {
            user.setUserName(googleUser.getDisplayName());
            user.setUserEmail(googleUser.getEmail());
            user.setUserProfileImg(googleUser.getPhotoUrl().toString());

            AppManager.setCurrentUserName(googleUser.getDisplayName());
        } else if (kakaoUser != null) {
            user.setUserName(kakaoUser.getKakaoAccount().getProfile().getNickname());
            user.setUserEmail(kakaoUser.getKakaoAccount().getEmail());
            user.setUserProfileImg(kakaoUser.getKakaoAccount().getProfile().getProfileImageUrl());

            AppManager.setCurrentUserName(kakaoUser.getKakaoAccount().getProfile().getNickname());
        }

        DatabaseReference db = AppManager.getDatabase().getReference("User").child(AppManager.getCurrentUserName());
        db.setValue(user);

        Intent intent = new Intent(getApplication(), Feed.class);
        intent.putExtra("name", AppManager.getCurrentUserName());
        startActivity(intent);
        finish();
    }
}
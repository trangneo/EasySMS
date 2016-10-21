package com.easySMS.ui.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.easySMS.R;
import com.easySMS.model.Member;
import com.easySMS.util.ShareReferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by poiuyt on 10/21/16.
 */

public class SplashActivity extends BaseActivity {
    public FirebaseAuth mAuth;
    Member member;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        doLogin();
    }

    private void doLogin() {
        mAuth = FirebaseAuth.getInstance();
        member = ShareReferenceManager.getData();
        if (member != null) {
            loginWithEmail();
            Log.d("Splash", "!null");
        } else {
            Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Log.d("Splash", "null");
            overridePendingTransition(R.anim.visi_in, R.anim.visi_out);
        }
    }

    private void loginWithEmail() {
        mAuth.signInWithEmailAndPassword(member.getEmail(), member.getPassword())
                .addOnCompleteListener(SplashActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.visi_in, R.anim.visi_out);
                        }
                    }
                });
    }
}

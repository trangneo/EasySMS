package com.example.poiuyt.easysms.ui.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.poiuyt.easysms.R;
import com.example.poiuyt.easysms.data.User;
import com.example.poiuyt.easysms.util.Constants;
import com.example.poiuyt.easysms.util.Util;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

import static com.example.poiuyt.easysms.R.id.edtPassWord;

/**
 * In
 * Created by poiuyt on 9/9/16.
 */

public class SignUpActivity extends BaseActivity {
    public Button btnFinish;
    public TextView tvHello;
    public EditText edtUserName, edtPassword, edtEmail;
    public Firebase memberData;
    public User newMember;
    public FirebaseUser user;
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    public String email, userName, password;
    StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        mAuth = FirebaseAuth.getInstance();
        initComponent();
        bindHandler();
    }

    public void initComponent() {
        edtPassword = (EditText) findViewById(edtPassWord);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        btnFinish = (Button) findViewById(R.id.btnFinish);
        tvHello = (TextView) findViewById(R.id.tvHello);
        storageRef = FirebaseStorage.getInstance().getReference();
        memberData = new Firebase(Constants.URL);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Sign Up", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Sign Up", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    public void bindHandler() {
        btnFinish.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             email = edtEmail.getText().toString().trim();
                                             userName = edtUserName.getText().toString().trim();
                                             password = edtPassword.getText().toString().trim();
                                             String alert = "";

                                             if (Util.isEmptyString(userName)||userName.length()>32||userName.length()<3) {
                                                 alert += getResources().getString(R.string.name_miss) + "\n";
                                             } else {
                                                 if (Util.isEmptyString(email)) {
                                                     alert += getResources().getString(R.string.email_miss) + "\n";
                                                 } else {
                                                     if (!Util.validateEmail(email)) {
                                                         alert += getResources().getString(R.string.email_error) + "\n";
                                                     } else {
                                                         if (Util.isEmptyString(password)) {
                                                             alert += getResources().getString(R.string.password_miss) + "\n";
                                                         } else {
                                                             if (!Util.validatePassword(password)) {
                                                                 alert += getResources().getString(R.string.password_error) + "\n";
                                                             }
                                                         }
                                                     }
                                                 }
                                             }
                                             if (alert != "") {
                                                 alert = alert.substring(0, alert.length() - 1);
                                                 Util.showToast(SignUpActivity.this, alert);
                                             } else {
                                                 showProgressDialog();
                                                 mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                                                         .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                                             @Override
                                                             public void onComplete(@NonNull Task<AuthResult> task) {
                                                                 if (!task.isSuccessful()) {
                                                                     Util.showToast(SignUpActivity.this, "Authentication failed!");
                                                                 } else {
                                                                     uptoDatabase();
                                                                     uptoAuthData(task);
                                                                 }
                                                             }
                                                         });
                                             }
                                         }
                                     }
        );
    }

    public void uptoAuthData(@NonNull Task<AuthResult> task) {
        FirebaseUser firebaseUser = task.getResult().getUser();
        Task<Void> updateTask = firebaseUser.updateProfile(
                new UserProfileChangeRequest.Builder()
                        .setDisplayName(edtUserName.getText().toString()).build());

        updateTask.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    hideProgressDialog();
                    Log.e("Trang", user.getDisplayName() + ".." + user.getToken(true) + "..." + user.getUid() + "..." + user.getEmail());
                    gotoMain();
                }
            }
        });
    }

    public void uptoDatabase() {
        user = mAuth.getCurrentUser();
        newMember = new User(userName, email, password, String.valueOf(new Date().getTime()));
        memberData.child(Constants.USER).child(user.getUid()).setValue(newMember);
    }

    public void gotoMain() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}

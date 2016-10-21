package com.easySMS.ui.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.easySMS.R;
import com.easySMS.model.Member;
import com.easySMS.util.ShareReferenceManager;
import com.easySMS.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends BaseActivity {
    static final String TAG = MainActivity.class.getSimpleName();
    Member member;
    public Button btnSignIn, btnSignUp;
    public TextView tvHello;
    public EditText edtEmail, edtPassword;
    public FirebaseAuth mAuth;
    String email, password, alert;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);
        overridePendingTransition(R.anim.visi_in, 0);
        initComponent();
        bindHandler();
    }

    public void initComponent() {
        mAuth = FirebaseAuth.getInstance();
        edtPassword = (EditText) findViewById(R.id.edtPassWord);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        tvHello = (TextView) findViewById(R.id.tvHello);

    }

    public void bindHandler() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.visi_in, R.anim.visi_out);

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             alert = "";
                                             email = edtEmail.getText().toString().trim();
                                             password = edtPassword.getText().toString().trim();
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
                                             if (alert != "" && alert.charAt(alert.length() - 1) == '\n') {
                                                 alert = alert.substring(0, alert.length() - 1);
                                                 Util.showToast(SignInActivity.this, alert);
                                             } else {
                                                 if (!Util.isOnline(SignInActivity.this)) {
                                                     Util.showToast(SignInActivity.this, getResources().getString(R.string.network_error));
                                                 } else {
                                                     showProgressDialog();
                                                     mAuth.signInWithEmailAndPassword(email, password)
                                                             .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                                                     if (!task.isSuccessful()) {
                                                                         hideProgressDialog();
                                                                         Util.showToast(SignInActivity.this, getResources().getString(R.string.login_error));
                                                                     } else {
                                                                         member = new Member("", email, password, "","");
                                                                         ShareReferenceManager.saveData(member);
                                                                         intent = new Intent(SignInActivity.this, MainActivity.class);
                                                                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                         startActivity(intent);
                                                                         overridePendingTransition(R.anim.visi_in, R.anim.visi_out);
                                                                     }
                                                                 }
                                                             });
                                                 }
                                             }
                                         }
                                     }
        );
    }

    @Override
    protected void onResume() {
        super.onStop();
        hideProgressDialog();
    }
}

package com.example.poiuyt.easysms.ui.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.poiuyt.easysms.R;
import com.example.poiuyt.easysms.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends BaseActivity {

    public Button btnSignIn, btnSignUp;
    public TextView tvHello;
    public EditText edtEmail, edtPassword;
    public FirebaseAuth mAuth;
    String email, password, alert = "";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);

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
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                if (alert != "") {
                    alert = alert.substring(0, alert.length() - 1);
                    Util.showToast(SignInActivity.this, alert);
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
                                        intent = new Intent(SignInActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }

                });
            }
        }


    }

    );
}


}

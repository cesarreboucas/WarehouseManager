package com.warehousemanager.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.warehousemanager.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void onSignUp(View view) {
        Intent it = new Intent(this, SignUpActivity.class);
        startActivity(it);
    }

    public void onSignIn(View view) {
        // TODO Create sign logic using firebase
    }

    public void onForgotPassword(View view) {
        Intent it = new Intent(this, ForgotPasswordActivity.class);
        startActivity(it);
    }
}

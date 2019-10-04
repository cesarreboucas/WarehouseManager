package com.warehousemanager.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.warehousemanager.R;

public class ForgotPasswordActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_password);

    Toolbar toolbar = findViewById(R.id.forgotPasswordToolBar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent it = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
        startActivity(it);
      }
    });
  }

  public void onResetPassword(View view) {
    // TODO Reset the user password on firebase.
  }
}
